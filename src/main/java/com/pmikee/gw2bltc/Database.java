package com.pmikee.gw2bltc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miki
 */
public final class Database {

    public Connection conn;
    public static final String DBNAME = "bltc_items";

    public Connection connect() {
        String url = "jdbc:mysql://192.168.1.43:3306/gw2";
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Hiba a kapcsolódás során", e);
        }
        return conn;
    }

    public void itemToDataBase(List<Item> items) {
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

        try {
            PreparedStatement pst = conn.prepareStatement("INSERT IGNORE INTO " + DBNAME + " (data_id, name, level, rarity, vendor, sell_price,"
                    + " sell_count, buy_price, buy_count, image, date_added) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            int i = 0;
            conn.setAutoCommit(false);
            for (Item item : items) {
                pst.setInt(1, item.getDataId());
                pst.setString(2, item.getName());
                pst.setInt(3, item.getLevel());
                pst.setInt(4, item.getRarity());
                pst.setInt(5, item.getVendor());
                pst.setInt(6, item.getSellPrice());
                pst.setInt(7, item.getSellCount());
                pst.setInt(8, item.getBuyPrice());
                pst.setInt(9, item.getBuyCount());
                pst.setString(10, item.getImage());
                pst.setTimestamp(11, date);
                pst.addBatch();
//                System.out.println(i++);
            }
            pst.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close() throws SQLException {
        conn.close();
    }

    public Database() {
        conn = connect();
    }

    public Item getItemFromDB(int id) throws SQLException {
        Statement stmt;
        ResultSet rs = null;
        int numColumns;
        try {
            stmt = conn.createStatement();
            String query = "select * from " + DBNAME + " where data_id=" + id + ";";
            rs = (ResultSet) stmt.executeQuery(query);
            try {
                rs.next();
                numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                }

            } catch (SQLException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
            }

        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        return (new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
                rs.getInt(8), rs.getInt(9), rs.getString(10)));
    }
}
