// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.*
import terverak.play.IdObject.*
import stainless.lang.*
import stainless.collection.*

/**
  * A game of Terverak.
  * @param currentPlayer the current player
  * @param waitingPlayer the waiting player
  */
final case class Game(currentPlayer: Player, waitingPlayer: Player) {
  
  /**
  * New turn in Terverak.
  * @return the new game
  */
  def newTurn(): Game = {
    val wakeUpMinions = currentPlayer.minionBoard.wakeUpMinions()
    val newCurrentPlayer = currentPlayer.copy(minionBoard = wakeUpMinions).removeMana(currentPlayer.mana)

    val damageToDeal = 
      ListOps.sum(
        currentPlayer.minionBoard.minions.filter(
          _.minion.card.attributes match {
            case MinionCardAttributesData.Toxicity() => true
            case _ => false
          }
        ).map(_.minion.attackPoints))
    
    val manaToRegen = 
      ListOps.sum(
        waitingPlayer.minionBoard.minions.map(manaMinion =>
          manaMinion.minion.card.attributes match {
              case MinionCardAttributesData.ManaRegen(amount) => 
                if (manaMinion.minion.healthPoints > damageToDeal) then amount else 0
              case _ => 0
          }
        )
      )

    copy(currentPlayer = waitingPlayer.startTurn().addMana(manaToRegen).takeDamage(damageToDeal), waitingPlayer = newCurrentPlayer.takeDamage(damageToDeal))
    .damageAllMinions(damageToDeal).refresh()
  }

  /**
    * Refresh the game.
    * It will for example remove minions with <= 0 health points from the board.
    * @return the new game
    */
  def refresh(): Game = {
    copy(currentPlayer.refresh(), waitingPlayer.refresh())
  }

  /**
   * Check if a card can be played.
   * @param handCard the card to check
   * @return true if the card can be played, false otherwise
   */
  def isCardPlayable(handCard: HandCard): Boolean = {
    handCard.card match
      case minion: Card.MinionCard =>
        (currentPlayer.mana >= minion.manaCost) && (currentPlayer.minionBoard.minions.length < MinionBoard.MaxMinionBoardSize)
      case spell: Card.SpellCard =>
        currentPlayer.mana >= spell.manaCost
  }

  /**
    * Damage a specific id object.
    * @param idObject the id object to damage
    * @param amount the amount of damage
    * @return the new game
    */
  def damageIdObject(idObject: IdObject, amount: BigInt): Game = {
    require(amount >= 0)

    copy(
      currentPlayer = currentPlayer.damageIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.damageIdObject(idObject, amount)
    )
  }

  /**
    * Damage all minions of the game.
    * @param amount the amount of damage
    * @return the new game
    */
  def damageAllMinions(amount: BigInt): Game = {
    require(amount >= 0)

    copy(
      currentPlayer = currentPlayer.damageAllMinions(amount),
      waitingPlayer = waitingPlayer.damageAllMinions(amount)
    )
  }

  /**
    * Heal a specific id object.
    * @param idObject the id object to heal
    * @param amount the amount of heal
    * @return the new game
    */
  def healIdObject(idObject: IdObject, amount: BigInt): Game = {
    require(amount >= 0)

    copy(
      currentPlayer = currentPlayer.healIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.healIdObject(idObject, amount)
    )
  }

  /**
    * Boost the attack of a specific id object.
    * @param idObject the id object to boost
    * @param amount the amount of boost
    * @return the new game
    */
  def boostAttackOfIdObject(idObject: IdObject, amount: BigInt): Game = {
    require(amount >= 0)

    copy(
      currentPlayer = currentPlayer.boostAttackOfIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.boostAttackOfIdObject(idObject, amount)
    )
  }

  /**
    * Boost the life of a specific id object.
    * @param idObject the id object to boost
    * @param amount the amount of boost
    * @return the new game
    */
  def boostLifeOfIdObject(idObject: IdObject, amount: BigInt): Game = {
    require(amount >= 0)

    copy(
      currentPlayer = currentPlayer.boostHealthOfIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.boostHealthOfIdObject(idObject, amount)
    )
  }

  /**
    * kill a specific minion
    * @param idObject
    * @return
    */
  def destroyMinion(idObject: IdObject): Game = {
    idObject match
      case minionId: IdObject.MinionWithId =>
        copy(
          currentPlayer = currentPlayer.destroyMinion(minionId),
          waitingPlayer = waitingPlayer.destroyMinion(minionId)
        )
      case _ => this
  }

  /**
   * Counts the number of minions with a specific subtype.
   * @param game the game
   * @param cardSubtype the subtype of the minion
   * @param target the target of the effect (current player, waiting player or both players)
   * @return the number of minions with the specific subtype
   */
  /*
  def countMinionsWithSubtype(cardSubtype: CardSubtype, target: BoardSelectionForCardEffect): BigInt = {
    target match {
      case BoardSelectionForCardEffect.CurrentPlayerMinionsBoard => currentPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
      case BoardSelectionForCardEffect.WaitingPlayerMinionsBoard => waitingPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
      case _ => currentPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
        + waitingPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
    }
  }*/

  /**
    * Activate a card effect
    * @param effect the effect to activate
    * @return the new game
    */
    /*
  def activateEffect(effect: CardEffect, selectedIdObject: Option[IdObject]): Game = {
    effect match {

      case InvokeMinion(minionCard) =>
        val canAttack = minionCard.attributes.contains(MinionCardAttributesData.Sprint())
        val newMinionBoard = currentPlayer.minionBoard.addMinion(
          Minion(minionCard, minionCard.life, minionCard.life, minionCard.damage, canAttack)
        )
        copy(
          currentPlayer = currentPlayer.copy(
            minionBoard = newMinionBoard
          )
        )
        
      case AddAttackPerSubtype(amount, subtype, target) =>
        val number = countMinionsWithSubtype(subtype, target) * amount
        val minionToBuff = currentPlayer.minionBoard.minions.head
        copy(
          currentPlayer = currentPlayer.copy(
            minionBoard = currentPlayer.minionBoard.copy(
              minions = currentPlayer.minionBoard.minions.updated(0, MinionWithId(minionToBuff.minion.copy(attackPoints = minionToBuff.minion.attackPoints + number), minionToBuff.id))
            )
          )
        )

      case DamageHero(amount, isEnemyHero) =>
        if (isEnemyHero) {
          copy(waitingPlayer = waitingPlayer.takeDamage(amount))
        } else {
          copy(currentPlayer = currentPlayer.takeDamage(amount))
        }

      case DamageAllMinions(amount) =>
        damageAllMinions(amount)

      case DamageEveryone(amount) =>
        val g1 = activateEffect(DamageHero(amount, isEnemyHero = true), None)
        val g2 = g1.activateEffect(DamageHero(amount, isEnemyHero = false), None)
        g2.activateEffect(DamageAllMinions(amount), None)

      case DamageEnnemyMinions(amount) =>
        copy(waitingPlayer = waitingPlayer.damageAllMinions(amount))

      case HealHero(amount) =>
        copy(currentPlayer = currentPlayer.heal(amount))

      case HealHeroPerSubtype(amount, subtype, target) =>
        activateEffect(HealHero(countMinionsWithSubtype(subtype, target) * amount), None)

      case DrawCard(amount) =>
        copy(currentPlayer = currentPlayer.drawCards(amount))
      
      case DrawCardPerSubtype(amount, subtype, target) =>
        activateEffect(DrawCard(countMinionsWithSubtype(subtype, target) * amount), None)

      case AddMana(amount) =>
        copy(currentPlayer = currentPlayer.addMana(amount))

      case AddManaPerSubtype(amount: Int, subtype: CardSubtype, target: BoardSelectionForCardEffect) =>
        activateEffect(AddMana(countMinionsWithSubtype(subtype, target) * amount), None)
        
      case cardEffectTarget: CardEffectWithTargetChoice =>
        selectedIdObject match {
          case None => this
          case Some(idObject) =>
            cardEffectTarget match {

              case DamageTarget(amount, _, _) =>
                damageIdObject(idObject, amount)

              case DestroyMinion(_, _) => 
                destroyMinion(idObject)
              
              case DestroyTargetAndGiveManaForHealth(_, _) =>
                val list = (currentPlayer.minionBoard.minions ++ waitingPlayer.minionBoard.minions).filter(_.id == idObject.id)
            
                if (list.isEmpty) {
                  this
                } else {
                  val minion = list.head
                  val newGame = destroyMinion(minion)
                  newGame.copy(currentPlayer = newGame.currentPlayer.addMana(minion.minion.healthPoints))
                }

              case HealTarget(amount, _, _) =>
                healIdObject(idObject, amount)

              case BoostMinionAttack(amount, _, _) => 
                boostAttackOfIdObject(idObject, amount)

              case BoostMinionLife(amount, _, _) =>
                boostLifeOfIdObject(idObject, amount)
              
              case _ => this
            }
        }
      case _ => this
    }
  }*/

}
