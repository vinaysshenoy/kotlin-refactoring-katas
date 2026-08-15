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

class BackstagePass(
  sellIn: Int,
  quality: Int
): GildedRoseItem(name = "Backstage passes to a TAFKAL80ETC concert", sellIn, quality) {
  override fun calculateQuality(): Int {
    val qualityIncrement = computeDemandDrivenQuality(sellIn)
    return (quality + qualityIncrement).coerceAtMost(50)
  }

  private fun computeDemandDrivenQuality(sellWithinDays: Int): Int = when {
    sellWithinDays <= 0 -> -quality
    sellWithinDays <= 5 -> 3
    sellWithinDays <= 10 -> 2
    else -> 1
  }

  override fun calculateSellIn(): Int {
    return sellIn - 1
  }
}

class Sulfuras(sellIn: Int): GildedRoseItem(
  name = "Sulfuras, Hand of Ragnaros",
  sellIn = sellIn,
  quality = 80
) {
  override fun calculateQuality(): Int {
    return quality
  }

  override fun calculateSellIn(): Int {
    return sellIn
  }
}

class GeneralItem(
  name: String,
  sellIn: Int,
  quality: Int
): GildedRoseItem(name, sellIn, quality) {
  override fun calculateQuality(): Int {
    val qualityDecrement = if (sellIn <= 0) 2 else 1
    return (quality - qualityDecrement).coerceAtLeast(0)
  }

  override fun calculateSellIn(): Int {
    return sellIn - 1
  }
}
