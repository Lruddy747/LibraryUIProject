package com.example.libraryui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;
import java.awt.*;
import java.io.IOException;
import java.sql.*;

import static java.awt.Color.black;

public class LibraryUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //standard login for admin
        Admin admin = new Admin("STAND", 1, 1, 1);
        //standard login for customer
        Customer customer = new Customer("CUST", 1, 1);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(10);
        pane.setVgap(10);

        TextField name = new TextField();
        Button button = new Button("Confirm");
        TextField ID = new TextField();


        pane.add(name, 0, 0);
        pane.add(button, 1, 0);
        pane.add(ID, 0, 1);

        //standard confirmation for login
        button.setOnAction(e -> {
            if(name.getText().equals(admin.name) &&  ID.getText().equals(Integer.toString(admin.EID))) {
                button.setText("");
            }
            else if(name.getText().equals(customer.name) &&  ID.getText().equals(Integer.toString(customer.CID))) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
                Stage stage2 = (Stage) button.getScene().getWindow();
                try {
                    stage2.setScene(new Scene(loader.load()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        Scene scene = new Scene(pane,500,500);
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Admin admin = new Admin("STAND", 0, 0, 0);



        launch();
    }

}