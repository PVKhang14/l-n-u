package com.example.library.repositories;

import com.example.library.models.Author;
import com.example.library.utils.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class AuthorRepositoryImpl implements IAuthorRepository {
    Repo repo = Repo.getInstance();

    @Override
    public ObservableList<Author> getAllAuthor() {
        ObservableList<Author> result = FXCollections.observableArrayList();

        String sql = """
                    SELECT * FROM authors
                """;
        ResultSet rs = repo.executeQuery(sql);

        try {
            while (rs.next()) {
                Author author = Author.builder()
                        .authorId(rs.getString("authorId"))
                        .authorName(rs.getString("authorName"))
                        .build();
                result.add(author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getAuthorIdByName(String author) {
        String sql = String.format("""
                    SELECT authorId FROM authors WHERE authorName = '%s'
                """, author);
        ResultSet rs = repo.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getString("authorId");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
