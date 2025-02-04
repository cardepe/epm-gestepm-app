package com.epm.gestepm.task;

import com.epm.gestepm.modelapi.user.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HolidaysScheduled {

    private final UserService userService;

    public HolidaysScheduled(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateCurrentAndLastHolidaysProcess() {
        this.userService.updateHolidaysInNewYear();
    }

    @Scheduled(cron = "0 0 0 1 3 *")
    public void deleteLastHolidaysProcess() {
        this.userService.resetLastYearHolidays();
    }

}
