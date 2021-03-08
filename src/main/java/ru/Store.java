package ru;

import java.util.List;
import java.util.Set;

public interface Store {
    boolean save(Post post);

    boolean addAll(Set<Post> postSet);

    List<Post> getAll();

    Post findById(String id);
}