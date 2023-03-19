// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.data

import terverak.model.HandCards.MinionHandCard
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

  val bato: Cards.MinionCard = Cards.MinionCard(
    "Bato", ("Small boat chilling in the water.","Draw 1 card.","Give 2 mana."), GameAssets.Cards.bato, 1, List(CardEffects.InvokeMinion(MinionHandCard(MinionCardsData.batoMinion, 0))), Nil, 2, 3
    )



  val shinyBato: Cards.MinionCard = Cards.MinionCard(
    "Shiny Bato", ("An expensive boat","Draw 2 cards","Give 4 mana."), GameAssets.Cards.shinyBato, 2, List(CardEffects.InvokeMinion(MinionHandCard(MinionCardsData.shinyBatoMinion, 0))), Nil, 3, 5
    )




}

object MinionCardsData {

  val batoMinion: Cards.MinionCard = Cards.MinionCard(
    "Minion Bato", ("", "", ""), GameAssets.Cards.bato, 1, Nil, Nil, 2, 3
    )

  val shinyBatoMinion: Cards.MinionCard = Cards.MinionCard(
    "Minion Shiny Bato", ("", "", ""), GameAssets.Cards.shinyBato, 2, Nil, Nil, 3, 5
    )
}
