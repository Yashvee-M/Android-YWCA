package com.example.cashregistor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManagerViewActivity extends AppCompatActivity {
    Button historyButton;
    Button restockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manager_view);

        historyButton = findViewById(R.id.HistoryButton);
        historyButton.setOnClickListener(V -> {
            Intent i = new Intent(ManagerViewActivity.this,HistoryActivity.class);
            startActivity(i);
        });

        restockButton = findViewById(R.id.RestockButton);
        restockButton.setOnClickListener(V -> {
            Intent i = new Intent(ManagerViewActivity.this,RestockActivity.class);
            startActivity(i);
        });




    }
}