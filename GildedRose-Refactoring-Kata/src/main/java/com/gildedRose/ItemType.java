package com.gildedrose;

public enum ItemType {
    NORMAL,
    AGED_BRIE,
    BACKSTAGE_PASS,
    SULFURAS;

    @Override
    public String toString() {
        switch(this) {
            case NORMAL:
                return "Normal Item";
            case AGED_BRIE:
                return "Aged Brie";
            case BACKSTAGE_PASS:
                return "Backstage passes to a TAFKAL80ETC concert";
            case SULFURAS:
                return "Sulfuras, Hand of Ragnaros";
            default:
                return "Unknown Item";
        }
    }
}