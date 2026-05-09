package com.vti.backend;

import com.vti.entity.Account;
import com.vti.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QLAccount {

    public void getListAccount() {

        try {

            Connection conn = ConnectionUtils.getConnection();

            String sql = "select * from account";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Account account = new Account();

                account.setAccountId(rs.getInt("account_id"));
                account.setEmail(rs.getString("email"));
                account.setUsername(rs.getString("username"));
                account.setFullname(rs.getString("fullname"));
                account.setDepartmentId(rs.getInt("department_id"));
                account.setPositionId(rs.getInt("position_id"));
                account.setCreateDate(rs.getDate("create_date"));

                System.out.println(account);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}