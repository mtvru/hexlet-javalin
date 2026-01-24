package org.example.hexlet.controller;

import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.dto.cars.CarPage;
import org.example.hexlet.dto.cars.CarsPage;
import org.example.hexlet.model.Car;
import org.example.hexlet.repository.CarRepository;
import org.example.hexlet.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class CarController {
    public static void index(Context ctx) throws SQLException {
        List<Car> cars = CarRepository.getEntities();
        CarsPage page = new CarsPage(cars);
        ctx.render("cars/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Car car = CarRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Car with id = " + id + " not found"));
        CarPage page = new CarPage(car);
        ctx.render("cars/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("cars/build.jte");
    }

    public static void create(Context ctx) throws SQLException {
        String make = ctx.formParam("make");
        String model = ctx.formParam("model");

        Car car = new Car(make, model);
        CarRepository.save(car);
        ctx.redirect(NamedRoutes.carsPath());
    }
}
