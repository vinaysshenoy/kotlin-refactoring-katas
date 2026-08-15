package com.gildedrose

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class GildedRoseTest {

  private fun setupAndRun(inventory: Array<Item>): Array<Item> {
    val store = GildedRose(inventory)
    return store.updateQuality()
  }

  @Test
  fun `it should handle empty items`() {
    val items = emptyArray<Item>()
    val newItems = setupAndRun(items)
    assertContentEquals(emptyArray<Item>(), newItems)
  }

  @Test
  fun `item sell in and quality should decrease each day`() {
    val items = arrayOf(
      Item("Phone", 10, 30)
    )

    val newItems = setupAndRun(items)

    assertContentEquals(newItems, arrayOf(
      Item("Phone", 9, 29),
    ))
  }
}
