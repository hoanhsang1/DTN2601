package org.example.backend.repository.impl;

import org.example.backend.repository.IStudentRepository;
import org.example.entity.Major;
import org.example.entity.Student;
import org.example.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Hiện thực IStudentRepository, thực thi các câu lệnh SQL với DB
public class StudentRepositoryImpl implements IStudentRepository {

    // Kiểm tra đăng nhập bằng email và password trong bảng Account
    @Override
    public boolean login(String email, String password) {
        String sql = "SELECT * FROM Account WHERE email = ? AND password = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
    }

    // Lấy toàn bộ danh sách sinh viên, JOIN với Major để lấy tên chuyên ngành
    @Override
    public List<Student> findAll() {
        String sql = "SELECT s.student_id, s.full_name, s.email, s.date_of_birth, " +
                     "       m.major_id, m.major_name " +
                     "FROM Student s " +
                     "LEFT JOIN Major m ON s.major_id = m.major_id " +
                     "ORDER BY s.student_id";

        List<Student> students = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }

        return students;
    }

    // Kiểm tra email sinh viên đã tồn tại trong bảng Student chưa
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Student WHERE email = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra email: " + e.getMessage());
            return false;
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
    }

    // Thêm mới sinh viên vào bảng Student
    @Override
    public boolean insert(Student student) {
        String sql = "INSERT INTO Student (full_name, email, date_of_birth, major_id) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getStudentFullName());
            ps.setString(2, student.getStudentEmail());

            if (student.getStudentBirthday() != null) {
                ps.setDate(3, new java.sql.Date(student.getStudentBirthday().getTime()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            if (student.getMajor() != null) {
                ps.setInt(4, student.getMajor().getMajorId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            return false;
        } finally {
            JDBCUtils.close(conn, ps, null);
        }
    }

    // Cập nhật chuyên ngành mới cho sinh viên theo studentId và majorId
    @Override
    public boolean updateMajor(int studentId, int majorId) {
        String sql = "UPDATE Student SET major_id = ? WHERE student_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, majorId);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chuyên ngành: " + e.getMessage());
            return false;
        } finally {
            JDBCUtils.close(conn, ps, null);
        }
    }

    // Xóa sinh viên theo student_id
    @Override
    public boolean deleteById(int studentId) {
        String sql = "DELETE FROM Student WHERE student_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sinh viên: " + e.getMessage());
            return false;
        } finally {
            JDBCUtils.close(conn, ps, null);
        }
    }

    // Tìm sinh viên theo tên chuyên ngành (tìm gần đúng, không phân biệt hoa thường)
    @Override
    public List<Student> findByMajorName(String majorName) {
        String sql = "SELECT s.student_id, s.full_name, s.email, s.date_of_birth, " +
                     "       m.major_id, m.major_name " +
                     "FROM Student s " +
                     "LEFT JOIN Major m ON s.major_id = m.major_id " +
                     "WHERE m.major_name LIKE ? " +
                     "ORDER BY s.student_id";

        List<Student> students = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + majorName + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm theo chuyên ngành: " + e.getMessage());
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }

        return students;
    }

    // Ánh xạ một dòng ResultSet sang đối tượng Student
    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        int studentId    = rs.getInt("student_id");
        String fullName  = rs.getString("full_name");
        String email     = rs.getString("email");
        java.util.Date dob = rs.getDate("date_of_birth");

        int majorId      = rs.getInt("major_id");
        String majorName = rs.getString("major_name");
        Major major      = (majorId != 0) ? new Major(majorId, majorName) : null;

        return new Student(studentId, fullName, email, dob, major);
    }
}
