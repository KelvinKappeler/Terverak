// =======================================
// Terverak -> CardEffectsDraw.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.card.CardSubtype
import terverak.play.Game
import terverak.utils.StringUtils.getWordWithGoodPlural

/**
  * The card effects that draw cards.
  */
object CardEffectsDraw {
  
  /**
    * A card effect that draw cards.
    * @param amount the amount of cards drawn
    */
  final case class DrawCard(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(currentPlayer = game.currentPlayer.drawCards(amount))
    }

    override def toString: String =
      "Draw " + amount + " " + getWordWithGoodPlural("card", amount)
  }

  /**
   * A card effect that draw cards for each subtype on the board.
   */
  final case class DrawCardPerSubtype(amount: Int, subtype: CardSubtype, target: BoardSelectionForCardEffect) extends CardEffect {
    require(amount >= 0)

    override def activateEffect(game: Game): Game = {
      DrawCard(CardEffectHelper.countMinionsWithSubtype(game, subtype, target) * amount).activateEffect(game)
    }

    override def toString: String =
      DrawCard(amount).toString + " for each " + subtype + " " + target.toString()
  }

}
