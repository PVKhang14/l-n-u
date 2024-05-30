package com.example.library.controllers;

import com.example.library.App;
import com.example.library.common.Regex;
import com.example.library.models.Reader;
import com.example.library.services.IReaderService;
import com.example.library.services.ReaderServiceImpl;
import com.example.library.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReaderManagementController implements Initializable {
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextField txtReaderId;
    @FXML
    private TextField txtReaderName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAddress;
    @FXML
    private DatePicker dpDob;
    @FXML
    private TableView<Reader> tbReaders;
    @FXML
    private TableColumn colReaderId;
    @FXML
    private TableColumn colReaderName;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colPhoneNumber;
    @FXML
    private TableColumn colDob;
    @FXML
    private TableColumn colAddress;

    private final IReaderService readerService;

    public ReaderManagementController() {
        this.readerService = new ReaderServiceImpl();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadReaders();
        customDatePicker();
        txtReaderId.setText(readerService.getReaderId());

        btnAdd.setVisible(true);
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
    }

    public void loadReaders() {
        colReaderId.setCellValueFactory(new PropertyValueFactory<>("readerId"));
        colReaderName.setCellValueFactory(new PropertyValueFactory<>("readerName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("readerEmail"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("readerPhone"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("readerDOB"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("readerAddress"));

        tbReaders.setItems(readerService.getAllReaders());
    }


    public void onSelected(MouseEvent mouseEvent) {
        Optional<Reader> selectedReader = Optional.ofNullable(tbReaders.getSelectionModel().getSelectedItem());

        if (selectedReader.isPresent()) {
            txtReaderId.setText(selectedReader.get().getReaderId());
            txtReaderName.setText(selectedReader.get().getReaderName());
            txtEmail.setText(selectedReader.get().getReaderEmail());
            txtPhoneNumber.setText(selectedReader.get().getReaderPhone());
            dpDob.setValue(selectedReader.get().getReaderDOB());
            txtAddress.setText(selectedReader.get().getReaderAddress());

            btnAdd.setVisible(false);
            btnDelete.setVisible(true);
            btnUpdate.setVisible(true);
        }
    }

    public void onClickAdd(ActionEvent actionEvent) {
        String readerId = txtReaderId.getText();
        String readerName = txtReaderName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        String dob = dpDob.getValue().toString();

        if (isNull(readerId, readerName, email, phoneNumber, address, dob)) {
            return;
        }

        if (!isValid(Regex.EMAIL, email)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Email không hợp lệ!");
            return;
        }

        if (!isValid(Regex.PHONE_NUMBER, phoneNumber)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số điện thoại không hợp lệ!");
            return;
        }

        Reader reader = Reader.builder()
                .readerId(readerId)
                .readerName(readerName)
                .readerEmail(email)
                .readerPhone(phoneNumber)
                .readerDOB(dpDob.getValue())
                .readerAddress(address)
                .build();
        readerService.saveReader(reader);

        tbReaders.setItems(readerService.getAllReaders());


    }

    public void onClickDelete(ActionEvent actionEvent) {
        Optional<Reader> selectedReader = Optional.ofNullable(tbReaders.getSelectionModel().getSelectedItem());

        if (selectedReader.isPresent() && AlertUtil.showConfirmation("Bạn có chắc chắn muốn xoá không?")) {
            readerService.deleteReader(selectedReader.get());
            tbReaders.setItems(readerService.getAllReaders());
        }
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        Optional<Reader> selectedReader = Optional.ofNullable(tbReaders.getSelectionModel().getSelectedItem());

        if (selectedReader.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng chọn độc giả cần cập nhật!");
            return;
        }

        String readerId = txtReaderId.getText();
        String readerName = txtReaderName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        String dob = dpDob.getValue().toString();

        if (isNull(readerId, readerName, email, phoneNumber, address, dob)) {
            return;
        }

        if (!isValid(Regex.EMAIL, email)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Email không hợp lệ!");
            return;
        }

        if (!isValid(Regex.PHONE_NUMBER, phoneNumber)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số điện thoại không hợp lệ!");
            return;
        }

        Reader reader = Reader.builder()
                .readerId(readerId)
                .readerName(readerName)
                .readerEmail(email)
                .readerPhone(phoneNumber)
                .readerDOB(dpDob.getValue())
                .readerAddress(address)
                .build();

        readerService.saveReader(reader);
        tbReaders.setItems(readerService.getAllReaders());
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật độc giả thành công!");
    }

    public void onClickRefresh(ActionEvent actionEvent) {
        txtAddress.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        txtReaderName.clear();
        dpDob.setValue(null);
        tbReaders.getSelectionModel().clearSelection();
        txtReaderId.setText(readerService.getReaderId());

        btnAdd.setVisible(true);
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
    }

    private boolean isNull(Object... o) {
        for (Object obj : o) {
            if (obj == null || obj.toString().isEmpty()) {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng điền đầy đủ thông tin!");
                return true;
            }
        }
        return false;
    }

    private boolean isValid(String regex, String input) {
        return input.matches(regex);
    }

    private void customDatePicker() {
        dpDob.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });
    }

    public void onSearch(KeyEvent keyEvent) {
        String keyword = txtSearch.getText();

        if (keyword.isEmpty()) {
            tbReaders.setItems(readerService.getAllReaders());
            return;
        } else {
            tbReaders.setItems(readerService.getAllReaders().filtered(reader -> {
// search by name, id, email, phone number, address
                return reader.getReaderName().toLowerCase().contains(keyword.toLowerCase()) ||
                        reader.getReaderId().toLowerCase().contains(keyword.toLowerCase()) ||
                        reader.getReaderEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                        reader.getReaderPhone().toLowerCase().contains(keyword.toLowerCase()) ||
                        reader.getReaderAddress().toLowerCase().contains(keyword.toLowerCase());

            }));
        }
    }

    public void onClickHistory(ActionEvent actionEvent) {
        Optional<Reader> selectedReader = Optional.ofNullable(tbReaders.getSelectionModel().getSelectedItem());

        selectedReader.ifPresent(reader -> {
            BorrowHistoryController.setReaderId(reader.getReaderId());
            try {
                App.setRootPop("BorrowHistoryFrm", "Lịch sử mượn sách", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
