package com.chag.ptr.cocoa.json.parser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailySummary {

    private long dateMillisSinceEpoch;

    private DaySummary daySummary;

    private DaySummary confirmedTestSummary;

    @Getter
    @Setter
    public static class DaySummary {

        private float maximumScore;

        private float scoreSum;

        private float weightDurationSum;
    }
}
