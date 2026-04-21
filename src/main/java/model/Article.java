package model;

import java.time.LocalDateTime;

public class Article {
    private String title;
    private String content;

    private int authorId;
    private LocalDateTime createdAt;

    public Article(String title, String content, int authorId, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
