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
    )

  val bato: Cards.MinionCard = Cards.MinionCard("Bato", GameAssets.Cards.bato, 1, List(CardEffects.DamageHeroEffect(3)), Nil, 2, 3)

}