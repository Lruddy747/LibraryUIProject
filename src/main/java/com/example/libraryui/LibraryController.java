package com.example.libraryui;

import com.mysql.cj.xdevapi.Column;
import com.mysql.cj.xdevapi.Table;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    Button ConnectToLibrary;
    @FXML
    Label LabelConfirmation;
    @FXML
    TableView<LibInventory> LibraryTable;
    @FXML
    TableColumn<LibInventory, Integer> BIDCOL;
    @FXML
    TableColumn<LibInventory, String> BNCOL;
    @FXML
    TableColumn<LibInventory, String> ALCOL;
    @FXML
    TableColumn<LibInventory, String> AvailableCOL;
    @FXML
    Button CheckoutButton;
    @FXML
    TextField CheckText;

    ObservableList<LibInventory> inventoryList = FXCollections.observableArrayList();




    public void Connect() throws ClassNotFoundException, SQLException {

        BIDCOL.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBookID()).asObject());
        BNCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookName()));
        ALCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookAuthor()));
        AvailableCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailable()));

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "WhiteTruck1304");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT bookID, bookName, authorLName, isAvailable FROM inventory");



        ConnectToLibrary.setVisible(false);
        LabelConfirmation.setVisible(true);



        while(resultSet.next()) {
            int bookID = resultSet.getInt("bookID");
            String bookName = resultSet.getString("bookName");
            String authorLName = resultSet.getString("authorLName");
            String isAvailable = resultSet.getString("isAvailable");

            if(isAvailable.equals("1")){
                isAvailable = "Yes";
            }
            else if(isAvailable.equals("0")){
                isAvailable = "No";
            }

            inventoryList.add(new LibInventory(bookID,bookName, authorLName, isAvailable));
        }

        LibraryTable.setItems(inventoryList);
        connection.close();



    }

    public void Checkout() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "WhiteTruck1304");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT bookID, bookName, authorLName, isAvailable FROM inventory");

        int bookID = resultSet.getInt("bookID");
        String bookName = resultSet.getString("bookName");
        String authorLName = resultSet.getString("authorLName");
        String isAvailable = resultSet.getString("isAvailable");

        if(CheckText.getText().equals(String.valueOf(bookID))){

            isAvailable = "NO";

        }

        AvailableCOL.setText(isAvailable);


    }



}
