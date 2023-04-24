// =======================================
// Terverak -> CardEffectsMana.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.card.CardSubtype
import terverak.play.Game
import terverak.play.IdObject.MinionWithId

/**
  * The data of the cards effects for mana.
  */
object CardEffectsMana {

  /**
   * A card effect that add mana to the current player.
   * @param amount the amount of mana added
   */
  final case class AddMana(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    override def activateEffect(game: Game, target: Option[MinionWithId]): Game = {
      game.copy(currentPlayer = game.currentPlayer.addMana(amount))
    }

    override def toString: String = "Add " + amount + " mana to your mana pool"
  }

    /**
    * A card effect that give mana for each alien on the board.
    */
  final case class AddManaPerSubtype(amount: Int, subtype: CardSubtype, target: CardEffectTarget) extends CardEffect {
    require(amount >= 0)

    override def activateEffect(game: Game, targetOption: Option[MinionWithId]): Game = {
      AddMana(CardEffectHelper.countMinionsWithSubtype(game, subtype, target) * amount).activateEffect(game, targetOption)
    }

    override def toString: String =
      AddMana(amount).toString + " for each " + subtype + " " + target.toString
  }
}
