package com.epm.gestepm.task;

import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HolidaysScheduled {

    private final UserServiceOld userServiceOld;

    public HolidaysScheduled(UserServiceOld userServiceOld) {
        this.userServiceOld = userServiceOld;
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateCurrentAndLastHolidaysProcess() {
        this.userServiceOld.updateHolidaysInNewYear();
    }

    @Scheduled(cron = "0 0 0 1 3 *")
    public void deleteLastHolidaysProcess() {
        this.userServiceOld.resetLastYearHolidays();
    }

}
