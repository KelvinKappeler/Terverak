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
  
  val cards: Set[Card] =
    Set(
      bato,
      shinyBato
    )

  val bato: Cards.MinionCard = Cards.MinionCard("Bato", ("Small boat chilling in the water.","Draw 1 card.","Give 2 mana."), GameAssets.Cards.bato, 1, List(CardEffects.DamageHeroEffect(3)), Nil, 2, 3)
  val shinyBato: Cards.MinionCard = Cards.MinionCard("Shiny Bato", ("An expensive boat","Draw 2 cards","Give 4 mana."), GameAssets.Cards.shinyBato, 2, List(CardEffects.DamageHeroEffect(3)), Nil, 3, 5)
}
