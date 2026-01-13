package com.gildedrose;

public class Item {

    public ItemType itemType;

    public int sellIn;

    public int quality;

    public Item(ItemType itemType, int sellIn, int quality) {
        this.itemType = itemType;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public ItemType getItemType() {
        return itemType;
    }

   @Override
   public String toString() {
        return this.itemType.toString() + ", " + this.sellIn + ", " + this.quality;
    }
}
