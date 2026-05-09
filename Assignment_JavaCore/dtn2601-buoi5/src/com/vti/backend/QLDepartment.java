package com.vti.backend;

import com.vti.entity.Department;
import com.vti.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QLDepartment {

    public void getListDepartment() {

        try {

            Connection conn = ConnectionUtils.getConnection();

            String sql = "select * from department";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Department department = new Department();

                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));

                System.out.println(department);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}