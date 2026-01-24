package org.example.hexlet.controller;

import java.util.List;
import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.posts.EditPostPage;
import org.example.hexlet.dto.posts.PostsPage;
import org.example.hexlet.dto.posts.PostPage;
import org.example.hexlet.model.Post;
import org.example.hexlet.repository.PostRepository;

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
    
    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Post post = PostRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        EditPostPage page = new EditPostPage(id, post.getName(), post.getBody());
        ctx.render("posts/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();

        try {
            String name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() >= 2, "The name must not be shorter than two characters.")
                    .get();
            String body = ctx.formParamAsClass("body", String.class)
                    .check(value -> value.length() >= 10, "The post must be at least 10 characters long.")
                    .get();
            Post post = PostRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
            post.setName(name);
            post.setBody(body);
            PostRepository.save(post);
            ctx.redirect(NamedRoutes.postsPath());

        } catch (ValidationException e) {
            String name = ctx.formParam("name");
            String body = ctx.formParam("body");
            var page = new EditPostPage(id, name, body, e.getErrors());
            ctx.render("posts/edit.jte", model("page", page)).status(422);
        }
    }
}
