package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        app.get("/", ctx -> ctx.render("index.jte"));
        app.get(NamedRoutes.buildCoursePath(), ctx -> {
            BuildCoursePage page = new BuildCoursePage();
            ctx.render("courses/build.jte", model("page", page));
        });
        app.get(NamedRoutes.coursesPath(), ctx -> {
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
        app.post(NamedRoutes.coursesPath(), ctx -> {
            String name = ctx.formParam("name").trim();
            String description = ctx.formParam("description").trim();

            try {
                name = ctx.formParamAsClass("name", String.class)
                        .check(value -> value.trim().length() > 2, "The name is not long enough")
                        .get();
                description = ctx.formParamAsClass("description", String.class)
                        .check(value -> value.trim().length() > 10, "The description is not long enough")
                        .get();
                Course course = new Course(name, description);
                CourseRepository.save(course);
                ctx.redirect(NamedRoutes.coursesPath());
            } catch (ValidationException e) {
                BuildCoursePage page = new BuildCoursePage(name, description, e.getErrors());
                ctx.status(422);
                ctx.render("courses/build.jte", model("page", page));
            }
        });
        app.get(NamedRoutes.coursePath("{id}"), ctx -> {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            Course course = CourseRepository.find(id).orElseThrow(() -> new NotFoundResponse("Course not found..."));
            CoursePage page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });
        app.get("/hello", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });
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
        app.start(7070);
    }
}
