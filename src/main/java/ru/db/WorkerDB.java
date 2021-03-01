package ru.db;

import ru.Post;
import ru.parse.ParseDate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class WorkerDB implements AutoCloseable {
    private Connection connection;
    private DataBase dataBase;
    private ParseDate parseDate;

    public WorkerDB(String prop) {
        dataBase = DataBase.getDataBase(prop);
        connection = dataBase.getConnection();
    }

    public List<Post> findAll() {
        List<Post> rsl = new ArrayList<>();
        String query = "select * from posts;";
        ResultSet rs = executeQueryWithResultSet(query);
        try {
            while (rs.next()) {
                rsl.add(new Post(rs.getString("title"),
                        rs.getString("href"),
                        rs.getString("descr"),
                        rs.getObject(5, LocalDateTime.class)));
            }
            rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rsl;
    }

    public boolean addPost (Post post) {
        boolean rsl = false;
        String query = String.format("insert into posts (title, href, descr, date) values ('%s', '%s', '%s', '%s');",
                post.getTitle(),
                post.getHref(),
                post.getDescription(),
                post.getCreateDate());
        if (executeQuery(query)) {
            rsl = true;
        }
        return rsl;
    }
    public boolean addAll(Set<Post> postSet) {
        boolean rsl = true;
        for (Post post : postSet) {
            if (!addPost(post)) {
                rsl = false;
            }
        }
        return rsl;
    }

    private ResultSet executeQueryWithResultSet(String query) {
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            rs = statement.getResultSet();
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
        }
        return rs;
    }

    private boolean executeQuery(String query) {
        boolean rsl = false;
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
            if (statement.getUpdateCount() > 0) {
                rsl = true;
            }
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
            System.out.println(sqle.getMessage());
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
