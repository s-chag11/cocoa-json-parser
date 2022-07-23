package com.chag.ptr.cocoa.json.parser.service;

import com.chag.ptr.cocoa.json.parser.dto.DailyResult;
import com.chag.ptr.cocoa.json.parser.dto.DailySummary;
import com.chag.ptr.cocoa.json.parser.dto.ExposureWindow;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ParseService {

    /**
     * Gson instances
     */
    private static final Gson GSON = new Gson();
    private static final Gson GSON_UPPER_CAMEL_CASE = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    /**
     * Parse method
     *
     * @param json
     * @return
     */
    public String parse(String json) {
        if (json == null || json.isBlank()) {
            return "{}";
        }

        // Parse to JsonObject
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);

        // Prepare resultMap
        Map<Long, DailyResult> resultMap = new HashMap<>();

        // Set exposure_windows
        computeDailyResult(ExposureWindow.class, "exposure_windows", jsonObject, o -> {
            DailyResult rs = resultMap.computeIfAbsent(o.getDateMillisSinceEpoch(), k -> new DailyResult());
            // Contact number
            rs.incrContactNum();
            // Contact info
            DailyResult.ContactInfo contactInfo = new DailyResult.ContactInfo();
            for (ExposureWindow.ScanInstance scanInstance : o.getScanInstances()) {
                contactInfo.addContactMinutes(scanInstance.getSecondsSinceLastScan() / 60);
                contactInfo.setMinAttenuationDb(scanInstance.getMinAttenuationDb());
                contactInfo.setMaxAttenuationDb(scanInstance.getTypicalAttenuationDb());
            }
            rs.addContactInfo(contactInfo);
        });

        // Set daily_summaries
        computeDailyResult(DailySummary.class, "daily_summaries", jsonObject, o -> {
            DailyResult rs = resultMap.computeIfAbsent(o.getDateMillisSinceEpoch(), k -> new DailyResult());
            // Score sum
            rs.setScoreSum(o.getDaySummary().getScoreSum());
        });

        // Convert Map to List
        List<DailyResult> results = new ArrayList<>();
        LocalDate current = LocalDate.now();
        for (Map.Entry<Long, DailyResult> entry : resultMap.entrySet()) {
            LocalDate targetDate = LocalDate.ofInstant(Instant.ofEpochMilli(entry.getKey()), ZoneId.systemDefault());
            if (targetDate.isBefore(current.minusDays(14))) {
                // not showing data before 14 days
                continue;
            }
            entry.getValue().setDate(targetDate);
            results.add(entry.getValue());
        }

        // Sort
        List<DailyResult> sorted = results.stream().sorted(Comparator.comparing(DailyResult::getDate).reversed()).collect(Collectors.toList());

        return GSON.toJson(sorted);
    }

    /**
     * Compute daily result
     *
     * @param clazz
     * @param field
     * @param json
     * @param consumer
     * @param <T>
     */
    private <T> void computeDailyResult(Class<T> clazz, String field, JsonObject json, Consumer<T> consumer) {
        // Get objects' list
        List<T> list = GSON_UPPER_CAMEL_CASE.fromJson(
                json.get(field).getAsJsonArray(), TypeToken.getParameterized(ArrayList.class, clazz).getType());
        // Compute
        list.forEach(consumer);
    }
}
