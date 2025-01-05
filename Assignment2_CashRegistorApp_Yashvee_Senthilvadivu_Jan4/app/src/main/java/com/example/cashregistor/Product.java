package com.example.cashregistor;

import java.io.Serializable;

public class Product implements Serializable {
    String productName;
    String quantity;
    String price;

    public Product(String pn, String q, String price) {
        this.productName = pn;
        this.quantity = q;
        this.price = price;
    }
}
