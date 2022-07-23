package com.chag.ptr.cocoa.json.parser.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class DailyResult {

    @SerializedName("d")
    private String date;

    @SerializedName("cn")
    private int contactNum = 0;

    @SerializedName("ss")
    private float scoreSum;

    @SerializedName("cl")
    private List<ContactInfo> contactInfoList = new ArrayList<>();

    @Getter
    @Setter
    public static class ContactInfo {

        @SerializedName("cm")
        private int contactMinutes;

        @SerializedName("miad")
        private int minAttenuationDb = Integer.MAX_VALUE;

        @SerializedName("maad")
        private int maxAttenuationDb = Integer.MIN_VALUE;

        public void addContactMinutes(int contactMinutes) {
            this.contactMinutes += contactMinutes;
        }

        public void setMinAttenuationDb(int minAttenuationDb) {
            this.minAttenuationDb = Math.min(this.minAttenuationDb, minAttenuationDb);
        }

        public void setMaxAttenuationDb(int maxAttenuationDb) {
            this.maxAttenuationDb = Math.max(this.maxAttenuationDb, maxAttenuationDb);
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd(E)", Locale.JAPANESE);

    public void setDate(LocalDate date) {
        this.date = FORMATTER.format(date);
    }

    public void incrContactNum() {
        ++contactNum;
    }

    public void addContactInfo(ContactInfo contactInfo) {
        contactInfoList.add(contactInfo);
    }
}
