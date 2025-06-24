package com.example.cashregistor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ProductViewHolder>{
    interface ProductClickListener {
        void productSelected(int position);
    }
    ArrayList<Product> list;
    Context context;
    ProductClickListener listener;
    ViewAdapter(ArrayList<Product> l, Context c){
        list = l;
        context = c;
    }

    @NonNull
    @Override
    public ViewAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.ProductViewHolder holder,
                                 int position) {
        holder.productText.setText(list.get(position).productName);
        holder.priceText.setText(list.get(position).price);
        holder.quantityText.setText(list.get(position).quantity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productText;
        TextView priceText;
        TextView quantityText;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productText =  itemView.findViewById(R.id.productTextList);
            priceText = itemView.findViewById(R.id.priceTextList);
            quantityText = itemView.findViewById(R.id.quantityTextList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // do nothing here
                    // just notify the activity
                    listener.productSelected(getAdapterPosition());
                }
            });

        }
    }
}
