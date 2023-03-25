// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import indigo.*
import terverak.model.IdObject.MinionWithId

import scala.util.Random

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
  final case class HealingHeroEffect (amount: Int = 0) extends CardEffect {
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
  final case class DamageHeroEffect (amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
    }

    override def toString: String = "Deal " + amount + " damage to the opponent hero"
  }

  /**
   * A card effect that add mana to the current player.
   * @param amount the amount of mana added
   */
  final case class AddManaEffect (amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(currentPlayer = game.currentPlayer.addMana(amount))
    }

        override def toString: String = "Add " + amount + " mana to your mana pool"
  }

  /**
    * A card effect that invoke a minion on the board.
    * @param minionCard the minion card
    */
  final case class InvokeMinion(minionCard: Card.MinionCard) extends CardEffect {

    override def activateEffect(game: Game): Game = {
      val newMinionBoard = game.currentPlayer.minionBoard.addMinion(
        Minion(minionCard, minionCard.life, minionCard.life, minionCard.damage, false)
      )

      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = newMinionBoard
        )
      )
    }

    override def toString: String = "Invoke a minion on the board"
  }

  /**
    * A card effect that draw cards.
    * @param amount the amount of cards drawn
    */
  final case class DrawCards(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(currentPlayer = game.currentPlayer.drawCards(amount))
    }

    override def toString: String = "Draw " + amount + " cards"
  }

  /**
    * A card effect that draw cards for each planet in control.	
    */
  final case class DrawCardsForNumberPlanetsInControl() extends CardEffect {
    override def activateEffect(game: Game): Game = {
      val numberOfPlanets = game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Planet)).size
      DrawCards(numberOfPlanets).activateEffect(game)
    }

    override def toString: String = "Draw a card for each planet you control"
  }

  /**
    * A card effect that give mana for each alien on the board.
    */
  final case class AddManaPerAlien() extends CardEffect {
    override def activateEffect(game: Game): Game = {
      val numberOfAliens = 
        game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
        + game.waitingPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
      AddManaEffect(numberOfAliens).activateEffect(game)
    }

    override def toString: String = "Add 1 mana for each alien on the board"
  }

  /**
   * A card effect that add attack to minion for each alien on the board.
   */
  final case class AddAttackPerAlien() extends CardEffect {
    override def activateEffect(game: Game): Game = {
      val numberOfAliens = 
        game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
        + game.waitingPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
      val minionToBuff = game.currentPlayer.minionBoard.minions(0)
      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = game.currentPlayer.minionBoard.copy(
            minions = game.currentPlayer.minionBoard.minions.updated(0, MinionWithId(minionToBuff.minion.copy(attackPoints = minionToBuff.minion.attackPoints + numberOfAliens), minionToBuff.id))
          ) 
        )
      )
    }

    override def toString: String = "When played: earn +1 attack for each alien on the board"
  }

  /**
   * A card effect that heal the hero for each alien on the board.
   */
  final case class HealHeroPerAlien() extends CardEffect {
    override def activateEffect(game: Game): Game = {
      val numberOfAliens = 
        game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
        + game.waitingPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(CardSubtype.Alien)).size
      HealingHeroEffect(numberOfAliens).activateEffect(game)
    }

    override def toString: String = "Heal your hero for each alien on the board"
  }

  final case class DestroyRandomMinions(amount: Int, opponentOnly: Boolean) extends CardEffect {
    require(amount >= 0, "Amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      val totalMinions = 
        if (opponentOnly) {
          game.waitingPlayer.minionBoard.minions.size
        } else {
          game.currentPlayer.minionBoard.minions.size + game.waitingPlayer.minionBoard.minions.size
        }

      val randomMinions = Random.shuffle((0 until totalMinions).toList).take(amount)

      if (opponentOnly) {
        val newWaitingPlayerMinionsBoard = game.waitingPlayer.minionBoard.copy(
          minions = game.waitingPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2)).map(_._1))
        game.copy(
          waitingPlayer = game.waitingPlayer.copy(
            minionBoard = newWaitingPlayerMinionsBoard
          )
        )
      } else {
        val newCurrentPlayerMinionsBoard = game.currentPlayer.minionBoard.copy(
          minions = game.currentPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2)).map(_._1))
        val newWaitingPlayerMinionsBoard = game.waitingPlayer.minionBoard.copy(
          minions = game.waitingPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2 + game.currentPlayer.minionBoard.minions.size)).map(_._1))
        game.copy(
          currentPlayer = game.currentPlayer.copy(
            minionBoard = newCurrentPlayerMinionsBoard
          ),
          waitingPlayer = game.waitingPlayer.copy(
            minionBoard = newWaitingPlayerMinionsBoard
          )
        )
      }
    }

    override def toString: String = 
      if (opponentOnly) {
        "Destroy " + amount + " random ennemy minions"
      } else {
        "Destroy " + amount + " random ally or ennemy minions"
      }
  }
}
