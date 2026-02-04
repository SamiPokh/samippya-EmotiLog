package com.example.samippya_emotilog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmoticonLog {
    private String emoticon;
    private long timestamp;

    public EmoticonLog(String emoticon) {
        this.emoticon = emoticon;
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor for manual timestamp if needed
    public EmoticonLog(String emoticon, long timestamp) {
        this.emoticon = emoticon;
        this.timestamp = timestamp;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}