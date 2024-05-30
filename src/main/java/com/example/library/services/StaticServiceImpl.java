package com.example.library.services;

import com.example.library.repositories.*;

public class StaticServiceImpl implements IStaticService {


    private final IReaderRepository readerRepository;
    private final IBorrowRepository borrowRepository;
    private final IBookRepository bookRepository;

    public StaticServiceImpl() {
        readerRepository = new ReaderRepositoryImpl();
        borrowRepository = new BorrowRepositoryImpl();
        bookRepository = new BookRepositoryImpl();
    }

    @Override
    public int getTotalBook() {
        return bookRepository.getTotalBook();
    }

    @Override
    public int getTotalReader() {
        return readerRepository.getTotalReader();
    }

    @Override
    public int getTotalBorrow() {
        return borrowRepository.getTotalBorrow();
    }

    @Override
    public int getTotalLate() {
        return borrowRepository.getTotalLate();
    }

    @Override
    public int getTotalReturn() {
        return borrowRepository.getTotalReturn();
    }

}
