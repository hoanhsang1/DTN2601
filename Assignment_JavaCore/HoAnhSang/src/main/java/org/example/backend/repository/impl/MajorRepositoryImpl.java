package org.example.backend.repository.impl;

import org.example.backend.repository.IMajorRepository;
import org.example.entity.Major;
import org.example.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Hiện thực IMajorRepository, thực thi câu lệnh SQL với bảng Major
public class MajorRepositoryImpl implements IMajorRepository {

    // Lấy toàn bộ danh sách chuyên ngành từ bảng Major
    @Override
    public List<Major> findAll() {
        String sql = "SELECT major_id, major_name FROM Major ORDER BY major_id";

        List<Major> majors = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                majors.add(new Major(rs.getInt("major_id"), rs.getString("major_name")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách chuyên ngành: " + e.getMessage());
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }

        return majors;
    }
}
