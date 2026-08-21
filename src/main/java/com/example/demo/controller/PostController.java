package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/feed")
    public String feed(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("newPost", new Post());
        return "feed";
    }

    @PostMapping("/posts")
    public String createPost(@Valid @ModelAttribute("newPost") Post post,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("username", user.getUsername());
            model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
            return "feed";
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setAuthor(user);
        postRepository.save(post);
        return "redirect:/feed";
    }
}