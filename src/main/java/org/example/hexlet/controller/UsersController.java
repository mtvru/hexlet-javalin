package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class UsersController {
    public static void index(Context ctx) {
        UsersPage page = new UsersPage(UserRepository.getEntities());
        ctx.render("users/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        User user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        UserPage page = new UserPage(user);
        ctx.render("users/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        BuildUserPage page = new BuildUserPage();
        ctx.render("users/build.jte", model("page", page));
    }

    public static void create(Context ctx) {
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
            ctx.redirect(NamedRoutes.usersPath());
        } catch (ValidationException e) {
            BuildUserPage page = new BuildUserPage(name, email, e.getErrors());
            ctx.status(422);
            ctx.render("users/build.jte", model("page", page));
        }
    }

    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        User user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        UserPage page = new UserPage(user);
        ctx.render("users/edit.jte", model("page", page));
    }


    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();

        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        UserRepository.save(user);
        ctx.redirect(NamedRoutes.usersPath());
    }

    public static void destroy(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        UserRepository.delete(id);
        ctx.redirect(NamedRoutes.usersPath());
    }
}