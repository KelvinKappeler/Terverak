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
      "Bato", ("Small boat chilling in the water.","Draw 1 card.","Give 2 mana."), GameAssets.Cards.bato, 0, Nil, Nil, 2, 3
      )

    val shinyBato: Card.MinionCard = Card.MinionCard(
      "Shiny Bato", ("An expensive boat","Draw 2 cards","Give 4 mana."), GameAssets.Cards.shinyBato, 0, Nil, Nil, 3, 5
      )
  }

  object SpellCards {
    val spellCards = Set(
      // List of spells
    )
  }
}
