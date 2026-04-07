package com.example.blog.post;

import java.time.Instant;

public record Post(Long id, String title, String content, String author, Instant createdAt) {
}
