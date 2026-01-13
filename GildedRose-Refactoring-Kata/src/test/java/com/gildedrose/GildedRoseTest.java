package com.gildedrose;

import com.gildedrose.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void testNormalItemDecreasesQuality() {
        Item[] items = new Item[] { 
            new Item(ItemType.NORMAL, 10, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    @Test
    void testAgedBrieIncreasesQuality() {
        Item[] items = new Item[] { 
            new Item(ItemType.AGED_BRIE, 10, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(9, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    void testSulfurasNeverChanges() {
        Item[] items = new Item[] { 
            new Item(ItemType.SULFURAS, 10, 80) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(10, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void testBackstagePassIncreasesQuality() {
        Item[] items = new Item[] { 
            new Item(ItemType.BACKSTAGE_PASS, 15, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(14, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    void testBackstagePassIncreasesFasterAt10Days() {
        Item[] items = new Item[] { 
            new Item(ItemType.BACKSTAGE_PASS, 10, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(9, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);
    }

    @Test
    void testBackstagePassIncreasesFasterAt5Days() {
        Item[] items = new Item[] { 
            new Item(ItemType.BACKSTAGE_PASS, 5, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(4, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);
    }

    @Test
    void testBackstagePassDropsToZeroAfterConcert() {
        Item[] items = new Item[] { 
            new Item(ItemType.BACKSTAGE_PASS, 0, 20) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void testQualityNeverNegative() {
        Item[] items = new Item[] { 
            new Item(ItemType.NORMAL, 10, 0) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(9, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void testQualityNeverExceeds50() {
        Item[] items = new Item[] { 
            new Item(ItemType.AGED_BRIE, 10, 50) 
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        
        assertEquals(9, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
    }

}
