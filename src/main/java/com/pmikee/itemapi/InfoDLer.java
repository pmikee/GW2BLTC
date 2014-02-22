/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmikee.itemapi;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 *
 * @author Miklós
 */
public class InfoDLer {

    private static final SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    Map<String, Integer> itemtype = new TreeMap<>();

    public InfoDLer() {
    }

    public ArrayList<Integer> getItemIds() {
        ArrayList<Integer> itemIds = new ArrayList<>();
        try {
            String requestUrl = "https://api.guildwars2.com/v1/items.json";
            JSONObject outer = buildConnectionProperties(new URL(requestUrl));
            JSONArray items = (JSONArray) outer.get("items");
            for (int i = 0; i < items.size(); i++) {
//                itemIds.add((Integer) items.get(i));
                getItemName((Integer) items.get(i));
                System.out.println(i);
            }
            Set<String> keys = itemtype.keySet();
            for(String s  : keys){
                System.out.println(s + "-" + itemtype.get(s));
            }
            
        } catch (IOException e) {
            Logger.getLogger(InfoDLer.class.getName()).log(Level.SEVERE, null, e);
        }
        return itemIds;
    }

    private void getItemName(int id) {
        try {

            String requestUrl = "https://api.guildwars2.com/v1/item_details.json?item_id=" + id;
            JSONObject outer = buildConnectionProperties(new URL(requestUrl));
            itemtype.put((String) outer.get("type"), id);
        } catch (IOException e) {
            Logger.getLogger(InfoDLer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private JSONObject buildConnectionProperties(URL url) throws ProtocolException, IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(socketFactory);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        JSONObject outer = (JSONObject) JSONValue.parse(connection.getInputStream());
        return outer;
    }

}
