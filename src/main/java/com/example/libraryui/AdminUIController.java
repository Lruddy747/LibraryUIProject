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

public class AdminUIController {

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
    Button ConfirmButton;
    @FXML
    TextField CheckBookID;
    @FXML
    TextField CheckTextBook;
    @FXML
    TextField CheckTextAuthor;
    @FXML
    TextField CheckTextAvailable;

    ObservableList<LibInventory> inventoryList = FXCollections.observableArrayList();




    public void Connect() throws ClassNotFoundException, SQLException {
        CheckBookID.clear();
        CheckTextBook.clear();
        CheckTextAuthor.clear();
        CheckTextAvailable.clear();

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

    public void handleAddButton() throws SQLException, ClassNotFoundException {
        String name = CheckTextBook.getText();
        int ID = Integer.parseInt(CheckBookID.getText());
        String Author = CheckTextAuthor.getText();

        int Available = Integer.parseInt(CheckTextAvailable.getText());

        //int Available = Integer.parseInt(CheckTextAvailable.getText());
        addBook(name, ID, Author, Available);
    }

    public void addBook(String name, int ID, String Author, int Available) throws ClassNotFoundException, SQLException {

        try {

            BIDCOL.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBookID()).asObject());
            BNCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookName()));
            ALCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookAuthor()));
            AvailableCOL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailable()));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "WhiteTruck1304");
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO inventory (bookID, bookName, authorLname, isAvailable) VALUES (?, ?, ?, ?)";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, Author);


            preparedStatement.setInt(4, Available);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            String isAvailable;
            switch(CheckTextAvailable.getText()){
                case "1":
                    isAvailable = "yes";
                    inventoryList.add(new LibInventory(ID, name, Author, isAvailable));
                    LibraryTable.setItems(inventoryList);
                    break;
                case "0":
                    isAvailable = "no";
                    inventoryList.add(new LibInventory(ID, name, Author, isAvailable));
                    LibraryTable.setItems(inventoryList);
                    break;
            }

            connection.close();

            CheckBookID.clear();
            CheckTextBook.clear();
            CheckTextAuthor.clear();
            CheckTextAvailable.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}