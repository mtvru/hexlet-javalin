package org.example.hexlet.repository;


import java.util.List;
import org.example.hexlet.model.Post;
import org.example.hexlet.util.Generator;
import java.util.Optional;

public class PostRepository {
    private static List<Post> entities = Generator.getPosts();

    public static void save(Post post) {
        post.setId((long) entities.size() + 1);
        entities.add(post);
    }

    public static List<Post> search(String term) {
        var posts = entities.stream()
                .filter(entity -> entity.getName().startsWith(term))
                .toList();
        return posts;
    }

    public static Optional<Post> find(Long id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findAny();
    }

    public static Optional<Post> findByName(String name) {
        return entities.stream()
                .filter(entity -> entity.getName().equals(name))
                .findAny();
    }

    public static boolean existsByName(String name) {
        return entities.stream()
                .anyMatch(value -> value.getName().equals(name));
    }

    public static List<Post> getEntities() {
        return entities;
    }

    public static List<Post> findAll(int pageNumber, int pageSize) {
        var begin = (pageNumber - 1) * pageSize;
        var end = begin + pageSize;

        return entities.stream().skip(begin).limit(end - begin).toList();
    }

    public static void clear() {
        entities.clear();
    }
}
