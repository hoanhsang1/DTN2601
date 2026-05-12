package com.vti.backend;

import com.vti.Enum.PositionName;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QLAccount {

    // Hàm dùng chung để map ResultSet -> Account
    private Account mapAccount(ResultSet rs) throws Exception {
        Department department = new Department();
        department.setDepartmentId(rs.getInt("department_id"));
        department.setDepartmentName(rs.getString("department_name"));

        Position position = new Position();
        position.setPositionId(rs.getInt("position_id"));
        position.setPositionName(PositionName.valueOf(rs.getString("position_name")));

        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setEmail(rs.getString("email"));
        account.setUsername(rs.getString("username"));
        account.setFullname(rs.getString("fullname"));
        account.setDepartmentId(department);
        account.setPositionId(position);
        account.setCreateDate(rs.getDate("create_date"));

        return account;
    }

    // SELECT - trả về List
    public List<Account> getListAccount() {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "SELECT a.*, d.department_name, p.position_name " +
                    "FROM account a " +
                    "JOIN department d ON a.department_id = d.department_id " +
                    "JOIN position p ON a.position_id = p.position_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(mapAccount(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // TÌM KIẾM THEO TÊN (fullname) - trả về List
    public List<Account> findByName(String name) {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "SELECT a.*, d.department_name, p.position_name " +
                    "FROM account a " +
                    "JOIN department d ON a.department_id = d.department_id " +
                    "JOIN position p ON a.position_id = p.position_id " +
                    "WHERE a.fullname LIKE ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapAccount(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // THÊM MỚI - trả về boolean
    public boolean createAccount(Account account) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "INSERT INTO account(email, username, fullname, department_id, position_id, create_date) " +
                    "VALUES (?, ?, ?, ?, ?, NOW())";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getFullname());
            stmt.setInt(4, account.getDepartment().getDepartmentId());
            stmt.setInt(5, account.getPosition().getPositionId());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CẬP NHẬT THEO ID - trả về boolean
    public boolean updateAccount(Account account) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "UPDATE account " +
                    "SET email = ?, username = ?, fullname = ?, department_id = ?, position_id = ? " +
                    "WHERE account_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getFullname());
            stmt.setInt(4, account.getDepartment().getDepartmentId());
            stmt.setInt(5, account.getPosition().getPositionId());
            stmt.setInt(6, account.getAccountId());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // XÓA THEO ID - trả về boolean
    public boolean deleteAccount(int id) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "DELETE FROM account WHERE account_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // XÓA THEO TÊN (fullname) - trả về boolean
    public boolean deleteByName(String name) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            String sql = "DELETE FROM account WHERE fullname = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}