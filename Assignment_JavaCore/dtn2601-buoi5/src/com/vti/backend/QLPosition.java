package com.vti.backend;

import com.vti.Enum.PositionName;
import com.vti.entity.Position;
import com.vti.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QLPosition {

    // SELECT - trả về List
    public List<Position> getListPosition() {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "SELECT * FROM position";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Position position = new Position();
                position.setPositionId(rs.getInt("position_id"));
                position.setPositionName(PositionName.valueOf(rs.getString("position_name")));
                list.add(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // TÌM KIẾM THEO TÊN - trả về List
    public List<Position> findByName(String name) {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "SELECT * FROM position WHERE position_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Position position = new Position();
                position.setPositionId(rs.getInt("position_id"));
                position.setPositionName(PositionName.valueOf(rs.getString("position_name")));
                list.add(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // THÊM MỚI - trả về boolean
    public boolean createPosition(Position position) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "INSERT INTO position(position_name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, position.getPositionName().name());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CẬP NHẬT THEO ID - trả về boolean
    public boolean updatePosition(Position position) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "UPDATE position SET position_name = ? WHERE position_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, position.getPositionName().name());
            pstmt.setInt(2, position.getPositionId());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // XÓA THEO ID - trả về boolean
    public boolean deletePosition(int id) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "DELETE FROM position WHERE position_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // XÓA THEO TÊN - trả về boolean
    public boolean deleteByName(String name) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "DELETE FROM position WHERE position_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getPositionsWithMostAccounts() {
        try {
            Connection conn = ConnectionUtils.getConnection();

            String sql = "SELECT p.position_name, COUNT(a.account_id) AS user_count " +
                    "FROM position p " +
                    "LEFT JOIN account a ON p.position_id = a.position_id " +
                    "GROUP BY p.position_id " +
                    "HAVING user_count = (SELECT MAX(my_count) FROM " +
                    "(SELECT COUNT(account_id) AS my_count FROM account GROUP BY position_id) AS temp)";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Chức vụ có nhiều nhân viên nhất ---");
            while (rs.next()) {
                System.out.printf("Chức vụ: %-15s | Số lượng: %d %n",
                        rs.getString("position_name"), rs.getInt("user_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPositionsWithLeastAccounts() {
        try {
            Connection conn = ConnectionUtils.getConnection();

            String sql = "SELECT p.position_name, COUNT(a.account_id) AS user_count " +
                    "FROM position p " +
                    "LEFT JOIN account a ON p.position_id = a.position_id " +
                    "GROUP BY p.position_id " +
                    "HAVING user_count = (SELECT MIN(my_count) FROM " +
                    "(SELECT COUNT(account_id) AS my_count FROM account GROUP BY position_id) AS temp)";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Chức vụ có ít nhân viên nhất ---");
            while (rs.next()) {
                System.out.printf("Chức vụ: %-15s | Số lượng: %d %n",
                        rs.getString("position_name"), rs.getInt("user_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}