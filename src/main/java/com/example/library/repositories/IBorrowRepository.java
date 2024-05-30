package com.example.library.repositories;

import com.example.library.models.Borrow;
import javafx.collections.ObservableList;

public interface IBorrowRepository {
    ObservableList<Borrow> getBorrowByReaderId(String readerId);
    ObservableList<Borrow> getAllBookBorrowed();
    void returnBook(String borrowId);
    void save(Borrow borrow);
    int getTotalBorrow();
    int getTotalLate();
    int getTotalReturn();

    int getTotalBorrowByReaderId(String readerId);
}
