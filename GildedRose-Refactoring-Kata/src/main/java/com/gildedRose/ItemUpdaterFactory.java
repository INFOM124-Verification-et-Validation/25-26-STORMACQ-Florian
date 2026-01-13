package com.gildedrose;

public class ItemUpdaterFactory {
    
    public static ItemUpdater getItemUpdater(ItemType itemType) {
        switch (itemType) {
            case AGED_BRIE:
                return new AgedBrieItemUpdater();
            case BACKSTAGE_PASS:
                return new BackstagePassItemUpdater();
            case SULFURAS:
                return new SulfurasItemUpdater();
            case NORMAL:
            default:
                return new NormalItemUpdater();
        }
    }
}