package com.example.libraryui;

public class Customer extends Person{

    int CID;

    public Customer(String name, int age, int CID) {
        super(name, age);
        this.CID = CID;
    }
}
