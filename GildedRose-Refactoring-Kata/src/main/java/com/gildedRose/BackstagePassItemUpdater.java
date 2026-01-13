package com.gildedrose;

public class BackstagePassItemUpdater implements ItemUpdater {
    
    @Override
    public void update(Item item) {
        // Backstage passes increase in quality as concert approaches
        if (item.quality < 50) {
            item.quality++;
            
            // Quality increases by 2 when there are 10 days or less
            if (item.sellIn <= 10 && item.quality < 50) {
                item.quality++;
            }
            
            // Quality increases by 3 when there are 5 days or less
            if (item.sellIn <= 5 && item.quality < 50) {
                item.quality++;
            }
        }
        
        // Decrease sellIn
        item.sellIn--;
        
        // Quality drops to 0 after the concert
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }
}
