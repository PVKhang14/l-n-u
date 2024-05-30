package com.example.library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ListView<String> lstMenu;
    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstMenu.getItems().addAll("Quản lý sách", "Quản lý độc giả", "Quản lý mượn trả", "Thống kê");
        lstMenu.getSelectionModel().selectFirst();
        loadPane(lstMenu.getSelectionModel().getSelectedItem());
    }

    public void onSelected(MouseEvent mouseEvent) {
        String selectedItem = lstMenu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            loadPane(selectedItem);
        }
    }

    private void loadPane(String selected) {
        String frm = "";

        switch (selected) {
            case "Quản lý sách":
                frm = "/com/example/library/BookManagementFrm.fxml";
                break;
            case "Quản lý độc giả":
                frm = "/com/example/library/ReaderManagementFrm.fxml";
                break;
            case "Quản lý mượn trả":
                frm = "/com/example/library/BorrowManagementFrm.fxml";
                break;
            case "Thống kê":
                frm = "/com/example/library/StaticFrm.fxml";
                break;

            default:
                break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
            pane.getChildren().clear();
            AnchorPane newPane = loader.load();
            pane.getChildren().add(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
