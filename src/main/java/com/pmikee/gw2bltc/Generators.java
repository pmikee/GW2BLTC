/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmikee.gw2bltc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Miki
 */
public class Generators {

    Database db = new Database();
    private static final String DBNAME = "item3";
    int col;
    ResultSetMetaData md;

    public Generators(Database db) {
        this.db = db;
    }

    private void getItemColumns() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + DBNAME + " limit 1");
            md = rs.getMetaData();
            col = md.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getFlippables() throws SQLException {
        Statement st = db.conn.createStatement();
        String lowestsell = md.getColumnName(md.getColumnCount());
        String highestbuy = md.getColumnName(md.getColumnCount() - 1);
        ResultSet rs = st.executeQuery("select name,( " + lowestsell + " * 0.85 - " + highestbuy + " ), "
                + lowestsell + ", " + highestbuy
                + " from " + DBNAME + " where ( " + lowestsell + " * 0.85 - " + highestbuy
                + " ) > 1000 and rarity between 2 and 6 "
                + " and " + lowestsell + "<200000 and name not like '%Recipe%' and name not like '%Satchel%' "
                + " and name not like '%Pot%' and name not like '%Tray%' and name not like '%Box%' and "
                + lowestsell + ">0 and " + highestbuy + ">0 "
                + " order by ( " + lowestsell + " * 0.85 -" + highestbuy + " ) desc;");
        ResultSetMetaData metad = rs.getMetaData();
    }

    private void getWeaponPricesList() throws SQLException {
        List<Integer> subtypes = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 8, 10, 11, 12, 16);
        List<String> names = Arrays.asList("Sword", "Hammer", "Longbow", "Shortbow", "Axe", "Dagger", "Greatsword",
                "Pistol", "Rifle", "Scepter", "Staff", "Shield");
        List<String> szoveg = new ArrayList<>();
        Statement st = db.conn.createStatement();
        String lowestsell = md.getColumnName(md.getColumnCount());
        String highestbuy = md.getColumnName(md.getColumnCount() - 1);
        int index = 0;
        for (Integer i : subtypes) {
            for (int j = 1; j < 8; j++) {
                if (j != 6) {
                    ResultSet rs = st.executeQuery("select name, "
                            + lowestsell + ", " + highestbuy + " , ( " + lowestsell + " * 0.85 - " + highestbuy + " ), "
                            + "restriction_level from " + DBNAME + " where rarity= " + j
                            + " and type_id=18 and sub_type_id= " + i
                            + " and " + highestbuy + " > 1 and " + lowestsell + ">0 "
                            + " and restriction_level > 70 "
                            + " order by ( " + lowestsell + " * 0.85 - " + highestbuy + " ) desc; ");
                    ResultSetMetaData metad = rs.getMetaData();

                }

            }
        }
    }

    private void generateProfit() throws SQLException {
        Statement st = db.conn.createStatement();
        String lowestsell = md.getColumnName(md.getColumnCount());
        String highestbuy = md.getColumnName(md.getColumnCount() - 1);

        ResultSet rs = st.executeQuery("select name,( " + lowestsell + " * 0.85 - " + highestbuy + " ) , "
                + md.getColumnName(md.getColumnCount() - 2) + ", " + md.getColumnName(md.getColumnCount() - 3)
                + " from " + DBNAME + " where ( " + lowestsell + " *0.85 - " + highestbuy + " ) > 100 "
                + " and rarity between 2 and 6 and " + lowestsell + ">0  and " + highestbuy + ">0 and "
                + "( " + lowestsell + " > " + md.getColumnName(md.getColumnCount() - 4) + " and "
                + md.getColumnName(md.getColumnCount() - 1) + " < " + md.getColumnName(md.getColumnCount() - 5) + " ) "
                + " and " + lowestsell + "<200000 and name not like '%Recipe%' and name not like '%Satchel%' "
                + " and name not like '%Pot%' and name not like '%Tray%' "
                + " order by ( " + lowestsell + " * 0.85 -" + highestbuy + " )  desc;");
        ResultSetMetaData metad = rs.getMetaData();

    }

    public void generateDyeList() throws SQLException {
        getItemColumns();
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + " from " + DBNAME + " where name like '%Dye%' and name not like '%Unidentified%' "
                    + " order by name asc;");
            ResultSetMetaData metad = rs.getMetaData();
         
    }

    private void generateSigilList() throws SQLException {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Sigil%' and rarity = 4 "
                    + " and name not like '%Recipe%' "
                    + " order by " + highestbuy + " asc;");
            ResultSetMetaData metad = rs.getMetaData();
           
    }

    private void generateExoSigilList() throws SQLException {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Sigil%' and rarity = 5 "
                    + " and name not like '%Recipe%' "
                    + " order by " + lowestsell + " desc;");
            ResultSetMetaData metad = rs.getMetaData();
           
    }

    private void generateExoRuneList() throws SQLException {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Rune of%' and rarity = 5 "
                    + " and name not like '%Recipe%' "
                    + " order by " + lowestsell + " desc;");
            ResultSetMetaData metad = rs.getMetaData();  
    }

    private void generateRuneList() throws SQLException {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Rune%' and rarity = 4 "
                    + " and name not like '%Recipe%' "
                    + " order by " + highestbuy + " asc;");
            ResultSetMetaData metad = rs.getMetaData();
     
    }
}
