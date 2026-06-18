package java.org.example.backend.repository.impl;

import java.org.example.backend.repository.IDepartmentRepository;
import java.org.example.entity.Department;
import java.org.example.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRepositoryImpl implements IDepartmentRepository {

    private Department mapRow(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("department_id"), rs.getString("department_name"));
    }

    @Override
    public Department findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(
                    "SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = ?"
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Department dep = rs.next() ? mapRow(rs) : null;
            return dep;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeConnection(conn, ps, rs);
        return null;
    }
}
