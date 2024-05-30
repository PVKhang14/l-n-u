package com.example.library.controllers;

import com.example.library.services.IStaticService;
import com.example.library.services.StaticServiceImpl;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class StaticController implements Initializable {
    public Text txtNumberReader;
    public Text txtTotalBorrow;
    public Text txtTotalQuantityBook;
    public Text txtTotalLate;

    private final IStaticService staticService;
    public Text txtNumberReturn;

    public StaticController() {
        staticService = new StaticServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNumberReader.setText(String.valueOf(staticService.getTotalReader()));
        txtTotalBorrow.setText(String.valueOf(staticService.getTotalBorrow()));
        txtTotalQuantityBook.setText(String.valueOf(staticService.getTotalBook()));
        txtTotalLate.setText(String.valueOf(staticService.getTotalLate()));
        txtNumberReturn.setText(String.valueOf(staticService.getTotalReturn()));
    }
}
