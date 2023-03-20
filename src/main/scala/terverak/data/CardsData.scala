// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.data

import terverak.model.*

/**
  * The cards of the game.
  */
object CardsData {
  
  val cards: Set[Card] = MinionCards.minionCards ++ SpellCards.spellCards

  object MinionCards {
    val minionCards = Set(
      bato,
      shinyBato
    )

    val bato: Card.MinionCard = Card.MinionCard(
      "Bato", ("Small boat chilling in the water.","None","Give 1 mana."), GameAssets.Cards.bato, 1, Nil, List(CardEffects.AddManaEffect(1)), 2, 3
      )

    val shinyBato: Card.MinionCard = Card.MinionCard(
      "Shiny Bato", ("An expensive boat","Deal 1 damage to the opponent hero","Give 2 mana."), GameAssets.Cards.shinyBato, 2, List(CardEffects.DamageHeroEffect(1)), List(CardEffects.AddManaEffect(2)), 3, 5
      )
  }

  object SpellCards {
    val spellCards = Set(
      // List of spells
    )
  }
}
