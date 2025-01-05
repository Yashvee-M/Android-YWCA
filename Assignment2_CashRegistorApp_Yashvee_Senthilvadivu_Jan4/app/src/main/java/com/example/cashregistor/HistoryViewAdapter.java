package com.example.cashregistor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>{
    interface HistoryClickListener {
        void historySelected(int position);
    }
    ArrayList<History> historyList;
    Context context;
    HistoryClickListener listener;
    HistoryViewAdapter(ArrayList<History> l, Context c){
        historyList = l;
        context = c;
    }

    @NonNull
    @Override
    public HistoryViewAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewAdapter.HistoryViewHolder holder,
                                 int position) {
        holder.productText.setText(historyList.get(position).productName);
        holder.priceText.setText(historyList.get(position).totalPrice);
        holder.quantityText.setText(historyList.get(position).quantity);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView productText;
        TextView priceText;
        TextView quantityText;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            productText =  itemView.findViewById(R.id.productTextList);
            priceText = itemView.findViewById(R.id.priceTextList);
            quantityText = itemView.findViewById(R.id.quantityTextList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // do nothing here
                    // just notify the activity
                    listener.historySelected(getAdapterPosition());
                }
            });

        }
    }
}
