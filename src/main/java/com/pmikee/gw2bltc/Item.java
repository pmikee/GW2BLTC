package com.pmikee.gw2bltc;

/**
 *
 * @author Miklós
 */
public class Item {
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

    public Item(int dataId, String name, int level, int rarity, int vendor, int sellPrice, int sellCount, int buyPrice, int buyCount, String image) {
        this.dataId = dataId;
        this.name = name;
        this.level = level;
        this.rarity = rarity;
        this.vendor = vendor;
        this.sellPrice = sellPrice;
        this.sellCount = sellCount;
        this.buyPrice = buyPrice;
        this.buyCount = buyCount;
        this.image = image;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getVendor() {
        return vendor;
    }

    public void setVendor(int vendor) {
        this.vendor = vendor;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" + "dataId=" + dataId + ", name=" + name + ", level=" + level + ", rarity=" + rarity + ", vendor=" + vendor + ", sellPrice=" + sellPrice + ", sellCount=" + sellCount + ", buyPrice=" + buyPrice + ", buyCount=" + buyCount + ", image=" + image + '}';
    }
    
    
}
