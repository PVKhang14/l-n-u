package com.example.library.repositories;

import com.example.library.models.Book;
import com.example.library.utils.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookRepositoryImpl implements IBookRepository {
    private final Repo repo = Repo.getInstance();

    @Override
    public void save(Book book) {
        String sql = String.format("""
                        insert into books(bookId, authorId, categoryId, bookName, quantity, publishDate)
                        values ('%s', %d, %d, '%s', %d, '%s');
                        """,
                book.getBookId(),
                Integer.parseInt(book.getAuthor()),
                Integer.parseInt(book.getCategory().trim()),
                book.getBookName(),
                book.getQuantity(),
                book.getPublisher()
        );
        String sqlUpdate = String.format("""
                        update books set authorId = '%s', categoryId = %d, bookName = '%s', quantity = %d, publishDate = '%s'
                        where bookId = '%s';
                        """,
                book.getAuthor(),
                Integer.parseInt(book.getCategory().trim()),
                book.getBookName(),
                book.getQuantity(),
                book.getPublisher(),
                book.getBookId()
        );

        String sqlCheck = String.format("""
                select * from books where bookId = '%s';
                """, book.getBookId());

        ResultSet rs = repo.executeQuery(sqlCheck);
        try {
            if (rs.next()) {
                repo.executeUpdate(sqlUpdate);
            } else {
                repo.executeUpdate(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        String sql = String.format("update books set isDelete = true where bookId = '%s'", book.getBookId());
        repo.executeUpdate(sql);
    }

    @Override
    public void saveCategory(String category) {
        String sqlCheck = String.format("""
                select * from categories where categoryName = '%s';
                """, category);
        ResultSet rs = repo.executeQuery(sqlCheck);
        try {
            if (rs.next()) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String sql = String.format("""
                insert into categories(categoryName) values ('%s');
                """, category);
        repo.executeUpdate(sql);
    }
    @Override

    public void saveAuthor(String author) {
        String sqlCheck = String.format("""
                select * from authors where authorName = '%s';
                """, author);
        ResultSet rs = repo.executeQuery(sqlCheck);
        try {
            if (rs.next()) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String sql = String.format("""
                insert into authors(authorName) values ('%s');
                """, author);
        repo.executeUpdate(sql);
    }

    @Override
    public String getCategoryIdByName(String categoryName) {
        String sql = String.format("""
                select categoryId from categories where categoryName = '%s';
                """, categoryName);

        System.out.println(sql);
        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getString("categoryId");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Book> getAllBook() {
        ObservableList<Book> result = FXCollections.observableArrayList();
        String sql = """
                select b.bookId, a.authorName, c.categoryName, b.bookName, b.quantity, b.publishDate  from books b
                join library.categories c on c.categoryId = b.categoryId
                join library.authors a on a.authorId = b.authorId
                where b.isDelete = false and a.isDelete = false;
                """;

        ResultSet rs = repo.executeQuery(sql);
        try {
            while (rs.next()) {
                String bookId = rs.getString("bookId");
                String authorName = rs.getString("authorName");
                String categoryName = rs.getString("categoryName");
                String bookName = rs.getString("bookName");
                int quantity = rs.getInt("quantity");
                LocalDate publishDate = rs.getDate("publishDate").toLocalDate();

                Book book = Book.builder()
                        .bookId(bookId)
                        .author(authorName)
                        .category(categoryName)
                        .bookName(bookName)
                        .quantity(quantity)
                        .publisher(publishDate)
                        .build();
                result.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @Override
    public ObservableList<String> getAllCategoryName() {
        ObservableList<String> result = FXCollections.observableArrayList();

        String sql = """
                select categoryName from categories;
                """;
        ResultSet rs = repo.executeQuery(sql);

        try {
            while (rs.next()) {
                String categoryName = rs.getString("categoryName");
                result.add(categoryName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public String getBookIdByName(String bookName) {
        String sql = String.format("""
                select bookId from books where bookName = '%s';
                """, bookName);

        ResultSet rs = repo.executeQuery(sql);

        try {
            if (rs.next()) {
                return rs.getString("bookId");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void increaseQuantity(String bookId) {
        String sql = String.format("""
                update books set quantity = quantity + 1 where bookId = '%s';
                """, bookId);
        repo.executeUpdate(sql);
    }

    @Override
    public void decreaseQuantity(String bookId) {
        String sql = String.format("""
                update books set quantity = quantity - 1 where bookId = '%s';
                """, bookId);
        repo.executeUpdate(sql);
    }

    @Override
    public ObservableList<String> getAllBookId() {
        ObservableList<String> result = FXCollections.observableArrayList();

        String sql = """
                select bookId from books where isDelete = false;
                """;

        ResultSet rs = repo.executeQuery(sql);

        try {
            while (rs.next()) {
                String bookId = rs.getString("bookId");
                result.add(bookId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public String getBookNameById(String bookId) {
        String sql = String.format("""
                select bookName from books where bookId = '%s' and isDelete = false;
                """, bookId);

        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getString("bookName");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int getTotalBook() {
        String sql = """
                select SUM(QUANTITY) as total from books where isDelete = false;
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
    public String getBookId() {
        String sql = "select count(*) from books";

        ResultSet rs = repo.executeQuery(sql);
        int id = getTotalBook();
        try{
            if(rs.next()){
                id = rs.getInt(1) + 1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return String.format("B00%d", id);
    }
}
