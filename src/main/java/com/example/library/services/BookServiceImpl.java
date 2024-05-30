package com.example.library.services;

import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.repositories.AuthorRepositoryImpl;
import com.example.library.repositories.BookRepositoryImpl;
import com.example.library.repositories.IAuthorRepository;
import com.example.library.repositories.IBookRepository;
import javafx.collections.ObservableList;

public class BookServiceImpl implements IBookService {

    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;

    public BookServiceImpl() {
        this.bookRepository = new BookRepositoryImpl();
        this.authorRepository = new AuthorRepositoryImpl();
    }

    @Override
    public ObservableList<Book> getAllBook() {
        return bookRepository.getAllBook();
    }


    @Override
    public ObservableList<String> getAllCategoryName() {
        return bookRepository.getAllCategoryName();
    }

    @Override
    public ObservableList<String> getAllBookId() {
        return bookRepository.getAllBookId();
    }

    @Override
    public ObservableList<Author> getAllAuthors() {
        return authorRepository.getAllAuthor();
    }

    @Override
    public String getBookNameById(String bookId) {
        return bookRepository.getBookNameById(bookId);
    }

    @Override
    public void saveBook(Book book) {
        setBook(book);
        bookRepository.save(book);

    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void updateBook(Book book) {
        setBook(book);
        bookRepository.save(book);

    }

    @Override
    public String getBookId() {
        return bookRepository.getBookId();
    }

    private void setBook(Book book) {
        String categoryId = bookRepository.getCategoryIdByName(book.getCategory());
        String authorId = authorRepository.getAuthorIdByName(book.getAuthor());

        if (categoryId == null) {
            bookRepository.saveCategory(book.getCategory());
            categoryId = bookRepository.getCategoryIdByName(book.getCategory());
        }

        if(authorId == null){
            bookRepository.saveAuthor(book.getAuthor());
            authorId = authorRepository.getAuthorIdByName(book.getAuthor());
        }

        book.setCategory(categoryId);
        book.setAuthor(authorId);

        System.out.println(book);

    }

}
