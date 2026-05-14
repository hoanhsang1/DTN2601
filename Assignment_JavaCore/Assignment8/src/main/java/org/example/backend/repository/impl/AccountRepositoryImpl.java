package org.example.backend.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.backend.repository.IAccountRepository;
import org.example.entity.Account;
import org.example.entity.Department;
import org.example.entity.Position;
import org.example.enums.PositionName;
import org.example.utils.JDBCUtils;

public class AccountRepositoryImpl implements IAccountRepository {

    private static final String SELECT_BASE =
            "select a.account_id, a.username, a.full_name, a.email, a.create_date, " +
            "d.department_id, d.department_name, p.position_id, p.position_name " +
            "from account a " +
            "join department d on a.department_id = d.department_id " +
            "join position p on a.position_id = p.position_id ";

    private Account mapRow(ResultSet rs) throws SQLException {
        Department dep = new Department(rs.getInt("department_id"), rs.getString("department_name"));
        Position pos = new Position(rs.getInt("position_id"), PositionName.valueOf(rs.getString("position_name")));
        return new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("full_name"),
                rs.getString("email"),
                dep, pos,
                rs.getDate("create_date")
        );
    }

    @Override
    public List<Account> findAll() {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_BASE + "order by a.account_id asc");
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, st, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Account findById(int id) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_BASE + "where a.account_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Account acc = rs.next() ? mapRow(rs) : null;
            JDBCUtils.close(conn, ps, rs);
            return acc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findByName(String name) {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    SELECT_BASE + "where a.full_name like ? order by a.account_id asc");
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
    public boolean create(String username, String fullName, String email, int departmentId, int positionId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "insert into account (username, full_name, email, department_id, position_id, create_date) " +
                    "values (?, ?, ?, ?, ?, now())");
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setInt(4, departmentId);
            ps.setInt(5, positionId);
            int c = ps.executeUpdate();
            JDBCUtils.close(conn, ps, null);
            return c > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, String username, String fullName, String email, int departmentId, int positionId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "update account set username = ?, full_name = ?, email = ?, " +
                    "department_id = ?, position_id = ? where account_id = ?");
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setInt(4, departmentId);
            ps.setInt(5, positionId);
            ps.setInt(6, id);
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
            PreparedStatement ps = conn.prepareStatement("delete from account where account_id = ?");
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
