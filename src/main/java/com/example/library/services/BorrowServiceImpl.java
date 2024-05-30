package com.example.library.services;

import com.example.library.models.Borrow;
import com.example.library.repositories.*;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class BorrowServiceImpl implements IBorrowService {
    private final IBorrowRepository borrowRepository;
    private final IBookRepository bookRepository;
    private final IReaderRepository readerRepository;

    public BorrowServiceImpl() {
        borrowRepository = new BorrowRepositoryImpl();
        bookRepository = new BookRepositoryImpl();
        readerRepository = new ReaderRepositoryImpl();
    }

    @Override
    public ObservableList<Borrow> getBorrowByReaderId(String readerId) {
        return borrowRepository.getBorrowByReaderId(readerId);
    }

    @Override
    public ObservableList<Borrow> getAllBookBorrowed() {
        return borrowRepository.getAllBookBorrowed();
    }

    @Override
    public void returnBook(Borrow borrow) {
        borrowRepository.returnBook(borrow.getBorrowId());
        String bookId = bookRepository.getBookIdByName(borrow.getBookName());
        bookRepository.increaseQuantity(bookId);
    }

    @Override
    public void borrowBook(Borrow borrow) {
        if(borrow.getBorrowId() != null){
            String bookId = bookRepository.getBookIdByName(borrow.getBookName());
            String readerId = readerRepository.getReaderIdByName(borrow.getReaderName());

            borrow.setBookName(bookId);
            borrow.setReaderName(readerId);

            System.out.println("Borrow on service layer: " + borrow);

        }

        borrowRepository.save(borrow);
        bookRepository.decreaseQuantity(borrow.getBookName());
    }

    @Override
    public int getTotalBorrowByReaderId(String readerId) {
        return borrowRepository.getTotalBorrowByReaderId(readerId);
    }


}
