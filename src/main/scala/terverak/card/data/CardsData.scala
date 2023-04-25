// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.data

import terverak.assets.GameAssets
import terverak.card.*
import terverak.card.cardeffect.*

/**
  * The data of the cards.
  */
object CardsData {

  /**
    * The cards of the game.
    */
  val cards: Set[Card] = AlienCardsData.cards ++ PlanetCardsData.cards ++ GemCardsData.cards ++ OtherCardsData.cards
}
