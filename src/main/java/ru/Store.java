package ru;

import java.util.List;

public interface Store {
    boolean save(Post post);

    boolean addAll(List<Post> postSet);

    List<Post> getAll();

    Post findById(String id);
}