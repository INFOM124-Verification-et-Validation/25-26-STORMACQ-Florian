package com.gildedrose;

public class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemType itemType = item.getItemType();

            ItemUpdater updater = ItemUpdaterFactory.getItemUpdater(itemType);
            
            updater.update(item);
        }
    }
}