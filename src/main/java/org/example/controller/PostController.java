package org.example.controller;


import com.google.gson.Gson;
import org.example.model.Dto;
import org.example.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dto> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.getById(id);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    @PostMapping
    public Dto save(@RequestBody Dto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")

    public void removeById(@PathVariable long id, HttpServletResponse response) {
        service.removeById(id);
    }

}

