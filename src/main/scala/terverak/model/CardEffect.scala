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

  override def toString: String = "Unknown effect"
}

/**
  * Card effects types
  */
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
      game.copy(currentPlayer = game.currentPlayer.heal(amount))
    }

    override def toString: String = "Heal your hero for " + amount + " health points"
  }

  /**
  * A card effect that damages the opponent hero.
  * @param amount the amount of damage healed
  */
  final case class DamageHeroEffect (
    amount: Int = 0
  ) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
    }

    override def toString: String = "Deal " + amount + "damage to the opponent hero"
  }

  /**
   * A card effect that add mana to the current player.
   * @param amount the amount of mana added
   */
  final case class AddManaEffect (
    amount: Int = 0
  ) extends CardEffect {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(currentPlayer = game.currentPlayer.addMana(amount))
    }

        override def toString: String = "Add " + amount + "mana to your mana pool"
  }

  /**
    * A card effect that invoke a minion on the board.
    * @param minionCard the minion card
    */
  final case class InvokeMinion(
    minionCard: Card.MinionCard
  ) extends CardEffect {

    override def activateEffect(game: Game): Game = {
      val newMinionBoard = game.currentPlayer.minionBoard.addMinion(
        Minion(minionCard, minionCard.life, minionCard.life, minionCard.damage)
      )

      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = newMinionBoard
        )
      )
    }

    override def toString: String = "Invoke a minion on the board"
  }
}
