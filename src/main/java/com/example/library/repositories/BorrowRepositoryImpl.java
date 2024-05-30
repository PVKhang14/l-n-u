package com.example.library.repositories;

import com.example.library.models.Borrow;
import com.example.library.utils.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowRepositoryImpl implements IBorrowRepository {
    private final Repo repo = Repo.getInstance();

    @Override
    public ObservableList<Borrow> getBorrowByReaderId(String readerId) {
        ObservableList<Borrow> result = FXCollections.observableArrayList();
        String sql = String.format(
                """
                        select br.borrowId, b.bookName, r.readerName, br.borrowDate, br.returnDate, br.dueDate from borrow br
                        join library.books b on b.bookId = br.bookId
                        join library.readers r on r.readerId = br.readerId
                        where b.isDelete = false and r.isDelete = false and r.readerId = '%s';
                        """, readerId
        );

        ResultSet rs = repo.executeQuery(sql);
        try {
            while (rs.next()) {
                result.add(Borrow.builder()
                        .borrowId(rs.getString("borrowId"))
                        .bookName(rs.getString("bookName"))
                        .readerName(rs.getString("readerName"))
                        .borrowDate(rs.getDate("borrowDate").toLocalDate())
                        .returnDate(rs.getDate("returnDate").toLocalDate())
                        .dueDate(rs.getString("dueDate"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObservableList<Borrow> getAllBookBorrowed() {
        ObservableList<Borrow> result = FXCollections.observableArrayList();
        String sql = """
                select br.borrowId, b.bookName, r.readerName, br.borrowDate, br.returnDate, br.dueDate from borrow br
                        join library.books b on b.bookId = br.bookId
                        join library.readers r on r.readerId = br.readerId
                        where b.isDelete = false and r.isDelete = false
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            while (rs.next()) {
                result.add(Borrow.builder()
                        .borrowId(rs.getString("borrowId"))
                        .bookName(rs.getString("bookName"))
                        .readerName(rs.getString("readerName"))
                        .borrowDate(rs.getDate("borrowDate").toLocalDate())
                        .returnDate(rs.getDate("returnDate").toLocalDate())
                        .dueDate(rs.getString("dueDate"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void returnBook(String borrowId) {
        String sql = String.format(
                """
                        update borrow
                        set dueDate = '%s'
                        where borrowId = '%s';
                        """, LocalDate.now(), borrowId
        );
        repo.executeUpdate(sql);
    }

    @Override
    public void save(Borrow borrow) {
        int borrowId = 0;
        String sqlGetIndex = """
                select count(*) from borrow
                """;

        if (borrow.getBorrowId() == null) {
            ResultSet rs = repo.executeQuery(sqlGetIndex);
            try {
                if (rs.next()) {
                    borrowId = rs.getInt(1) + 1;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            borrowId = Integer.parseInt(borrow.getBorrowId());
        }

        String sql = String.format(
                """
                        insert into borrow(borrowId, bookId, readerId, borrowDate, returnDate)
                        values ('%s', '%s', '%s', '%s', '%s');
                        """, borrowId, borrow.getBookName(), borrow.getReaderName(), LocalDate.now(), borrow.getReturnDate()
        );

        repo.executeUpdate(sql);
    }

    @Override
    public int getTotalBorrow() {
        String sql = """
                select count(*) as total from borrow;
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int getTotalLate() {
        String sql = """
                select count(*) as total from borrow where returnDate < current_date();
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int getTotalReturn() {
        String sql = """
                select count(*) from borrow where dueDate is not null;
                                """;
        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int getTotalBorrowByReaderId(String readerId) {
        String sql = String.format(
                """
                        select count(*) as total from borrow where readerId = '%s';
                        """, readerId
        );

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
