package org.vitej.core.utils;

import org.joda.time.DateTime;

public final class TimeUtils {
    public static DateTime longToDateTime(Long timestamp) {
        return timestamp == null ? null : new DateTime(timestamp * 1000);
    }
}
