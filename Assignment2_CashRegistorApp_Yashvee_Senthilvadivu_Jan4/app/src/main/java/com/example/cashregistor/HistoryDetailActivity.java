package com.example.cashregistor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class HistoryDetailActivity extends AppCompatActivity {
    TextView historyDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_detail);

        historyDetailTextView = findViewById(R.id.historyDetailTextView);

        Intent intent = getIntent();

        // Get the extras from the intent using the same keys

        int position = intent.getIntExtra("position", 0);

        String pName= ((MyApp)getApplication()).myservice.historyList.get(position).productName;
        String totalPrice= ((MyApp)getApplication()).myservice.historyList.get(position).totalPrice;
        String pQuantity= ((MyApp)getApplication()).myservice.historyList.get(position).quantity;
        String pDate= ((MyApp)getApplication()).myservice.historyList.get(position).purchaseDate;
        String currentHistory = "Product : "+pName+"\n"+"Quantity : "+pQuantity+"\n"+"Price : "+totalPrice+"\n"+"Purchase Date : "+pDate;
        historyDetailTextView.setText(currentHistory);




    }
}