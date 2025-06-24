package com.example.cashregistor;

import java.util.ArrayList;

public class ProductServiceClass {
    ArrayList<Product> productlist = new ArrayList<>(0);
    ArrayList<History> historyList = new ArrayList<>(0);


    void addNewTodo(Product newproduct){
        productlist.add(newproduct);
    }

}



