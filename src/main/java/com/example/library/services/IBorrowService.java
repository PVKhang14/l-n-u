package com.example.library.services;

import com.example.library.models.Borrow;
import javafx.collections.ObservableList;

public interface IBorrowService {
    ObservableList<Borrow> getBorrowByReaderId(String readerId);
    ObservableList<Borrow> getAllBookBorrowed();
    void returnBook(Borrow borrow);
    void borrowBook(Borrow borrow);

    int getTotalBorrowByReaderId(String readerId);
}
