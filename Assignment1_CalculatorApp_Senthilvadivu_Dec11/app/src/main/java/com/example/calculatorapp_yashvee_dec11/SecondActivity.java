package com.example.calculatorapp_yashvee_dec11;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    CalculatorBrain model = new CalculatorBrain();
    TextView historySecondTextViewDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the id of historySecondTV to historySecondTVDisplay
        historySecondTextViewDisplay=findViewById(R.id.historySecondTextView);

        // Get the history as a string from the calculator
        String history = model.getHistoryAsString();

        // Update the TextView to show the history
        historySecondTextViewDisplay.setText(history);
    }
}