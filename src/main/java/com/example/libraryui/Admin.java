package com.example.libraryui;

public class Admin extends Customer{

    int EID;

    public Admin(String name, int age, int CID, int EID) {
        super(name, age, CID);
        this.EID = EID;
    }


}
