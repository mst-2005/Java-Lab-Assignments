package com.example.jdbc_javafx.dao;

import com.example.jdbc_javafx.model.Restaurant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    public void addRestaurant(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO restaurant (name, cuisine, location) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getCuisine());
            pstmt.setString(3, restaurant.getLocation());
            pstmt.executeUpdate();
        }
    }

    public List<Restaurant> getAllRestaurants() throws SQLException {
        String sql = "SELECT * FROM restaurant";
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setCuisine(rs.getString("cuisine"));
                restaurant.setLocation(rs.getString("location"));
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }

    public void updateRestaurant(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE restaurant SET name = ?, cuisine = ?, location = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getCuisine());
            pstmt.setString(3, restaurant.getLocation());
            pstmt.setInt(4, restaurant.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteRestaurant(int id) throws SQLException {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
