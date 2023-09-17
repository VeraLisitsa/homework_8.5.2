package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Dto;
import org.example.model.Post;
import org.example.model.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
@Repository
public class PostRepository {
    @Autowired
    private PostMapper postMapper;
    private final ConcurrentHashMap<Long, Post> postList = new ConcurrentHashMap<>();

    private final AtomicLong count = new AtomicLong(0);

    public List<Dto> all() {
        List<Dto> list = postList.values().stream()
                .filter(post -> !post.getIsRemoved())
                .map(post -> postMapper.postToDto(post))
                .collect(Collectors.toList());
        return list;
    }

    public Optional<Dto> getById(long id) {
        Post post = postList.get(id);
        if (post != null && !post.getIsRemoved()) {
            return Optional.ofNullable(postMapper.postToDto(post));
        }
        throw new NotFoundException("Post not found");
    }

    public Dto save(Dto dto) {
        Post post = postMapper.dtoToPost(dto);
        if (dto.getId() == 0) {
            post.setId(count.incrementAndGet());
            postList.put(post.getId(), post);
            return postMapper.postToDto(post);
        } else {
            Optional<Post> postOptional = Optional.ofNullable(postList.get(post.getId()));
            if (postOptional.isEmpty() || post.getIsRemoved()) {
                throw new NotFoundException("Post not found");
            }
            postList.put(post.getId(), post);
            return postMapper.postToDto(post);

        }
    }

    public void removeById(long id) {
        if (postList.get(id) != null) {
            postList.get(id).setIsRemoved(true);
        } else {
            throw new NotFoundException("Post not found");
        }
    }
}
