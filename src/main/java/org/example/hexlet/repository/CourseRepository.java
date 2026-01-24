package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository extends BaseRepository {
    public static void save(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                // Устанавливаем ID в сохраненную сущность
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        }
    }

    public static Optional<Course> find(Long id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(name, description);
                    course.setId(id);
                    return Optional.of(course);
                }
                return Optional.empty();
            }
        }
    }

    public static List<Course> getEntities() throws SQLException {
        String sql = "SELECT * FROM courses";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Course> result = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(name, description);
                    course.setId(id);
                    result.add(course);
                }
                return result;
            }
        }
    }

    public static List<Course> search(String term) throws SQLException {
        String sql = "SELECT * FROM courses WHERE description LIKE ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, "%" + term + "%");
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Course> result = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(name, description);
                    course.setId(id);
                    result.add(course);
                }
                return result;
            }
        }
    }

    public static void delete(Long id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
