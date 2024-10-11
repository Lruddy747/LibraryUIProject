package com.example.libraryui;

public class LibInventory {

    int bookID;
    String bookName;
    String bookAuthor;
    String isAvailable;

    public LibInventory(int bookID, String bookName, String bookAuthor, String isAvailable) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.isAvailable = isAvailable;
    }
    public int getBookID() {
        return bookID;
    }
    public String getBookName() {
        return bookName;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public String getIsAvailable() {
        return isAvailable;
    }


}
