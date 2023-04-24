// =======================================
// Terverak -> CardEffectsHeal.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.card.CardSubtype
import terverak.play.Game
import terverak.utils.StringUtils.getWordWithGoodPlural

/**
  * The card effects that heal.
  */
object CardEffectsHeal {
  
  /**
  * A card effect that heals the hero.
  * @param amount the amount of damage healed
  */
  final case class HealHero(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(currentPlayer = game.currentPlayer.heal(amount))
    }

    override def toString: String =
      "Heal your hero for " + amount + " health " + getWordWithGoodPlural("point", amount)
  }

  /**
   * A card effect that heal the hero for each alien on the board.
   */
  final case class HealHeroPerSubtype(amount: Int, subtype: CardSubtype, target: BoardSelectionForCardEffect) extends CardEffect {
    require(amount >= 0)

    override def activateEffect(game: Game): Game = {
      HealHero(CardEffectHelper.countMinionsWithSubtype(game, subtype, target) * amount).activateEffect(game)
    }

    override def toString: String =
      HealHero(amount).toString + " for each " + subtype + " " + target.toString()
  }
}
