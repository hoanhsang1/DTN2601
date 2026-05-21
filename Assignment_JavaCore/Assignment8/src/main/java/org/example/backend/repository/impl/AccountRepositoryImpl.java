package org.example.backend.repository.impl;

import org.example.backend.repository.IAccountRepository;
import org.example.entity.Account;
import org.example.entity.Department;
import org.example.entity.Position;
import org.example.enums.PositionName;
import org.example.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements IAccountRepository {

    private static final String SELECT_BASE =
            "select a.account_id, a.username, a.fullname, a.email, a.create_date, " +
            "d.department_id, d.department_name, p.position_id, p.position_name " +
            "from account a " +
            "join department d on a.department_id = d.department_id " +
            "join position p on a.position_id = p.position_id ";

    private Account mapRow(ResultSet rs) throws SQLException {
        Department dep = new Department(rs.getInt("department_id"), rs.getString("department_name"));
        Position pos = new Position(rs.getInt("position_id"),
                PositionName.fromDb(rs.getString("position_name")));
        return new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("fullname"),
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
                    SELECT_BASE + "where a.fullname like ? order by a.account_id asc");
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, ps, rs);
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
                    "select count(1) from account where account_id = ?");
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
    public boolean existsByUsername(String username) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from account where username = ?");
            ps.setString(1, username);
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
    public boolean existsByUsernameExcludeId(String username, int excludeId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from account where username = ? and account_id <> ?");
            ps.setString(1, username);
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

    @Override
    public boolean existsByEmail(String email) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from account where email = ?");
            ps.setString(1, email);
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
    public boolean existsByEmailExcludeId(String email, int excludeId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select count(1) from account where email = ? and account_id <> ?");
            ps.setString(1, email);
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
    public boolean create(String username, String fullName, String email, int departmentId, int positionId) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "insert into account (username, fullname, email, department_id, position_id, create_date) " +
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
                    "update account set username = ?, fullname = ?, email = ?, " +
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
            PreparedStatement ps = conn.prepareStatement(
                    "delete from account where account_id = ?");
            ps.setInt(1, id);
            int c = ps.executeUpdate();
            JDBCUtils.close(conn, ps, null);
            return c > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createAccounts(List<Account> accounts) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into account (username, fullname, email, department_id, position_id, create_date) values (?, ?, ?, ?, ?, now());";
            preparedStatement = connection.prepareStatement(sql);
            for (Account account : accounts) {
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getFullName());
                preparedStatement.setString(3, account.getEmail());
                preparedStatement.setInt(4, account.getDepartment().getId());
                preparedStatement.setInt(5, account.getPosition().getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, preparedStatement, null);
        }
        return false;
    }
}
