package com.example.cashregistor;

public class History {
    String productName;
    String quantity;
    String totalPrice;
    String purchaseDate;


    public History(String pn, String q, String tPrice, String pDate) {
        this.productName = pn;
        this.quantity = q;
        this.totalPrice = tPrice;
        this.purchaseDate = pDate;
    }
}
