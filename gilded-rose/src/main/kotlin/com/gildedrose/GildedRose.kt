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

      when {
        itemToProcess is AgedBrie || itemToProcess is BackstagePass || itemToProcess is Sulfuras -> {
          itemToProcess.quality = (itemToProcess as GildedRoseItem).calculateQuality()
          itemToProcess.sellIn = (itemToProcess as GildedRoseItem).calculateSellIn()
        }

        else -> {
          val qualityDecrement = if (itemToProcess.sellIn <= 0) 2 else 1
          itemToProcess.quality = (itemToProcess.quality - qualityDecrement).coerceAtLeast(0)
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
      }
    }

    return items.toList()
  }
}
