package com.jnsn.springboot.web.dto;

import com.jnsn.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    // PostsResponseDto는 entity의 필드 중 일부만 사용하므로 생성자로 entity를 받아 필드에 값을 넣는다.

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
