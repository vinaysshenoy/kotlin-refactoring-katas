package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
  override fun toString(): String {
    return this.name + ", " + this.sellIn + ", " + this.quality
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Item

    if (sellIn != other.sellIn) return false
    if (quality != other.quality) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = sellIn
    result = 31 * result + quality
    result = 31 * result + name.hashCode()
    return result
  }
}

interface CalculateQuality {
  fun calculateQuality(): Int
}

interface CalculateSellIn {
  fun calculateSellIn(): Int
}

abstract class GildedRoseItem(name: String, sellIn: Int, quality: Int) : CalculateQuality, CalculateSellIn,
  Item(name, sellIn, quality)

class AgedBrie(
  sellIn: Int,
  quality: Int
): GildedRoseItem("Aged Brie", sellIn, quality) {
  override fun calculateQuality(): Int {
    return (quality + 1).coerceAtMost(50)
  }

  override fun calculateSellIn(): Int {
    return sellIn - 1
  }
}
