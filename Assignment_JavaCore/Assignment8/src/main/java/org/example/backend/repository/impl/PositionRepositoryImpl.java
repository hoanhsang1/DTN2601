package org.example.backend.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.backend.repository.IPositionRepository;
import org.example.entity.Position;
import org.example.enums.PositionName;
import org.example.utils.JDBCUtils;

public class PositionRepositoryImpl implements IPositionRepository {

    private Position mapRow(ResultSet rs) throws SQLException {
        return new Position(rs.getInt("position_id"), PositionName.valueOf(rs.getString("position_name")));
    }

    @Override
    public List<Position> findAll() {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from position order by position_id asc");
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, st, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Position findById(int id) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from position where position_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Position pos = rs.next() ? mapRow(rs) : null;
            JDBCUtils.close(conn, ps, rs);
            return pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Position> findByName(String name) {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select * from position where position_name like ? order by position_id asc");
            ps.setString(1, "%" + name.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            JDBCUtils.close(conn, ps, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Position> findMostEmployees() {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql = "select p.position_id, p.position_name " +
                         "from position p " +
                         "join account a on p.position_id = a.position_id " +
                         "group by p.position_id, p.position_name " +
                         "having count(a.account_id) = (" +
                         "  select max(cnt) from (" +
                         "    select count(account_id) as cnt from account group by position_id" +
                         "  ) t" +
                         ")";
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
    public List<Position> findLeastEmployees() {
        List<Position> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql = "select p.position_id, p.position_name " +
                         "from position p " +
                         "left join account a on p.position_id = a.position_id " +
                         "group by p.position_id, p.position_name " +
                         "having count(a.account_id) = (" +
                         "  select min(cnt) from (" +
                         "    select count(a2.account_id) as cnt from position p2 " +
                         "    left join account a2 on p2.position_id = a2.position_id " +
                         "    group by p2.position_id" +
                         "  ) t" +
                         ")";
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
    public boolean create(String name) {
        try {
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into position (position_name) values (?)");
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
                    "update position set position_name = ? where position_id = ?");
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
            PreparedStatement ps = conn.prepareStatement("delete from position where position_id = ?");
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
