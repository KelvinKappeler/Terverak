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
  val cards: Set[Card] = AlienCardsData.cards ++ PlanetCardsData.cards ++ GemCardsData.cards

  /** The spell cards of the game.
    */
  object SpellCards {

    val blackHoleSpell: Card.SpellCard = Card.SpellCard(
      "Machiavellian Black Hole",
      "You don't want to meet him",
      GameAssets.Cards.blackHole,
      4,
      List(CardEffects.DestroyRandomMinions(2, BoardSelectionForCardEffect.WaitingPlayerMinionsBoard)),
      List(CardEffects.DestroyRandomMinions(1, BoardSelectionForCardEffect.BothPlayersMinionsBoard)),
      Nil
    )

    val spellCards: Set[Card.SpellCard] = Set(
      blackHoleSpell,
    )
  }
}
