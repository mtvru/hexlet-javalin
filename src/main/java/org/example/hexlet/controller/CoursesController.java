package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.hexlet.util.NamedRoutes;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CoursesController {
    public static void index(Context ctx) {
        String header = "List of courses";
        String term = ctx.queryParam("term");
        List<Course> courses;
        if (term != null) {
            courses = CourseRepository.search(term);
        } else {
            courses = CourseRepository.getEntities();
        }
        CoursesPage page = new CoursesPage(courses, header, term);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("courses/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Course course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        CoursePage page = new CoursePage(course);
        ctx.render("courses/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        BuildCoursePage page = new BuildCoursePage();
        ctx.render("courses/build.jte", model("page", page));
    }

    public static void create(Context ctx) {
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
            ctx.sessionAttribute("flash", "Course has been created!");
            ctx.redirect(NamedRoutes.coursesPath());
        } catch (ValidationException e) {
            BuildCoursePage page = new BuildCoursePage(name, description);
            page.setErrors(e.getErrors());
            ctx.status(422);
            ctx.render("courses/build.jte", model("page", page));
        }
    }

    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Course course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        CoursePage page = new CoursePage(course);
        ctx.render("courses/edit.jte", model("page", page));
    }


    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();

        String name = ctx.formParam("name");
        String description = ctx.formParam("description");

        Course course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        course.setName(name);
        course.setDescription(description);
        CourseRepository.save(course);
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void destroy(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        CourseRepository.delete(id);
        ctx.redirect(NamedRoutes.coursesPath());
    }
}