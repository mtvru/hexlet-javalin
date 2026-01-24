package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.PostsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.util.NamedRoutes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.javalin.rendering.template.TemplateUtil.model;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        app.before(ctx -> {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println("[" + now + "] Incoming request: " + ctx.method() + " " + ctx.path());
        });
        app.get("/", ctx -> {
            Boolean visited = Boolean.valueOf(ctx.cookie("visited"));
            MainPage page = new MainPage(visited);
            ctx.render("index.jte", model("page", page));
            ctx.cookie("visited", String.valueOf(true));
        });
        app.get("/hello", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });
        app.get(NamedRoutes.coursesPath(), CoursesController::index);
        app.get(NamedRoutes.buildCoursePath(), CoursesController::build);
        app.get(NamedRoutes.coursePath("{id}"), CoursesController::show);
        app.post(NamedRoutes.coursesPath(), CoursesController::create);
        app.get(NamedRoutes.editCoursePath("{id}"), CoursesController::edit);
        app.patch(NamedRoutes.coursePath("{id}"), CoursesController::update);
        app.delete(NamedRoutes.coursePath("{id}"), CoursesController::destroy);
        app.get(NamedRoutes.usersPath(), UsersController::index);
        app.get(NamedRoutes.buildUserPath(), UsersController::build);
        app.get(NamedRoutes.userPath("{id}"), UsersController::show);
        app.post(NamedRoutes.usersPath(), UsersController::create);
        app.get(NamedRoutes.editUserPath("{id}"), UsersController::edit);
        app.patch(NamedRoutes.userPath("{id}"), UsersController::update);
        app.delete(NamedRoutes.userPath("{id}"), UsersController::destroy);
        app.get("/users/{id}/post/{postId}", ctx -> {
            String postId = ctx.pathParam("postId");
            String id =  ctx.pathParam("id");
            ctx.result("User ID: " + id + " Post ID: " + postId);
        });
        app.get(NamedRoutes.postsPath(), PostsController::index);
        app.get(NamedRoutes.postPath("{id}"), PostsController::show);
        app.get(NamedRoutes.editPostPath("{id}"), PostsController::edit);
        app.post(NamedRoutes.postPath("{id}"), PostsController::update);
        app.start(7070);
    }
}
