package com.vti.backend;

import com.vti.entity.Position;
import com.vti.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QLPosition {

    public void getListPosition() {

        try {

            Connection conn = ConnectionUtils.getConnection();

            String sql = "select * from position";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Position position = new Position();

                position.setPositionId(rs.getInt("position_id"));
                position.setPositionName(rs.getString("position_name"));

                System.out.println(position);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}