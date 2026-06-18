package org.example.backend.repository;

import org.example.entity.Admin;
import org.example.entity.Employee;
import org.example.entity.User;
import org.example.enums.ProSkill;
import org.example.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements IUserRepository {
    private User mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String fullname = rs.getString("FullName");
        String email = rs.getString("Email");
        String password = rs.getString("Password");
        String role = rs.getString("role");

        if ("ADMIN".equalsIgnoreCase(role)) {
            int exp = rs.getInt("ExpInYear");
            return new Admin(id, fullname, email, password, exp);
        } else if ("EMPLOYEE".equalsIgnoreCase(role)) {
            String skillStr = rs.getString("ProSkill");
            ProSkill skill = null;
            if (skillStr != null) {
                try {
                    skill = ProSkill.valueOf(skillStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Ignore
                }
            }
            return new Employee(id, fullname, email, password, skill);
        }
        return new User(id, fullname, email, password);
    }

    private Admin mapRowAdmin(ResultSet rs) throws SQLException {
        return new Admin(rs.getInt("id"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Password"), rs.getInt("ExpInYear"));
    }

    private Employee mapRowEmployee(ResultSet rs) throws SQLException {
        String skillStr = rs.getString("ProSkill");
        ProSkill skill = null;
        if (skillStr != null) {
            try {
                skill = ProSkill.valueOf(skillStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore
            }
        }
        return new Employee(rs.getInt("id"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Password"), skill);
    }
    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from users order by id asc");
            while (rs.next()) users.add(mapRow(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn, stmt, rs);
        }

        return users;
    }

    @Override
    public User findById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?;");
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(conn, stmt, rs);
        }

    }

    @Override
    public boolean deleteById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JDBCUtils.getConnection();

            stmt = conn.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
            );

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JDBCUtils.close(conn, stmt, null);
        }
    }

    @Override
    public User login(String email, String password) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = JDBCUtils.getConnection();

            stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE Email = ? AND Password = ?"
            );

            stmt.setString(1, email);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {

                if (rs.getObject("ExpInYear") != null) {
                    return mapRowAdmin(rs);
                }

                if (rs.getObject("ProSkill") != null) {
                    return mapRowEmployee(rs);
                }

                return mapRow(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JDBCUtils.close(conn, stmt, rs);
        }
    }

    @Override
    public boolean checkExistEmail(String email) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = JDBCUtils.getConnection();

            stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE Email = ?"
            );

            stmt.setString(1, email);

            rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JDBCUtils.close(conn, stmt, rs);
        }
    }

    @Override
    public boolean createEmployee(String fullname, String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO users (FullName, Email, Password, role) VALUES (?, ?, '123456', 'EMPLOYEE')"
            );
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn, stmt, null);
        }
    }

    @Override
    public boolean registerAdmin(String fullname, String email, String password, int expInYear) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO users (FullName, Email, Password, role, ExpInYear) VALUES (?, ?, ?, 'ADMIN', ?)"
            );
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4, expInYear);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn, stmt, null);
        }
    }

    @Override
    public boolean registerEmployee(String fullname, String email, String password, ProSkill proSkill) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO users (FullName, Email, Password, role, ProSkill) VALUES (?, ?, ?, 'EMPLOYEE', ?)"
            );
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, proSkill.name());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn, stmt, null);
        }
    }

    @Override
    public void initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO users (FullName, Email, Password, role, ExpInYear) VALUES ('Nguyen Van Admin', 'admin.nguyen@vti.com.vn', 'Password123', 'ADMIN', 5)");
                stmt.executeUpdate("INSERT INTO users (FullName, Email, Password, role, ProSkill) VALUES ('Tran Van Employee', 'employee.tran@vti.com.vn', 'Password123', 'EMPLOYEE', 'JAVA')");
                System.out.println("Khởi tạo cơ sở dữ liệu thành công! Đã thêm 1 Admin và 1 Employee.");
            } else {
                System.out.println("Cơ sở dữ liệu đã có dữ liệu, không cần khởi tạo lại.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi khởi tạo database", e);
        } finally {
            JDBCUtils.close(conn, stmt, rs);
        }
    }
}
