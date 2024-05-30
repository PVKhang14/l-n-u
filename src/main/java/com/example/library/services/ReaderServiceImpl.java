package com.example.library.services;

import com.example.library.models.Reader;
import com.example.library.repositories.IReaderRepository;
import com.example.library.repositories.ReaderRepositoryImpl;
import javafx.collections.ObservableList;
import org.w3c.dom.ls.LSOutput;

public class ReaderServiceImpl implements IReaderService {
    private final IReaderRepository readerRepository;


    public ReaderServiceImpl() {
        this.readerRepository = new ReaderRepositoryImpl();
    }

    @Override
    public ObservableList<Reader> getAllReaders() {
        return readerRepository.getAllReaders();
    }

    @Override
    public ObservableList<String> getAllReaderId() {
        return readerRepository.getAllReaderId();
    }

    @Override
    public String getReaderNameById(String readerId) {
        return readerRepository.getReaderNameById(readerId);
    }

    @Override
    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

    @Override
    public void deleteReader(Reader reader) {
        readerRepository.delete(reader);
    }

    @Override
    public String getReaderId() {
        return readerRepository.getReaderId();
    }


}
