// =======================================
// Terverak -> CardEffectsHeal.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.card.CardSubtype
import terverak.play.Game

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
      if (amount <= 1) "Heal your hero for " + amount + " health point"
      else "Heal your hero for " + amount + " health points"
  }

  /**
   * A card effect that heal the hero for each alien on the board.
   */
  final case class HealHeroPerSubtype(amount: Int, subtype: CardSubtype, target: CardEffectTarget) extends CardEffect {
    require(amount >= 0)

    override def activateEffect(game: Game): Game = {
      HealHero(CardEffectHelper.countMinionsWithSubtype(game, subtype, target) * amount).activateEffect(game)
    }

    override def toString: String =
      HealHero(amount).toString() + " for each " + subtype + target.toString()
  }
}
