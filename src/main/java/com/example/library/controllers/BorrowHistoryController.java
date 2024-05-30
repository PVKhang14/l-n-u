package com.example.library.controllers;

import com.example.library.models.Borrow;
import com.example.library.services.BorrowServiceImpl;
import com.example.library.services.IBorrowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BorrowHistoryController implements Initializable {
    @FXML
    private TableColumn colDueDate;
    @FXML
    private TableView<Borrow> tbBorrows;
    @FXML
    private TableColumn colBorrowId;
    @FXML
    private TableColumn colBookName;
    @FXML
    private TableColumn colBorrowDate;
    @FXML
    private TableColumn colReturnDate;

    @Setter
    private static String readerId;
    private final IBorrowService borrowService;

    public BorrowHistoryController() {
        this.borrowService = new BorrowServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBorrows();
    }

    private void loadBorrows() {
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        tbBorrows.setItems(borrowService.getBorrowByReaderId(readerId));

    }

    public void onClickReturn(ActionEvent actionEvent) {
        Optional<Borrow> selectedBorrow = Optional.ofNullable(tbBorrows.getSelectionModel().getSelectedItem());

        selectedBorrow.ifPresent(borrow -> {
            borrowService.returnBook(borrow);
            tbBorrows.setItems(borrowService.getBorrowByReaderId(readerId));
        });
    }
}
