package com.vti.backend;

import com.vti.entity.Department;
import com.vti.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QLDepartment {

    // READ
    public List<Department> getListDepartment() {

        List<Department> departments = new ArrayList<>();

        try {

            Connection conn = ConnectionUtils.getConnection();

            String sql = "select * from department";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Department department = new Department();

                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));

                departments.add(department);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return departments;
    }

    // THÊM MỚI (Nhận tham số name từ Frontend)
    public boolean createDepartment(String name) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "INSERT INTO department(department_name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CẬP NHẬT (Nhận tham số id và name từ Frontend)
    public boolean updateDepartment(int id, String newName) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "UPDATE department SET department_name = ? WHERE department_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // XÓA (Nhận tham số id từ Frontend)
    public boolean deleteDepartment(int id) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "DELETE FROM department WHERE department_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // TÌM KIẾM THEO ID VÀ TÊN
    public List<Department> findByDepartmentIdAndName(int id, String name) {
        List<Department> departments = new ArrayList<>();
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "SELECT * FROM department WHERE department_id = ? AND department_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, "%" + name + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));
                departments.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
    }

    public void getDepartmentsWithMostAccounts() {
        try {
            Connection conn = ConnectionUtils.getConnection();

            // SQL: Đếm số nhân viên mỗi phòng, sau đó lọc những phòng có số nhân viên = số lớn nhất
            String sql = "SELECT d.department_name, COUNT(a.account_id) AS user_count " +
                    "FROM department d " +
                    "LEFT JOIN account a ON d.department_id = a.department_id " +
                    "GROUP BY d.department_id " +
                    "HAVING user_count = (SELECT MAX(my_count) FROM " +
                    "(SELECT COUNT(account_id) AS my_count FROM account GROUP BY department_id) AS temp)";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Phòng ban có nhiều nhân viên nhất ---");
            while (rs.next()) {
                System.out.printf("Tên phòng: %-15s | Số lượng: %d %n",
                        rs.getString("department_name"), rs.getInt("user_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDepartmentsWithLeastAccounts() {
        try {
            Connection conn = ConnectionUtils.getConnection();

            // Tương tự nhưng dùng MIN
            String sql = "SELECT d.department_name, COUNT(a.account_id) AS user_count " +
                    "FROM department d " +
                    "LEFT JOIN account a ON d.department_id = a.department_id " +
                    "GROUP BY d.department_id " +
                    "HAVING user_count = (SELECT MIN(my_count) FROM " +
                    "(SELECT COUNT(account_id) AS my_count FROM account GROUP BY department_id) AS temp)";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Phòng ban có ít nhân viên nhất ---");
            while (rs.next()) {
                System.out.printf("Tên phòng: %-15s | Số lượng: %d %n",
                        rs.getString("department_name"), rs.getInt("user_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}