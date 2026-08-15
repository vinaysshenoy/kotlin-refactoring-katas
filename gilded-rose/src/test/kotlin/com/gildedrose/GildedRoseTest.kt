package com.gildedrose

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class GildedRoseTest {

  @Test
  fun `it should handle empty items`() {
    val store = GildedRose.create()
    val newItems = store.updateQuality()
    assertContentEquals(emptyList(), newItems)
  }

  @Test
  fun `item sell in and quality should decrease each day`() {
    val phone = GeneralItem("Phone", 10, 30)
    val newItems = GildedRose.create(phone).updateQuality()

    assertContentEquals(newItems, listOf(
      GeneralItem("Phone", 9, 29),
    ))
  }

  @Test
  fun `post sell by date item quality should degrade twice as fast`() {
    val phone = GeneralItem("Phone", 10, 30)
    val vegetables = GeneralItem("Vegetables", 2, 10)

    val store = GildedRose.create(phone, vegetables)

    // day 1
    var newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      GeneralItem("Phone", 9, 29),
      GeneralItem("Vegetables", 1, 9),
    ))

    // day 2
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      GeneralItem("Phone", 8, 28),
      GeneralItem("Vegetables", 0, 8),
    ))

    // day 3
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      GeneralItem("Phone", 7, 27),
      GeneralItem("Vegetables", -1, 6),
    ))
  }

  @Test
  fun `quality of item can never be negative`() {
    val phone = GeneralItem("Phone", 10, 30)
    val vegetables = GeneralItem("Vegetables", 2, 0)

    val store = GildedRose.create(phone, vegetables)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      GeneralItem("Phone", 9, 29),
      GeneralItem("Vegetables", 1, 0),
    ))
  }

  @Test
  fun `aged brie has to increase in quality with age`() {
    val agedBrie = AgedBrie( 10, 30)

    val store = GildedRose.create(agedBrie)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      AgedBrie(9, 31),
    ))
  }

  @Test
  fun `sulfuras should never be negative quality nor sell by date changes`() {
    val sulfuras1 = Sulfuras(10)
    val sulfuras2 = Sulfuras(-1)

    val store = GildedRose.create(sulfuras1, sulfuras2)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Sulfuras(10),
      Sulfuras(-1),
    ))
  }

  @Test
  fun `backstage passes should increase in quality closer to sell by date changes`() {
    val item = BackstagePass(12, 10)

    val store = GildedRose.create(item)

    // 11 days
    var newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 11, quality = 11),
    ))

    // 10 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 10, quality = 12),
    ))

    // 9 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 9, quality = 14),
    ))

    // 8 -> 5 days
    store.updateQuality()
    store.updateQuality()
    store.updateQuality()
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 5, quality = 22),
    ))

    // 5 -> 4
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 4, quality = 25)
    ))

    // 4 -> 3
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 3, quality = 28)
    ))
  }

  @Test
  fun `backstage passes should drop quality to 0 post sell by date`() {
    val item = BackstagePass(2, 10)

    val store = GildedRose.create(item)

    // 2 -> 1 days
    var newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 1, quality = 13),
    ))

    // 1 -> 0 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 0, quality = 16),
    ))

    // 0 -> -1 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = -1, quality = 0),
    ))

    // -1 -> -2 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = -2, quality = 0),
    ))
  }

  @Test
  fun `backstage passes quality can never exceed 50`() {
    val item = BackstagePass(2, 49)

    val store = GildedRose.create(item)

    // 2 -> 1 days
    var newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 1, quality = 50),
    ))

    // 1 -> 0 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = 0, quality = 50),
    ))

    // 0 -> -1 days
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      BackstagePass(sellIn = -1, quality = 0),
    ))
  }

  @Test
  fun `conjured items degrade twice as fast`() {
    val conjuredShield = ConjuredItem(name = "Shield", sellIn = 5, quality = 10)
    val conjuredSword = ConjuredItem(name = "Sword", sellIn = 2, quality = 3)

    val store = GildedRose.create(conjuredShield, conjuredSword)

    // 5 -> 4 days
    var newItems = store.updateQuality()
    assertContentEquals(expected = newItems, actual = listOf(
      ConjuredItem(name = "Shield", sellIn = 4, quality = 8),
      ConjuredItem(name = "Sword", sellIn = 1, quality = 1)
    ))

    // 4 -> 3 days
    newItems = store.updateQuality()
    assertContentEquals(expected = newItems, actual = listOf(
      ConjuredItem(name = "Shield", sellIn = 3, quality = 6),
      ConjuredItem(name = "Sword", sellIn = 0, quality = 0)
    ))
  }
}
