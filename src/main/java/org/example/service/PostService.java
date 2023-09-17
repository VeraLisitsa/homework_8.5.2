package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.Dto;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Dto> all() {
        return repository.all();
    }

    public Dto getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Dto save(Dto dto) {
        return repository.save(dto);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
