package com.pmikee.gw2bltc;

import com.pmikee.itemapi.InfoDLer;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) {
        try {
//            new InfoDLer().getItemIds();
            Database db = new Database();
            ItemList il = new ItemList(db);
            System.out.println("Start");
            il.getItemList(false);
            System.out.println("End");
            db.conn.close();
            Thread.sleep(1800000);
            while (true) {
                try {
                    db.connect();
                    Date d = new Date();
                    d.getTime();
                    System.out.println("Start: " + d.toString());
                    il.getItemList(true);
                    db.conn.close();
                    Date e = new Date();
                    e.getTime();
                    System.out.println("End: " + e.toString());
                    Thread.sleep(1800000);
                } catch (SQLException | InterruptedException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
