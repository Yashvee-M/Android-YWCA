package com.example.cashregistor;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {
    EditText productName;
    EditText productQuantity;
    EditText productPrice;
    Button save;
    ProductServiceClass serviceClass;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productName = findViewById(R.id.ProductNameText);
        productQuantity = findViewById(R.id.ProductQuantityText);
        productPrice = findViewById(R.id.ProductPriceText);
        save = findViewById(R.id.SaveButton);

        serviceClass = ((MyApp)getApplication()).myservice;
        sharedPref = this.getSharedPreferences("allProducts", Context.MODE_PRIVATE);

        save.setOnClickListener(v -> {
            if(productName.getText().toString().isEmpty()||productQuantity.getText().toString().isEmpty()||productPrice.getText().toString().isEmpty()){
                // Show a message if any field is empty
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                Product newProduct1 = new Product(productName.getText().toString(), productQuantity.getText().toString(), productPrice.getText().toString());
                ((MyApp) getApplication()).myservice.productlist.add(newProduct1);
                // Optional: Show a confirmation message
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
                productName.setText("");
                productQuantity.setText("");
                productPrice.setText("");
                updateTheListInStorage();
            }
        });





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