package ru;

import java.util.List;
import java.util.Set;

public interface Parse {
    Set<Post> list(String link);


    Post detail(String link);
}

