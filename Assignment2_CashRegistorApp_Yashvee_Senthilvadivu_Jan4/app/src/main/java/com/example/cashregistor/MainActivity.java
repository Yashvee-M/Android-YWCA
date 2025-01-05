package com.example.cashregistor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        ViewAdapter.ProductClickListener{
    TextView productTypeText;
    TextView totalText;
    TextView quantityText;
    RecyclerView productList;
    Button buyButton;
    Button managerButton;
    int currentPosition;

    String currentInput;
    String Input="";
    String pName;
    String pPrice;
    int pQuantity;
    Float total;
    ProductServiceClass serviceClass;
    SharedPreferences sharedPref;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceClass = ((MyApp)getApplication()).myservice;

        sharedPref = this.getSharedPreferences("allProducts", Context.MODE_PRIVATE);
        // MODE_PRIVATE is overwriting the exsisting file
        //MODE_APPEND is continue writing -- only works with wrting to a file
        readFromSharedPreferences();


        if(((MyApp)getApplication()).myservice.productlist.isEmpty()) {
            Product newProduct1 = new Product("Pants", "10", "20.44");
            ((MyApp) getApplication()).myservice.productlist.add(newProduct1);
            Product newProduct2 = new Product("Shoes", "100", "10.44");
            ((MyApp) getApplication()).myservice.productlist.add(newProduct2);
            Product newProduct3 = new Product("Hats", "30", "5.9");
            ((MyApp) getApplication()).myservice.productlist.add(newProduct3);
            updateTheListInStorage();
        }
        productList = findViewById(R.id.productList);
        quantityText = findViewById(R.id.QuantityText);
        productTypeText = findViewById(R.id.selectedProductText);
        totalText = findViewById(R.id.totalAmountText);
        buyButton = findViewById(R.id.ButtonBuy);
        managerButton = findViewById(R.id.ManagerButton);


        ViewAdapter adapter = new ViewAdapter(
                ((MyApp)getApplication()).myservice.productlist, this  );
        productList.setAdapter(adapter);
        productList.setLayoutManager(new GridLayoutManager(this,1));
        //productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter.listener = this;

        adapter.notifyDataSetChanged();




        //Set the id's for digitButtons
        int[] digitIds = {R.id.Button0, R.id.Button1, R.id.Button2, R.id.Button3,
                R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7,
                R.id.Button8, R.id.Button9};
        //OnClick event for digitId's pass perform onDigitClicked function for each digit pressed
        for (int id : digitIds){
            findViewById(id).setOnClickListener(this::onDigitClicked);
        }

        //OnClickListener for buyButton
        buyButton.setOnClickListener(V -> {
            if((((String) productTypeText.getText()).equals("Product Type"))) {
                Toast.makeText(this,"All fields Should be selected!!!",Toast.LENGTH_LONG).show();
            }
            else if((((String) quantityText.getText()).equals("Quantity"))){
                Toast.makeText(this,"Quantity Should be selected!!!",Toast.LENGTH_LONG).show();
            }

            else{
                if (Integer.parseInt(Input) <= pQuantity ){
                    ((MyApp)getApplication()).myservice.productlist.get(currentPosition).quantity = String.valueOf(pQuantity-(Integer.parseInt(Input)));
                    adapter.notifyDataSetChanged();
                    updateTheListInStorage();

                    //Add a History for each purchase
                    Date date = new Date();
                    History newHistory = new History(pName,Input,total.toString(),date.toString());
                    Log.d("history", "newhistory"+newHistory.productName+newHistory.quantity+newHistory.totalPrice+newHistory.purchaseDate);

                    ((MyApp)getApplication()).myservice.historyList.add(newHistory);

                    //Toast.makeText(this,"Thank you for buying",Toast.LENGTH_LONG).show();

                    new AlertDialog.Builder(this)
                            .setTitle("Thank you for your purchase!")
                            .setMessage("Your Purchase Details:\n" +
                                    "Item: " + pName + "\n" +
                                    "Price: $" + total + "\n" +
                                    "Quantity: " + Input + "\n\n")
                            .setNegativeButton("OK", null)  // Just dismiss the dialog on "No"
                            .show();

                    clear();
                }
                else{
                    Toast.makeText(this,"No enough quantity in the stock!!",Toast.LENGTH_LONG).show();
                    clear();
                }

            }

        });

        //OnClickListener for managerButton to go add items and view history
        managerButton.setOnClickListener(V -> {
            Intent i = new Intent(MainActivity.this,ManagerViewActivity.class);
            startActivity(i);
        });

        //OnClickListener for clearButton to clear all input
        findViewById(R.id.ButtonC).setOnClickListener(V -> {
            clear();
        });

        //Log.d("RecyclerView", "Visibility: " + (productList.getVisibility() == View.VISIBLE ? "VISIBLE" : "GONE"));



    }

    // Handle digit button clicks
    private void onDigitClicked(View view) {
        Button button = (Button) view;
        currentInput = button.getText().toString();

        String currentProductName = getString(R.string.selectedProductPlaceHolder);
        String currentProductText = (String) productTypeText.getText();
        if (currentProductName.equals(currentProductText)) {
            Toast.makeText(this,"Select the product first!",Toast.LENGTH_LONG).show();
        }
        else{
            Input += currentInput;
            quantityText.setText(Input);
            total = Float.parseFloat(pPrice) * Integer.parseInt(Input);
            totalText.setText(String.valueOf(total));
        }


    }


    @Override
    public void productSelected(int position) {
        currentPosition = position;
        pName= ((MyApp)getApplication()).myservice.productlist.get(position).productName;
        pPrice= ((MyApp)getApplication()).myservice.productlist.get(position).price;
        pQuantity= Integer.parseInt(((MyApp)getApplication()).myservice.productlist.get(position).quantity);
        productTypeText.setText(pName);

    }
    public void clear(){
        quantityText.setText(getString(R.string.quantityPlaceHolder));
        Input = "";
        total = (float) 0;
        productTypeText.setText(getString(R.string.selectedProductPlaceHolder));
        totalText.setText(getString(R.string.totalPlaceHolder));
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



    void readFromSharedPreferences() {
        // read one product at a time (string, string, boolean)
        String jsonfromPreferences = sharedPref.getString("allProducts", "");
        Gson gson = new Gson();
        // decoding
        ArrayList<Product> list = gson.fromJson(jsonfromPreferences, new TypeToken<ArrayList<Product>>() {
        }.getType());
        if (list == null) {
            ((MyApp) getApplication()).myservice.productlist = new ArrayList<>(0);
        } else {
            ((MyApp) getApplication()).myservice.productlist = list;
        }
    }


}