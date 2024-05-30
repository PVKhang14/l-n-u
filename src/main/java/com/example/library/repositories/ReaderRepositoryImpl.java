package com.example.library.repositories;

import com.example.library.models.Reader;
import com.example.library.utils.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.random.RandomGenerator;

public class ReaderRepositoryImpl implements IReaderRepository {
    private final Repo repo = Repo.getInstance();

    @Override
    public ObservableList<Reader> getAllReaders() {
        ObservableList<Reader> readers = FXCollections.observableArrayList();

        String sql = """
                select * from readers where isDelete = false;
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            while (rs.next()) {
                Reader reader = Reader.builder()
                        .readerId(rs.getString("readerId"))
                        .readerName(rs.getString("readerName"))
                        .readerEmail(rs.getString("readerEmail"))
                        .readerPhone(rs.getString("readerPhoneNumber"))
                        .readerDOB(rs.getDate("readerDOB").toLocalDate())
                        .readerAddress(rs.getString("address"))
                        .build();
                readers.add(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readers;
    }

    @Override
    public void save(Reader reader) {
        String sqlInsert = String.format("""
                insert into readers(readerId, readerName, readerEmail, readerPhoneNumber, readerDob, address) values('%s','%s','%s','%s','%s','%s');
                 """, reader.getReaderId(), reader.getReaderName(), reader.getReaderEmail(), reader.getReaderPhone(), reader.getReaderDOB(), reader.getReaderAddress());

        String sqlUpdate = String.format(
                """
                        update readers set
                        readerName = '%s',
                        readerEmail = '%s',
                        readerPhoneNumber = '%s',
                        readerDOB = '%s',
                        address = '%s'
                        where readerId = '%s';
                                     
                        """, reader.getReaderName(), reader.getReaderEmail(), reader.getReaderPhone(), reader.getReaderDOB(), reader.getReaderAddress(), reader.getReaderId());

        String sqlCheck = String.format("""
                select Count(readerId) from readers where readerId = '%s';
                """, reader.getReaderId());

        ResultSet rs = repo.executeQuery(sqlCheck);

        try {
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    repo.executeUpdate(sqlUpdate);
                } else {
                    repo.executeUpdate(sqlInsert);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reader reader) {
        String sql = String.format("""
                update readers set isDelete = true where readerId = '%s';
                """, reader.getReaderId());

        repo.executeUpdate(sql);

    }

    @Override
    public ObservableList<String> getAllReaderId() {
        ObservableList<String> result = FXCollections.observableArrayList();
        String sql = """
                select readerId from readers where isDelete = false;
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            while (rs.next()) {
                result.add(rs.getString("readerId"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    public String getReaderNameById(String readerId) {
        String sql = String.format("""
                select readerName from readers where readerId = '%s' and isDelete = false;
                """, readerId);

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getString("readerName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String getReaderIdByName(String readerName) {
        String sql = String.format(
                """
                        select readerId from readers where readerName = '%s' and isDelete = false;
                         """, readerName
        );

        ResultSet rs = repo.executeQuery(sql);

        try {
            if (rs.next()) {
                return rs.getString("readerId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalReader() {
        String sql = """
                select count(*) as total from readers where isDelete = false;
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getReaderId() {
        String sql = "select count(*) from readers";

        int id = 0;

        ResultSet rs = repo.executeQuery(sql);

        try {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format("R00%d", id);
    }
}

