package com.pmikee.gw2bltc;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class ItemList {

    private final Database db;
    private static final String DBNAME = "bltc_items";
    private final Util util;
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");
    public String SetupClient() {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setUseInsecureSSL(true);

            final HtmlPage page = webClient.getPage("https://account.guildwars2.com/login?redirect_uri=http%3A%2F%2Ftradingpost-live.ncplatform.net%2Fauthenticate%3Fsource%3D%252F&game_code=gw2");
            final HtmlForm form = (HtmlForm) page.getFirstByXPath("//form[@class=\"login-panel async-form\"]");

            final HtmlButton button = form.getFirstByXPath("//form[@class=\"login-panel async-form\"]//div[@class=\"yui3-g\"]//button[@class=\"yui3-button yui3-u\"]");
            final HtmlTextInput textField = form.getInputByName("email");
            final HtmlPasswordInput pwField = form.getInputByName("password");

            textField.setValueAttribute(USERNAME);
            pwField.setValueAttribute(PASSWORD);

            final HtmlPage page2 = button.click();
            final Page page3
                    = webClient.getPage("https://tradingpost-live.ncplatform.net/ws/search.json"
                            + "?text=&levelmin=0&levelmax=80&count=0");
            return page3.getWebResponse().getContentAsString();
        } catch (IOException | FailingHttpStatusCodeException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ItemList(Database db) {
        this.util = new Util();
        this.db = db;
    }

    public void getItemList(Boolean isUpdate) {
        List<Item> items = new ArrayList<>();
        try {
            JSONObject outer = (JSONObject) JSONValue.parse(SetupClient());
//            JSONObject outer = (JSONObject) JSONValue.parse(new FileReader(new File("C:/Users/Miklós/Downloads/search.json")));
            JSONArray itemjson = (JSONArray) outer.get("results");
            for (int i = 0; i < itemjson.size(); i++) {
                JSONObject json = (JSONObject) itemjson.get(i);
                items.add(util.generateItem(json));
            }
            if (isUpdate) {
                updateItemPrices(items);
            } else {
                db.itemToDataBase(items);
            }
        } catch (ParseException e) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, e);
        }
//        catch (FileNotFoundException ex) {
//            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void updateItemPrices(List<Item> items) {
        try {
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            db.conn.setAutoCommit(false);
            PreparedStatement pst = db.conn.prepareStatement("UPDATE " + DBNAME + " SET sell_price=?,sell_count = ?,"
                    + "buy_price = ?, buy_count = ?, date_updated = ? where data_id=?;");
            int i = 0;
            for (Item item : items) {
                pst.setInt(1, item.getSellPrice());
                pst.setInt(2, item.getSellCount());
                pst.setInt(3, item.getBuyPrice());
                pst.setInt(4, item.getBuyCount());
                pst.setTimestamp(5, date);
                pst.setInt(6, item.getDataId());
                pst.addBatch();
//                System.out.println(i++);
            }
            int[] numofqueries = pst.executeBatch();
            System.out.println(numofqueries.length);
            db.conn.commit();
//            Logger.getLogger(Database.class.getName()).log(Level.INFO, item.toString());
        } catch (SQLException e) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
