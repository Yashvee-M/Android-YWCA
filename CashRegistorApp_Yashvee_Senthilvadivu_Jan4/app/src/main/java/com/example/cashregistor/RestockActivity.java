package com.example.cashregistor;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Objects;

public class RestockActivity extends AppCompatActivity implements
        ViewAdapter.ProductClickListener{
    EditText newQuantity;
    Button ok;
    Button cancel;

    RecyclerView restockRecyclerList;

    FloatingActionButton addButton;
    FloatingActionButton deleteButton;
    SharedPreferences sharedPref;

    int currentPosition;
    String pName = "";
    int pQuantity;
    int currentQuantity;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        newQuantity = findViewById(R.id.NewQuantityText);
        ok = findViewById(R.id.OKButton);
        cancel = findViewById(R.id.CancelButton);
        addButton = findViewById(R.id.AddNewProduct);
        deleteButton = findViewById(R.id.DeleteProduct);

        restockRecyclerList = findViewById(R.id.RestockRecyclerList);
        sharedPref = this.getSharedPreferences("allProducts", Context.MODE_PRIVATE);


        ViewAdapter adapter = new ViewAdapter(
                ((MyApp)getApplication()).myservice.productlist, this  );
        restockRecyclerList.setAdapter(adapter);
        restockRecyclerList.setLayoutManager(new GridLayoutManager(this,1));
        //productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter.listener = this;

        ok.setOnClickListener(V -> {
            if (!Objects.equals(pName, "")){
                currentQuantity = Integer.parseInt(String.valueOf(newQuantity.getText()));
                currentQuantity += pQuantity;
                ((MyApp)getApplication()).myservice.productlist.get(currentPosition).quantity = String.valueOf(currentQuantity);
                updateTheListInStorage();
                adapter.notifyDataSetChanged();
                currentQuantity = 0;
                pName = "";
                newQuantity.setText("");
            }
            else{
                Toast.makeText(this,"Should select Item!",Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestockActivity.this,AddProductActivity.class);
                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                if (!Objects.equals(pName, "")){
                    // add an alert
                    new AlertDialog.Builder(context).setTitle("Delete!!!").
                            setMessage("Are You Sure You Want To Delete this Product?"+pName).
                            setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // on delete
                                    ((MyApp)getApplication()).myservice.productlist.remove(currentPosition);
                                    updateTheListInStorage();
                                    //updateTheListInStorage();
                                    adapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("No",null).show();
                    pName = "";
                }
                else{
                    Toast.makeText(context,"Should select Product for delete!",Toast.LENGTH_SHORT).show();
                }



            }
        });




    }


    @Override
    public void productSelected(int position) {
        currentPosition = position;
        pName= ((MyApp)getApplication()).myservice.productlist.get(position).productName;
        //pPrice= ((MyApp)getApplication()).myservice.productlist.get(position).price;
        pQuantity= Integer.parseInt(((MyApp)getApplication()).myservice.productlist.get(position).quantity);

    }

    void updateTheListInStorage(){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        // convert list to string(JSON)
        // convert string(JSON) to list
        //encoding
        String json = gson.toJson( ((MyApp)getApplication()).myservice.productlist);
        editor.putString("allProducts",json);
        editor.apply();
    }
}