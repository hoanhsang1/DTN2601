package com.vti.backend;

import com.vti.Enum.Sex;
import com.vti.entity.CanBo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Excercise1 {
    public static void getVanBo() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/qlcb";
        String username = "root";
        String password = "S1a2n3g4@2006";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        if(con!=null){
            System.out.println("Thanhf ccoong");
        }else {
            System.out.println("That bai");
        }
        String address;

        String query = "select * from can_bo";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<CanBo> canBos = new ArrayList<>();
        while (rs.next()) {
            String fullName = rs.getString( "full_name");
            int age = rs.getInt ( "age");
            Sex gioiTinh = Sex.valueOf(rs.getString( "gioi_tinh");
            String address = rs.getString( "address");
            CanBo canBo = new CanBo(fullName,age,gioiTinh,address);
            canBos.add(canBo);
        }

    }
}
