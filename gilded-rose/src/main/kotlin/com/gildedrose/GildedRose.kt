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
          if (itemToProcess.sellIn <= 0) {
            itemToProcess.quality = 0
          } else {
            val qualityIncrement = when {
              itemToProcess.sellIn <= 5 -> 3
              itemToProcess.sellIn <= 10 -> 2
              else -> 1
            }
            itemToProcess.quality = (itemToProcess.quality + qualityIncrement).coerceAtMost(50)
          }
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
        "Sulfuras, Hand of Ragnaros" -> {}
        else ->  {
          val qualityDecrement = if (itemToProcess.sellIn <= 0) 2 else 1
          itemToProcess.quality = (itemToProcess.quality - qualityDecrement).coerceAtLeast(0)
          itemToProcess.sellIn = itemToProcess.sellIn - 1
        }
      }
    }

    return items.toList()
  }
}
