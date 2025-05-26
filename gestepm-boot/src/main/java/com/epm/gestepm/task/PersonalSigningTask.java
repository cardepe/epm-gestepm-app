package com.epm.gestepm.task;

import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.service.PersonalSigningService;
import com.epm.gestepm.modelapi.timecontrolold.dto.SigningScheduledDTO;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
import com.epm.gestepm.task.config.PersonalSigningFtpClient;
import com.mysql.jdbc.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static java.lang.String.format;

@Log
@Component
@RequiredArgsConstructor
public class PersonalSigningTask {

    @Value("${gestepm.signing.lunch.interval.start}")
    private String signingLunchIntervalStart;

    @Value("${gestepm.signing.lunch.interval.end}")
    private String signingLunchIntervalEnd;

    @Value("${gestepm.signing.lunch.interval.time}")
    private Integer signingLunchIntervalTime;

    private final PersonalSigningFtpClient ftpClient;

    private final PersonalSigningService personalSigningService;

    private final UserServiceOld userServiceOld;

    @Scheduled(cron = "${scheduler.personal-signings.cron}")
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            msgIn = "Starting task to process personal signings",
            msgOut = "Starting task to process personal signings OK",
            errorMsg = "Failed to process personal signings")
    public void dailyPersonalSigningProcess() {
        try {
            this.ftpClient.connect();

            final LocalDateTime lunchStarts = this.parseTime(signingLunchIntervalStart);
            final LocalDateTime lunchEnds = this.parseTime(signingLunchIntervalEnd);

            this.ftpClient.listNames().forEach(fileName ->
                this.processFile(fileName, lunchStarts, lunchEnds)
            );

            this.cleanFtpFolderAndClose();

        } catch (Exception e) {
            throw new RuntimeException("Error processing daily personal signings", e);
        }
    }

    private void cleanFtpFolderAndClose() throws IOException {
        this.ftpClient.cleanFolder();
        this.ftpClient.disconnect();
    }

    private void processFile(final String fileName, final LocalDateTime lunchStarts, final LocalDateTime lunchEnds) {

        final String fileContent = this.readFileContent(fileName);

        if (StringUtils.isNullOrEmpty(fileContent)) {
            return;
        }

        final List<SigningScheduledDTO> signings = buildSignings(fileContent);
        signings.sort(Comparator.comparing(SigningScheduledDTO::getUserSigningId));

        final Map<Long, User> userMap = this.userServiceOld.findBySigningIds(
                signings.stream().map(SigningScheduledDTO::getUserSigningId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(User::getSigningId, user -> user));

        this.processSignings(signings, userMap, lunchStarts, lunchEnds);
    }

    private void processSignings(List<SigningScheduledDTO> signings, Map<Long, User> userMap,
                                 LocalDateTime lunchStarts, LocalDateTime lunchEnds) {

        PersonalSigning personalSigning = null;

        for (Map.Entry<Long, User> map : userMap.entrySet()) {
            final List<SigningScheduledDTO> userSignings = signings.stream()
                    .filter(signing -> map.getKey().equals(signing.getUserSigningId()))
                    .collect(Collectors.toList());
            int i = 0;

            for (final SigningScheduledDTO signing : userSignings) {
                i++;

                if (signing.getValue() == 0) {
                    personalSigning = processStartSigning(personalSigning, signing.getDate(), lunchStarts, lunchEnds, map.getValue());
                } else if (signing.getValue() == 1 && personalSigning != null) {
                    personalSigning.setEndDate(signing.getDate());
                }
            }

            if (i == userSignings.size() && personalSigning != null) {

                if (personalSigning.getEndDate() == null) {
                    personalSigning.setEndDate(personalSigning.getStartDate().plusHours(8));
                }

                this.personalSigningService.save(personalSigning);
            }

            log.info(format("User %s processed correctly in personal signings task", map.getKey()));
        }
    }

    private PersonalSigning processStartSigning(PersonalSigning personalSigning, LocalDateTime startDate,
                                                LocalDateTime lunchStarts, LocalDateTime lunchEnds, User user) {
        if (personalSigning != null && personalSigning.getEndDate() != null) {
            this.personalSigningService.save(personalSigning);

            final LocalDateTime endDate = personalSigning.getEndDate();
            final boolean isLunchBreak = endDate.isAfter(lunchStarts) && endDate.isBefore(lunchEnds);

            if (isLunchBreak && Duration.between(startDate, endDate).toMinutes() < signingLunchIntervalTime) {
                startDate = endDate.plusMinutes(signingLunchIntervalTime);
            }

            personalSigning = null;
        }

        if (personalSigning == null) {
            personalSigning = new PersonalSigning();
            personalSigning.setUser(user);
            personalSigning.setStartDate(startDate);
        }

        return personalSigning;
    }

    private LocalDateTime parseTime(final String time) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    private List<SigningScheduledDTO> buildSignings(final String fileContent) {
        final List<String> lines = Arrays.asList(fileContent.split("\n"));

        return lines.stream()
                .filter(line -> !StringUtils.isNullOrEmpty(line))
                .filter(line -> line.contains(","))
                .map(line -> line.replaceAll("\r", ""))
                .map(this::buildSigningSchedules)
                .collect(Collectors.toList());
    }

    private SigningScheduledDTO buildSigningSchedules(final String line) {
        final String[] parts = line.split(",");
        final Long userSigningId = Long.parseLong(parts[0]);
        final LocalDateTime signingDate = Utiles.transform(parts[1], "yyyy-MM-dd HH:mm:ss");
        final int signingValue = Integer.parseInt(parts[2]);

        return new SigningScheduledDTO(userSigningId, signingDate, signingValue);
    }

    private String readFileContent(final String fileName) {
        try {
            return ftpClient.readFileContent(fileName);
        } catch (IOException e) {
            throw new RuntimeException(format("Error reading fileName '%s'", fileName), e);
        }
    }
}
