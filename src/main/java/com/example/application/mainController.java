package com.example.application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField id;
    @FXML
    private TextField author;
    @FXML
    private TextField title;
    @FXML
    private TextField pages;
    @FXML
    private TextField year;
    @FXML
    private TableView<Books> books_table;
    @FXML
    private TableColumn<Books, Integer> colId;
    @FXML
    private TableColumn<Books, String> colTitle;
    @FXML
    private TableColumn<Books, Integer> colPages;
    @FXML
    private TableColumn<Books, Integer> colYear;
    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    String query = null;
    Connection conn;

    Books books = null;
    ObservableList<Books> booklist = FXCollections.observableArrayList();


    @FXML
    private void handleButtonAction(Event event) throws SQLException {

    getConnection();
        try {
            if (event.getSource() == btnInsert) {
                insertRecord();
            } else if (event.getSource() == btnUpdate) {
                updateRecord();
            } else if (event.getSource() == btnDelete) {
                deleteButton();
            } else {
                System.out.println("im a failure");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Connection getConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/person?allowPublicKeyRetrieval=true&useSSL=false", "root", "Colacion#324265");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public ObservableList<Books> refreshTabl() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        query = "SELECT * FROM books";
        preparedStatement = conn.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        try {

            while (resultSet.next()) {
                booklist.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year"),
                        resultSet.getInt("pages")));
                books_table.setItems(booklist);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books_table.getItems();
    }

        public void showBooks () throws SQLException {
            getConnection();
//        System.out.println("Bitch please");
            ObservableList<Books> list = refreshTabl();
            System.out.println(list);
            colId.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
            colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
            colYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
            colPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));

            books_table.setItems(list);
        }

        private boolean iExist(String title) throws SQLException {
            getConnection();
            String query =  "SELECT * FROM books WHERE title = '" + title + "'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs.next();
        }


        private void insertRecord () throws SQLException {
            try {
                getConnection();


                PreparedStatement psInsert = null;
                ResultSet results = null;

                System.out.println("A");
                if(iExist(title.getText())){
                    System.out.println("Book already exists");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("book created");
                    alert.show();
                }else {
                    //WHERE(title, author, pages, year)
                    psInsert = conn.prepareStatement("INSERT INTO books VALUES(?, ?, ?, ?, ?)");
                    psInsert.setString(1, String.valueOf(Integer.parseInt(id.getText())));
                    psInsert.setString(2, title.getText());
                    psInsert.setString(3, author.getText());
                    psInsert.setInt(4, Integer.parseInt(pages.getText()));
                    psInsert.setInt(5, Integer.parseInt(year.getText()));
//                    books_table.setItems(booklist);
                    psInsert.executeUpdate();

                }
            } catch(SQLException e){
                e.printStackTrace();
            }
            showBooks();
        }


        private void updateRecord () throws SQLException {

            try {
                getConnection();
                PreparedStatement psInsert = null;
                ResultSet results = null;


                psInsert = conn.prepareStatement("UPDATE books SET title = ?, author = ?, pages = ? , year = ? WHERE id = ?");
                psInsert.setString(1, title.getText());
                psInsert.setString(2, author.getText());
                psInsert.setInt(3, Integer.parseInt(pages.getText()));
                psInsert.setInt(4, Integer.parseInt(year.getText()));
                psInsert.setString(5, String.valueOf(Integer.parseInt(id.getText())));
                psInsert.executeUpdate();

                showBooks();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }


        private void deleteButton () throws SQLException {

        try {
            getConnection();
            PreparedStatement psDelete = null;
            ResultSet results = null;

            psDelete = conn.prepareStatement("DELETE FROM books WHERE id = ?");
            psDelete.setString(1, String.valueOf(Integer.parseInt(id.getText())));
            psDelete.executeUpdate();

            showBooks();
        } catch (SQLException e){
            e.printStackTrace();
        }
        }

}
