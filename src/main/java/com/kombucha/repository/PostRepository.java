package com.kombucha.repository;

import com.kombucha.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByTitleContaining(String title);
    List<Post> findPostsByContentContaining(String content);
    @Query("SELECT post FROM Post post WHERE post.title = :post OR post.content = :content")
    List<Post> findPostsByTitleOrContentContaining(String title, String content);
}
