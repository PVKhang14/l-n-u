package com.example.library.controllers;

import com.example.library.App;
import com.example.library.models.Account;
import com.example.library.services.AccountServiceImpl;
import com.example.library.services.IAccountService;
import com.example.library.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController {
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField pwMatKhau;

    private final IAccountService accountService;

    public LoginController() {
        this.accountService = new AccountServiceImpl();
    }

    public void onClickLogin(ActionEvent actionEvent) throws IOException {
        String username = txtTaiKhoan.getText();
        String password = pwMatKhau.getText();
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tài khoản và mật khẩu không được để trống!");
            return;
        }

        Account account = Account.builder()
                .username(username)
                .password(password)
                .build();
        if (accountService.checkAccount(account)) {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Đăng nhập thành công");
            App.setRoot("DashboardFrm");
        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tài khoản hoặc mật khẩu không đúng!");
        }
    }

    public void onClickRegister(ActionEvent actionEvent) throws IOException {
        App.setRootPop("RegisterFrm", "Đăng ký tài khoản", false);
    }


}
