package com.pmikee.gw2bltc;

import java.text.ParseException;
import net.minidev.json.JSONObject;

public class Util {

    private int dataId;
    private String name;
    private int level;
    private int rarity;
    private int vendor;
    private int sellPrice;
    private int sellCount;
    private int buyPrice;
    private int buyCount;
    private String image;
    JSONObject result;

    public Util() {
    }

    public Item generateItem(JSONObject json) throws ParseException {
        dataId = Integer.parseInt((String) json.get("data_id"));
        name = (String) json.get("name");
        level = Integer.parseInt((String) json.get("level"));
        rarity = Integer.parseInt((String) json.get("rarity"));
        vendor = Integer.parseInt((String) json.get("vendor"));
        sellPrice = Integer.parseInt((String) json.get("sell_price"));
        sellCount = Integer.parseInt((String) json.get("sell_count"));
        buyPrice = Integer.parseInt((String) json.get("buy_price"));
        buyCount = Integer.parseInt((String) json.get("buy_count"));
        image = (String) json.get("img");
        return new Item(dataId, name, level, rarity, vendor, sellPrice, sellCount, buyPrice, buyCount, image);
    }

    void getItemFromDB(int id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
