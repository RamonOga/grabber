package ru.db;

import ru.Post;
import ru.Store;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PsqlStore implements Store, AutoCloseable {
    private Connection connection;
    private DataBase dataBase;

    public PsqlStore(Properties prop) {
        dataBase = DataBase.getDataBase(prop);
        connection = dataBase.getConnection();
    }

    @Override
    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        String query = "select * from posts;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl.add(new Post(rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("href"),
                            rs.getString("descr"),
                            rs.getObject(5, LocalDateTime.class)));
                }
          } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rsl;
}

    @Override
    public boolean save(Post post) {
        boolean rsl = false;
        String query = "insert into posts (title, href, descr, date) values (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getHref());
            statement.setString(3, post.getDescription());
            statement.setObject(4, post.getCreateDate());
            statement.executeUpdate();
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
        }

        return rsl;
    }

    @Override
    public boolean addAll(List<Post> postSet) {
        boolean rsl = true;
        for (Post post : postSet) {
            if (!save(post)) {
                rsl = false;
            }
        }
        return rsl;
    }

    public Post findById(String id) {
        Post rsl = null;
        String query = "select * from posts where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            statement.executeQuery();
            try (ResultSet resultSet = statement.getResultSet()) {
                resultSet.next();
                rsl = new Post(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("href"),
                        resultSet.getString("descr"),
                        resultSet.getObject(5, LocalDateTime.class));
            }
            return rsl;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rsl;
    }

    @Override
    public void close()  {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
