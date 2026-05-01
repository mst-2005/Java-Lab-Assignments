package com.example.jdbc_javafx.dao;

import com.example.jdbc_javafx.model.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void addMenuItem(MenuItem menuItem) throws SQLException {
        String sql = "INSERT INTO menu_item (name, price, restaurant_id) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setInt(3, menuItem.getRestaurantId());
            pstmt.executeUpdate();
        }
    }

    public List<MenuItem> getAllMenuItems() throws SQLException {
        String sql = "SELECT * FROM menu_item";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("id"));
                menuItem.setName(rs.getString("name"));
                menuItem.setPrice(rs.getDouble("price"));
                menuItem.setRestaurantId(rs.getInt("restaurant_id"));
                menuItems.add(menuItem);
            }
        }
        return menuItems;
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException {
        String sql = "UPDATE menu_item SET name = ?, price = ?, restaurant_id = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setInt(3, menuItem.getRestaurantId());
            pstmt.setInt(4, menuItem.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMenuItem(int id) throws SQLException {
        String sql = "DELETE FROM menu_item WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
