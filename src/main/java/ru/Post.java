package ru;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String href;
    private String description;
    private String createDate;

    public Post(String href, String description, String createDate) {
        this.href = href;
        this.description = description;
        this.createDate = createDate;
    }

    public String getHref() {
        return href;
    }

    public String getDescription() {
        return description;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "ru.Post{"
                + "href='" + href + '\''
                + ", description='" + description + '\''
                + ", createDate=" + createDate
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(href, post.href)
                && Objects.equals(description, post.description)
                && Objects.equals(createDate, post.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href, description, createDate);
    }
}