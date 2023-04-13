// =======================================
// Terverak -> CardsCatalog.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.deckCollection

import indigo.*
import terverak.assets.*
import terverak.card.Card
import terverak.card.CardsData

/**
  * The catalog of all the cards inside the DeckCollectionScene.
  */
final case class CardsCatalog(cards: List[Card])

object CardsCatalog {

  val initial: CardsCatalog = CardsCatalog(CardsData.cards.toList.sortBy(_.manaCost))

}
