package org.example.model;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PostMapper {
    @Autowired
    private ModelMapper mapper;

    public Post dtoToPost(Dto dto) {
        return (Objects.isNull(dto) ? null : mapper.map(dto, Post.class));
    }

    public Dto postToDto(Post post) {
        return (Objects.isNull(post) ? null : mapper.map(post, Dto.class));
    }
}
