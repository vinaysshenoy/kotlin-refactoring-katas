package com.gildedrose

class GildedRose(var items: Array<Item>) {
  companion object {
    fun create(vararg items: Item = emptyArray()): GildedRose {
      return GildedRose(arrayOf(*items))
    }
  }

  fun updateQuality(): List<Item> {
    items.onEach {
      val itemToProcess = it as GildedRoseItem
      itemToProcess.quality = itemToProcess.calculateQuality()
      itemToProcess.sellIn = itemToProcess.calculateSellIn()
    }

    return items.toList()
  }
}
