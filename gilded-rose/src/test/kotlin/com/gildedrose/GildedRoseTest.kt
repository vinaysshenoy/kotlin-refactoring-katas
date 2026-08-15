package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class GildedRoseTest {
  @Test
  fun foo() {
    val items = arrayOf(Item("foo", 0, 0))
    val app = GildedRose(items)
    app.updateQuality()
    assertEquals("foo", app.items[0].name)
  }

  @Test
  fun `it should handle empty items`() {
    val items = emptyArray<Item>()
    val app = GildedRose(items)
    val newItems = app.updateQuality()
    assertContentEquals(emptyArray<Item>(), newItems)
  }
}
