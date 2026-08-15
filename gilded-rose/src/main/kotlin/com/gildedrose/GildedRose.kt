package com.gildedrose

class GildedRose(var items: Array<Item>) {
  companion object {
    fun create(vararg items: Item = emptyArray()): GildedRose {
      return GildedRose(arrayOf(*items))
    }
  }

  fun updateQuality(): List<Item> {
    for (i in items.indices) {
      val itemToProcess = items[i]

      when(itemToProcess.name) {
        "Aged Brie" -> {
          itemToProcess.quality = (itemToProcess.quality + 1).coerceAtMost(50)
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
        "Backstage passes to a TAFKAL80ETC concert" -> {
          val qualityIncrement = when {
            itemToProcess.sellIn <= 5 -> 3
            itemToProcess.sellIn <= 10 -> 2
            else -> 1
          }
          itemToProcess.quality = (itemToProcess.quality + qualityIncrement).coerceAtMost(50)
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
        "Sulfuras, Hand of Ragnaros" -> {}
        else ->  {
          itemToProcess.quality = (itemToProcess.quality - 1).coerceAtLeast(0)
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
      }

      if (items[i].sellIn < 0) {
        if (items[i].name != "Aged Brie") {
          if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
            if (items[i].quality > 0) {
              if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].quality = items[i].quality - 1
              }
            }
          } else {
            items[i].quality = items[i].quality - items[i].quality
          }
        } else {
          if (items[i].quality < 50) {
            items[i].quality = items[i].quality + 1
          }
        }
      }
    }

    return items.toList()
  }
}
