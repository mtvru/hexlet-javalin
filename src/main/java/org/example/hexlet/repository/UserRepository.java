package org.example.hexlet.repository;

import lombok.Getter;
import org.example.hexlet.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepository {
    @Getter
    private static List<User> entities = new ArrayList<User>();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        user.setCreatedAt(LocalDateTime.now());
        entities.add(user);
    }

    public static List<User> search(String term) {
        return entities.stream()
                .filter(entity -> entity.getName().startsWith(term))
                .toList();
    }

    public static Optional<User> find(Long id) {
        return entities.stream()
                .filter(entity -> Objects.equals(entity.getId(), id))
                .findAny();
    }

    public static void delete(Long id) {
        entities.removeIf(user -> Objects.equals(user.getId(), id));
    }

    public static void removeAll() {
        entities = new ArrayList<User>();
    }
}
