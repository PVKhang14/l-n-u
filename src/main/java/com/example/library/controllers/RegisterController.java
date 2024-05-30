package com.example.library.controllers;

import com.example.library.models.Account;
import com.example.library.services.AccountServiceImpl;
import com.example.library.services.IAccountService;
import com.example.library.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField pwMatKhau;
    @FXML
    private PasswordField pwReMatKhau;

    private final IAccountService accountService;
    public RegisterController() {
        accountService = new AccountServiceImpl();
    }
    public void onClickRegister(ActionEvent actionEvent) {
        String username = txtTaiKhoan.getText();
        String password = pwMatKhau.getText();
        String rePassword = pwReMatKhau.getText();
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR,"Lỗi", null, "Tài khoản và mật khẩu không được để trống!");
            return;
        }

        if (!password.equals(rePassword)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR,"Lỗi", null, "Mật khẩu không trùng khớp!");
            return;
        }

        if (accountService.registerAccount(new Account(username, password))){
            AlertUtil.showAlert(Alert.AlertType.INFORMATION,"Thông báo", null, "Đăng ký tài khoản thành công!");
        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR,"Lỗi", null, "Tài khoản đã tồn tại!");
        }
    }
}
