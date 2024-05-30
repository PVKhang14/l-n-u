package com.example.library.services;

import com.example.library.models.Author;
import com.example.library.models.Book;
import javafx.collections.ObservableList;

public interface IBookService {
    ObservableList<Book> getAllBook();
    ObservableList<String> getAllCategoryName();
    ObservableList<String> getAllBookId();
    ObservableList<Author> getAllAuthors();
    String getBookNameById(String bookId);
    void saveBook(Book book);
    void deleteBook(Book book);
    void updateBook(Book book);
    String getBookId();
}
