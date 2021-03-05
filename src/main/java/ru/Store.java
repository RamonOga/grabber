package ru;

import java.util.List;

public interface Store {
    boolean save(Post post);

    List<Post> getAll();

    Post findById(String id);
}