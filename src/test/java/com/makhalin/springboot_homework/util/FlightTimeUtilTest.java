package com.makhalin.springboot_homework.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlightTimeUtilTest {

    @Test
    void hoursFromSec() {
        var actualResult = FlightTimeUtil.hoursFromSec(8100L);

        assertThat(actualResult).isEqualTo(2);
    }

    @Test
    void minFromSec() {
        var actualResult = FlightTimeUtil.minFromSec(8100L);

        assertThat(actualResult).isEqualTo(15);
    }

    @Test
    void secFromHoursAndMin() {
        var actualResult = FlightTimeUtil.secFromHoursAndMin(2, 15);

        assertThat(actualResult).isEqualTo(8100L);
    }
}