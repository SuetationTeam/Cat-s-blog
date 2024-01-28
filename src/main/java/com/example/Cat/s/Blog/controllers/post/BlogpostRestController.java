package com.example.Cat.s.Blog.controllers.post;

import com.example.Cat.s.Blog.controllers.dto.PostDTO;
import com.example.Cat.s.Blog.controllers.dto.TitlePostDTO;
import com.example.Cat.s.Blog.db.entity.posts.Blogpost;
import com.example.Cat.s.Blog.mappers.BlogpostMapper;
import com.example.Cat.s.Blog.services.post.BlogpostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogposts")
public class BlogpostRestController {
    private final BlogpostService blogpostService;

    private final BlogpostMapper blogpostMapper;

    @GetMapping
    public List<TitlePostDTO> getAllBlogposts() {
        List<Blogpost> posts = blogpostService.showAll();
        return posts.stream().map(blogpostMapper::blogpostToTitlePostDTO).toList();
    }


    @GetMapping(value = "/{id}")
    public PostDTO getUserById(@PathVariable("id") Long id) {
        Optional<Blogpost> user = blogpostService.showById(id);
        if (user.isPresent()) {
            return blogpostMapper.blogpostToPostDTO(user.get());
        }
        return new PostDTO();

    }

}