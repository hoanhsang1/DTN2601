package org.example.backend.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.backend.repository.IDepartmentRepository;
import org.example.entity.Department;
import org.example.utils.JDBCUtils;

public class DepartmentRepositoryImpl implements IDepartmentRepository {

    private Department mapRow(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("department_id"), rs.getString("department_name"));
    }

    @Override
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from department order by department_id asc");
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, st, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Department findById(int id) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select * from department where department_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Department dep = rs.next() ? mapRow(rs) : null;
            JDBCUtils.close(conn, ps, rs);
            return dep;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Department> findByName(String name) {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select * from department where department_name like ? order by department_id asc");
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, ps, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Department> findMostEmployees() {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql = "select d.department_id, d.department_name " +
                         "from department d " +
                         "join account a on d.department_id = a.department_id " +
                         "group by d.department_id, d.department_name " +
                         "having count(a.account_id) = (" +
                         "  select max(cnt) from (" +
                         "    select count(account_id) as cnt from account group by department_id" +
                         "  ) t)";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, st, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Department> findLeastEmployees() {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql = "select d.department_id, d.department_name " +
                         "from department d " +
                         "left join account a on d.department_id = a.department_id " +
                         "group by d.department_id, d.department_name " +
                         "having count(a.account_id) = (" +
                         "  select min(cnt) from (" +
                         "    select count(a2.account_id) as cnt from department d2 " +
                         "    left join account a2 on d2.department_id = a2.department_id " +
                         "    group by d2.department_id" +
                         "  ) t)";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, st, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ validation

    @Override
    public boolean existsById(int id) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from department where department_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next() && rs.getInt(1) > 0;
            JDBCUtils.close(conn, ps, rs);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByName(String name) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from department where department_name like ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next() && rs.getInt(1) > 0;
            JDBCUtils.close(conn, ps, rs);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByNameExcludeId(String name, int excludeId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from department where department_name like ? and department_id <> ?");
            ps.setString(1, name);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next() && rs.getInt(1) > 0;
            JDBCUtils.close(conn, ps, rs);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ------------------------------------------------------------------ CRUD

    @Override
    public boolean create(String name) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "insert into department (department_name) values (?)");
            ps.setString(1, name);
            int c = ps.executeUpdate();
            JDBCUtils.close(conn, ps, null);
            return c > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, String name) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "update department set department_name = ? where department_id = ?");
            ps.setString(1, name);
            ps.setInt(2, id);
            int c = ps.executeUpdate();
            JDBCUtils.close(conn, ps, null);
            return c > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "delete from department where department_id = ?");
            ps.setInt(1, id);
            int c = ps.executeUpdate();
            JDBCUtils.close(conn, ps, null);
            return c > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
