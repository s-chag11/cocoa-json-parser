package com.chag.ptr.cocoa.json.parser.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExposureWindow {

    private long dateMillisSinceEpoch;

    private List<ScanInstance> scanInstances;

    @Getter
    @Setter
    public static class ScanInstance {

        private int minAttenuationDb;

        private int secondsSinceLastScan;

        private int typicalAttenuationDb;
    }
}
