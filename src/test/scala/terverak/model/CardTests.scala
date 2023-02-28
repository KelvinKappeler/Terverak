// =======================================
// Terverak -> CardTests.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.model

class CardTests extends munit.FunSuite {

  test("MinionCardEmptyNameShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.MinionCard("", 1, Nil, Nil, 0, 1)
    }
  }

  test("SpellCardEmptyNameShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.SpellCard("", 1, Nil, Nil)
    }
  }

  test("MinionCardManaCostNegativeShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.MinionCard("Card1", -1, Nil, Nil, 0, 1)
    }
  }

  test("SpellCardManaCostNegativeShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.SpellCard("Card1", -1, Nil, Nil)
    }
  }

  test("MinionCardDamageNegativeShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.MinionCard("Card1", 1, Nil, Nil, -1, 1)
    }
  }

  test("MinionCardLifeNegativeOrZeroShouldThrowException") {
    intercept[IllegalArgumentException] {
      Cards.MinionCard("Card1", 1, Nil, Nil, 0, 0)
    }
    intercept[IllegalArgumentException] {
      Cards.MinionCard("Card1", 1, Nil, Nil, 0, -1)
    }
  }

  test("MinionCardNormal") {
    val card = Cards.MinionCard("Card1", 23, Nil, Nil, 7, 3)
    assertEquals(card.name, "Card1")
    assertEquals(card.manaCost, 23)
    assertEquals(card.effectsWhenPlayed, Nil)
    assertEquals(card.effectsWhenDiscard, Nil)
    assertEquals(card.damage, 7)
    assertEquals(card.life, 3)
  }

  test("SpellCardNormal") {
    val card = Cards.SpellCard("Card1", 4, Nil, Nil)
    assertEquals(card.name, "Card1")
    assertEquals(card.manaCost, 4)
    assertEquals(card.effectsWhenPlayed, Nil)
    assertEquals(card.effectsWhenDiscard, Nil)
  }
}
