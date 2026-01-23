package org.example.hexlet.controller;

import java.util.List;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.dto.posts.PostsPage;
import org.example.hexlet.dto.posts.PostPage;
import org.example.hexlet.repository.PostRepository;
import org.example.hexlet.model.Post;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class PostsController {
    public static void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Post post = PostRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Page not found"));
        PostPage page = new PostPage(post);
        ctx.render("posts/show.jte", model("page", page));
    }

    public static void index(Context ctx) {
        Integer pageNumber = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        List<Post> posts = PostRepository.findAll(pageNumber, 5);
        boolean hasNext = posts.size() == 5;
        PostsPage page = new PostsPage(posts, pageNumber, hasNext);
        ctx.render("posts/index.jte", model("page", page));
    }
}
