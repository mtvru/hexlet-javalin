package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
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

            try {
                String passwordConfirmation = ctx.formParam("passwordConfirmation");
                String password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value.equals(passwordConfirmation), "The passwords don't match")
                        .check(value -> value.length() > 6, "The password is not long enough")
                        .get();
                User user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                BuildUserPage page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", model("page", page));
            }
        });
        app.get("/users/build", ctx -> {
            BuildUserPage page = new BuildUserPage();
            ctx.render("users/build.jte", model("page", page));
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
        app.get("/courses", ctx -> {
            String header = "List of courses";
            String term = ctx.queryParam("term");
            List<Course> courses;
            if (term != null) {
                courses = CourseRepository.search(term);
            } else {
                courses = CourseRepository.getEntities();
            }
            CoursesPage page = new CoursesPage(courses, header, term);
            ctx.render("courses/index.jte", model("page", page));
        });
        app.post("/courses", ctx -> {
            String name = ctx.formParam("name");
            String description = ctx.formParam("description");

            try {
                name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() > 2, "The name is not long enough")
                    .get();
                description = ctx.formParamAsClass("description", String.class)
                    .check(value -> value.length() > 10, "The description is not long enough")
                    .get();
                Course course = new Course(name, description);
                CourseRepository.save(course);
                ctx.redirect("/courses");
            } catch (ValidationException e) {
                BuildCoursePage page = new BuildCoursePage(name, description, e.getErrors());
                ctx.render("courses/build.jte", model("page", page));
            }
        });
        app.get("/courses/build", ctx -> {
            BuildCoursePage page = new BuildCoursePage();
            ctx.render("courses/build.jte", model("page", page));
        });
        app.get("/courses/{id}", ctx -> {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            Course course = CourseRepository.find(id).orElseThrow(() -> new NotFoundResponse("Course not found..."));
            CoursePage page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });
        app.start(7070);
    }
}
