package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    private static final List<Course> COURSES = Data.getCourses();
    private static final List<User> USERS = Data.getUsers();

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        app.get("/", ctx -> ctx.render("index.jte"));
        app.get("/users", ctx -> {
            UsersPage page = new UsersPage(UserRepository.getEntities());
            ctx.render("users/index.jte", model("page", page));
        });
        app.post("/users", ctx -> {
            String name = ctx.formParam("name").trim();
            String email = ctx.formParam("email").trim().toLowerCase();
            String password = ctx.formParam("password");
            String passwordConfirmation = ctx.formParam("passwordConfirmation");

            User user = new User(name, email, password);
            UserRepository.save(user);
            ctx.redirect("/users");
        });
        app.get("/users/build", ctx -> {
            ctx.render("users/build.jte");
        });
        app.get("/users/{id}", ctx -> {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            User user = UserRepository.find(id).orElseThrow(() -> new NotFoundResponse("Course not found..."));
            UserPage page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        });
        app.get("/hello", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });
        app.get("/users/{id}/post/{postId}", ctx -> {
            String postId = ctx.pathParam("postId");
            String id =  ctx.pathParam("id");
            ctx.result("User ID: " + id + " Post ID: " + postId);
        });
        app.get("/courses/{id}", ctx -> {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            Course course = COURSES
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundResponse("Course not found..."));
            CoursePage page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });
        app.get("/courses", ctx -> {
            String header = "List of courses";
            String term = ctx.queryParam("term");
            List<Course> courses;
            if (term != null) {
                courses = COURSES
                    .stream()
                    .filter(c -> c.getDescription().contains(term))
                    .toList();
            } else {
                courses = COURSES;
            }
            CoursesPage page = new CoursesPage(courses, header, term);
            ctx.render("courses/index.jte", model("page", page));
        });
        app.start(7070);
    }
}
