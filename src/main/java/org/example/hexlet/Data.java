package org.example.hexlet;

import java.util.Random;
import java.util.Locale;
import net.datafaker.Faker;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;
import org.example.hexlet.model.Course;
import java.util.stream.LongStream;

public class Data {
    private static final long ITEMS_COUNT = 30;

    private static long idCounter = ITEMS_COUNT;

    public static List<Course> getCourses() {
        Random random = new Random(123);
        Faker faker = new Faker(new Locale("en"), random);

        List<Long> ids = LongStream
                .range(1, ITEMS_COUNT + 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(ids, random);

        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < ITEMS_COUNT; i++) {
            Long id = ids.get(i);
            String firstName = faker.name().firstName();
            String desc = faker.programmingLanguage().creator();
            Course course = new Course(firstName, desc);
            course.setId(id);
            courses.add(course);
        }

        return courses;
    }

    public static long getNextId() {
        long id = ++idCounter;
        return id;
    }
}
