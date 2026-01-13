package com.gildedrose;

public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = new Item[] {
                new Item(ItemType.NORMAL, 10, 20), // +5 Dexterity Vest
                new Item(ItemType.AGED_BRIE, 2, 0), // Aged Brie
                new Item(ItemType.NORMAL, 5, 7), // Elixir of the Mongoose
                new Item(ItemType.SULFURAS, 0, 80), // Sulfuras, Hand of Ragnaros
                new Item(ItemType.SULFURAS, -1, 80), // Sulfuras, Hand of Ragnaros
                new Item(ItemType.BACKSTAGE_PASS, 15, 20), // Backstage passes
                new Item(ItemType.BACKSTAGE_PASS, 10, 49), // Backstage passes
                new Item(ItemType.BACKSTAGE_PASS, 5, 49), // Backstage passes
                new Item(ItemType.NORMAL, 3, 6) // Conjured Mana Cake (treated as normal for now)
        };

        GildedRose app = new GildedRose(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
