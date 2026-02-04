package com.example.samippya_emotilog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Emoticon arrays
    private final String[] emoticons = {
            "😊", "😢", "😰",
            "🎉", "😠", "😨",
            "😴", "🤢", "😲"
    };

    private final String[] emoticonNames = {
            "Happy", "Sad", "Stressed",
            "Excited", "Angry", "Anxious",
            "Tired", "Disgust", "Surprise"
    };

    // Data storage
    private List<EmoticonLog> logs;

    // UI Components
    private LinearLayout summaryContainer;
    private Button viewSummaryButton;
    private Button closeSummaryButton;
    private TextView summaryText;
    private Button clearDataButton; // Button inside summary to clear data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data
        logs = new ArrayList<>();

        // Initialize views
        summaryContainer = findViewById(R.id.summaryContainer);
        viewSummaryButton = findViewById(R.id.viewSummaryButton);
        closeSummaryButton = findViewById(R.id.closeSummaryButton);
        summaryText = findViewById(R.id.summaryText);
        clearDataButton = findViewById(R.id.clearDataButton);

        // Setup UI components
        setupEmoticonButtons();
        setupListeners();
    }

    private void setupEmoticonButtons() {
        LinearLayout emoticonGrid = findViewById(R.id.emoticonGrid);

        // Create 3 rows of 3 buttons each
        for (int row = 0; row < 3; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1f
            );
            rowLayout.setLayoutParams(rowParams);

            for (int col = 0; col < 3; col++) {
                final int index = row * 3 + col;
                Button button = new Button(this);
                button.setText(emoticons[index]);
                button.setTextSize(32f);

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1f
                );
                buttonParams.setMargins(8, 8, 8, 8);
                button.setLayoutParams(buttonParams);

                // Set click listener
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logEmotion(emoticons[index]);
                    }
                });

                rowLayout.addView(button);
            }

            emoticonGrid.addView(rowLayout);
        }
    }

    private void setupListeners() {
        viewSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSummary();
            }
        });

        closeSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSummary();
            }
        });

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logs.clear();
                updateSummaryText();
            }
        });
    }

    private void logEmotion(String emoticon) {
        logs.add(new EmoticonLog(emoticon));

        // If summary is open, update it in real-time
        if (summaryContainer.getVisibility() == View.VISIBLE) {
            updateSummaryText();
        }
    }

    private void showSummary() {
        summaryContainer.setVisibility(View.VISIBLE);
        updateSummaryText();
    }

    private void hideSummary() {
        summaryContainer.setVisibility(View.GONE);
    }

    private void updateSummaryText() {
        if (logs.isEmpty()) {
            summaryText.setText("No logs yet!\n\nStart tracking your emotions by tapping the emoticon buttons.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String today = sdf.format(new Date());

        // 1. Calculate Counts
        Map<String, Integer> emoticonCounts = new HashMap<>();
        for (EmoticonLog log : logs) {
            String emoticon = log.getEmoticon();
            emoticonCounts.put(emoticon, emoticonCounts.getOrDefault(emoticon, 0) + 1);
        }

        StringBuilder summary = new StringBuilder();

        // --- Section 1: Statistics ---
        summary.append("📊 EMOTION STATS\n");
        summary.append("Date: ").append(today).append("\n");
        summary.append("Total Events: ").append(logs.size()).append("\n");
        summary.append("━━━━━━━━━━━━━━━━━━━━\n");

        // Sort by count (descending)
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(emoticonCounts.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String emoticon = entry.getKey();
            int count = entry.getValue();

            // Match symbol to name
            String name = "";
            for(int i=0; i<emoticons.length; i++) {
                if(emoticons[i].equals(emoticon)) name = emoticonNames[i];
            }

            int percentage = (int) ((count / (float) logs.size()) * 100);
            summary.append(emoticon).append(" ").append(name)
                    .append(": ").append(count)
                    .append(" (").append(percentage).append("%)\n");
        }

        // --- Section 2: Detailed History (The Requirement) ---
        summary.append("\n\n📝 DETAILED HISTORY\n");
        summary.append("━━━━━━━━━━━━━━━━━━━━\n");

        // Loop backwards to show newest first
        for (int i = logs.size() - 1; i >= 0; i--) {
            EmoticonLog log = logs.get(i);
            summary.append(log.getFormattedTime())
                    .append("  ")
                    .append(log.getEmoticon())
                    .append("\n");
        }

        summaryText.setText(summary.toString());
    }
}