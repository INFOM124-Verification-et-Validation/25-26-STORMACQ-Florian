package com.gildedrose;

public class SulfurasItemUpdater implements ItemUpdater {
    
    @Override
    public void update(Item item) {
        // Sulfuras never has to be sold or decreases in quality
        // It's a legendary item with quality always 80
        // Do nothing
    }
}
