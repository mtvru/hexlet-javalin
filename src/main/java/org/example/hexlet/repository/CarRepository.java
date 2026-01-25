package org.example.hexlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.hexlet.model.Car;

public class CarRepository extends BaseRepository {
    public static void save(Car car) throws SQLException {
        String sql = "INSERT INTO cars (make, model) VALUES (?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, car.getMake());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        }
    }

    public static Optional<Car> find(Long id) throws SQLException {
        String sql = "SELECT * FROM cars WHERE id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    var make = resultSet.getString("make");
                    var model = resultSet.getString("model");
                    var car = new Car(make, model);
                    car.setId(id);
                    return Optional.of(car);
                }
                return Optional.empty();
            }
        }
    }

    public static List<Car> getEntities() throws SQLException {
        String sql = "SELECT * FROM cars";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Car> result = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String make = resultSet.getString("make");
                    String model = resultSet.getString("model");
                    Car car = new Car(make, model);
                    car.setId(id);
                    result.add(car);
                }
                return result;
            }
        }
    }
}
