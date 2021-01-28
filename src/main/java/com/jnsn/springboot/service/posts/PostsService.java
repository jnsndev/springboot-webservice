package com.jnsn.springboot.service.posts;

import com.jnsn.springboot.domain.posts.Posts;
import com.jnsn.springboot.domain.posts.PostsRepository;
import com.jnsn.springboot.web.dto.PostsListResponseDto;
import com.jnsn.springboot.web.dto.PostsResponseDto;
import com.jnsn.springboot.web.dto.PostsSaveRequestDto;
import com.jnsn.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    // Transactional(readOnly = true): 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        // PostsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 후, List로 반환
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
