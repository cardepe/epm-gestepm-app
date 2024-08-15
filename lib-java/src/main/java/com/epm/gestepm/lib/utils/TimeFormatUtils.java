package com.epm.gestepm.lib.utils;

import static java.lang.String.format;
import java.util.concurrent.TimeUnit;

public class TimeFormatUtils {

    private TimeFormatUtils() {
    }

    public static String asHumanReadable(final Long time) {

        if (time == null) {
            return null;
        }

        final long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes);
        final long millis = time - TimeUnit.SECONDS.toMillis(seconds);

        return format("%03dm %02ds %03dms", minutes, seconds, millis);
    }

}
