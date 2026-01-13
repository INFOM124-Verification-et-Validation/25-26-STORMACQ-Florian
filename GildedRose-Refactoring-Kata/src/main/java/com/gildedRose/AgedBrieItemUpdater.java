package com.gildedrose;

public class AgedBrieItemUpdater implements ItemUpdater {
    
    @Override
    public void update(Item item) {
        // Aged Brie increases in quality as it gets older
        if (item.quality < 50) {
            item.quality++;
        }
        
        // Decrease sellIn
        item.sellIn--;
        
        // After sell by date, quality increases twice as fast
        if (item.sellIn < 0 && item.quality < 50) {
            item.quality++;
        }
    }
}
