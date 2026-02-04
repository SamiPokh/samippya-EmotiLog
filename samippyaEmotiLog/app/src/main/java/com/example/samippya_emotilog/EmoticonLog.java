package com.example.samippya_emotilog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * EmoticonLog represents one emotion entry.
 * It stores the emotion, its name, and the time it was logged.
 */
public class EmoticonLog {
    private String emoticon;
    private String emotionName;
    private long timestamp;

    // Creates a new log entry and saves the current time
    public EmoticonLog(String emoticon, String emotionName) {
        this.emoticon = emoticon;
        this.emotionName = emotionName;
        this.timestamp = System.currentTimeMillis();
    }

    public String getEmoticon() {
        return emoticon;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Returns the time the emotion was logged
    public String getFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    // Returns the date the emotion was logged
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    // Returns a short string used for displaying logs
    public String getLogDisplay() {
        return emoticon + " " + emotionName + " " + getFormattedTime();
    }
}