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
    val phone = Item("Phone", 10, 30)
    val newItems = GildedRose.create(phone).updateQuality()

    assertContentEquals(newItems, listOf(
      Item("Phone", 9, 29),
    ))
  }

  @Test
  fun `post sell by date item quality should degrade twice as fast`() {
    val phone = Item("Phone", 10, 30)
    val vegetables = Item("Vegetables", 2, 10)

    val store = GildedRose.create(phone, vegetables)

    // day 1
    var newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Phone", 9, 29),
      Item("Vegetables", 1, 9),
    ))

    // day 2
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Phone", 8, 28),
      Item("Vegetables", 0, 8),
    ))

    // day 3
    newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Phone", 7, 27),
      Item("Vegetables", -1, 6),
    ))
  }

  @Test
  fun `quality of item can never be negative`() {
    val phone = Item("Phone", 10, 30)
    val vegetables = Item("Vegetables", 2, 0)

    val store = GildedRose.create(phone, vegetables)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Phone", 9, 29),
      Item("Vegetables", 1, 0),
    ))
  }

  @Test
  fun `aged brie has to increase in quality with age`() {
    val agedBrie = Item("Aged Brie", 10, 30)

    val store = GildedRose.create(agedBrie)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Aged Brie", 9, 31),
    ))
  }

  @Test
  fun `sulfuras should never be negative quality nor sell by date changes`() {
    val sulfuras1 = Item("Sulfuras, Hand of Ragnaros", 10, 80)
    val sulfuras2 = Item("Sulfuras, Hand of Ragnaros", -1, 80)

    val store = GildedRose.create(sulfuras1, sulfuras2)

    val newItems = store.updateQuality()
    assertContentEquals(newItems, listOf(
      Item("Sulfuras, Hand of Ragnaros", 10, 80),
      Item("Sulfuras, Hand of Ragnaros", -1, 80),
    ))
  }
}
