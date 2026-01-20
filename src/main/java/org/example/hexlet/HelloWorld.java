package org.example.hexlet;

import io.javalin.Javalin;

import java.util.Objects;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        // Описываем, что загрузится по адресу /
        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/users", ctx -> ctx.json("GET /users"));
        app.get("/hello", ctx -> {
            String name = Objects.requireNonNullElse(ctx.queryParam("name"), "World");
            ctx.result("Hello, " + name + "!");
        });
        app.post("/users", ctx -> ctx.result("POST /users"));
        app.start(7070); // Стартуем веб-сервер
    }
}