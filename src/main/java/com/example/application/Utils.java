package com.example.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class Utils {
    static String url = "jdbc:mysql://localhost:3306/person?allowPublicKeyRetrieval=true&useSSL=false";
    static String user = "root";
    static String _password = "Colacion#324265";


    public static void showBooks(ActionEvent event, int id, String title, String author, int year, int pages){

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet results = null;



    }




    }


