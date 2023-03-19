// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import indigo.*

/**
  * A card effect
  */
trait CardEffect {
  /**
    * Activates the effect of the card
    */
  def activateEffect(game: Game): Game
}

object CardEffects {

  /**
  * A card effect that heals the hero.
  * @param amount the amount of damage healed
  */
  final case class HealingHeroEffect (
    amount: Int = 0
  ) extends CardEffect {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game
    }
  }

  /**
  * A card effect that damages the hero.
  * @param amount the amount of damage healed
  */
  final case class DamageHeroEffect (
    amount: Int = 0
  ) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game
    }
  }

  final case class InvokeMinion(
    handMinionCard: HandCards.MinionHandCard
  ) extends CardEffect {

    override def activateEffect(game: Game): Game = {
      val minionCard = handMinionCard.card
      val newMinionBoard = game.currentPlayer.minionBoard.addMinion(
        Minion(minionCard, minionCard.life, minionCard.life, minionCard.damage)
      )

      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = newMinionBoard,
        )
      )
    }

  }

}
