package com.example.blog.post;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostService {

    private final CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public PostService() {
        create(new CreatePostRequest(
                "欢迎来到博客",
                "这是第一篇文章，你可以通过前端发布新的内容。",
                "Admin"
        ));
    }

    public List<Post> findAll() {
        var result = new ArrayList<>(posts);
        result.sort(Comparator.comparing(Post::createdAt).reversed());
        return result;
    }

    public Post create(CreatePostRequest request) {
        var post = new Post(
                nextId.getAndIncrement(),
                request.title().trim(),
                request.content().trim(),
                request.author().trim(),
                Instant.now()
        );
        posts.add(post);
        return post;
    }
}
