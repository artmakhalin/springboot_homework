package com.makhalin.springboot_homework.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlightTimeUtil {

    public static Integer hoursFromSec(Long seconds) {
        return Math.toIntExact(seconds / 3600);
    }

    public static Integer minFromSec(Long seconds) {
        return Math.toIntExact((seconds % 3600) / 60);
    }

    public static Long secFromHoursAndMin(Integer hours, Integer minutes) {
        return  hours * 3600L + minutes * 60L;
    }
}
