package com.example.cashregistor;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity implements
        HistoryViewAdapter.HistoryClickListener{
    RecyclerView historyRecyclerList;

    int currentPosition;
    //String pName;
    //String pPrice;
    //int pQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyRecyclerList = findViewById(R.id.HistoryRecyclerList);

        HistoryViewAdapter historyAdapter = new HistoryViewAdapter(
                ((MyApp)getApplication()).myservice.historyList, this  );
        historyRecyclerList.setAdapter(historyAdapter);
        historyRecyclerList.setLayoutManager(new GridLayoutManager(this,1));
        //productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        historyAdapter.listener = this;
    }



    @Override
    public void historySelected(int position) {
        currentPosition = position;
        //pName= ((MyApp)getApplication()).myservice.historyList.get(position).productName;
        //pPrice= ((MyApp)getApplication()).myservice.historyList.get(position).totalPrice;
        //pQuantity= Integer.parseInt(((MyApp)getApplication()).myservice.historyList.get(position).quantity);
        Intent i = new Intent(HistoryActivity.this,HistoryDetailActivity.class);
        i.putExtra("position", position);
        startActivity(i);

    }
}