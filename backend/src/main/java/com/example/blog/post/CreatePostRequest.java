package com.example.blog.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 5000) String content,
        @NotBlank @Size(max = 60) String author
) {
}
