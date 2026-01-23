package org.example.hexlet.util;

import java.util.Random;
import net.datafaker.Faker;
import java.util.List;
import java.util.ArrayList;
import org.example.hexlet.model.Post;

public class Generator {
    private static final int ITEMS_COUNT = 32;
    private static final Random RANDOM = new Random(123);

    public static List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        Faker faker = new Faker(RANDOM);

        for (int i = 0; i < ITEMS_COUNT; i++) {
            var name = faker.book().title();
            var body = faker.lorem().sentence();
            var id = (long) (i + 1);
            var post = new Post(id, name, body);
            posts.add(post);
        }

        return posts;
    }

}
