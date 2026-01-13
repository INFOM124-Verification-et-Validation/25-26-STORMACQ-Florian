package com.gildedrose;

public class NormalItemUpdater implements ItemUpdater {
    
    @Override
    public void update(Item item) {
        // Decrease quality
        if (item.quality > 0) {
            item.quality--;
        }
        
        // Decrease sellIn
        item.sellIn--;
        
        // Once sell by date has passed, quality degrades twice as fast
        if (item.sellIn < 0 && item.quality > 0) {
            item.quality--;
        }
    }
}
