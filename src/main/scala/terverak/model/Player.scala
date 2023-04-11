// =======================================
// Terverak -> Player.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import indigo.*
import terverak.model.IdObject.*
import terverak.model.*

import scala.annotation.tailrec

/**
  * A player.
  */
final case class Player(
  name: String,
  heroPicture: AssetName,
  maxHealthPoints: Int,
  healthPoints: Int,
  mana: Int,
  deck: DeckZone,
  hand: Hand,
  minionBoard: MinionBoard,
  discardZone: DiscardZone
) {

  /**
    * The player takes an amount of damage.
    * @param amount the amount of damage to take.
    * @return the new player.
    */
  def takeDamage(amount: Int): Player = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    copy(healthPoints = healthPoints - amount)
  } ensuring(_.healthPoints == healthPoints - amount, "Player health points must be decreased by the damage amount")

  /**
    * Heals the player.
    * @param amount the amount of health to heal.
    * @return the new player.
    */
  def heal(amount: Int): Player = {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    copy(healthPoints = Math.min(healthPoints + amount, maxHealthPoints))
  } ensuring(player => player.healthPoints == Math.min(healthPoints + amount, player.maxHealthPoints), "Player health points must be increased by the healing amount")

  /**
    * Adds mana to the player.
    * @param amount the amount of mana to add.
    * @return the new player.
    */
  def addMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    copy(mana = mana + amount)
  } ensuring(_.mana == mana + amount, "Player mana must be increased by the mana amount")

  /**
    * Removes mana from the player.
    * @param amount the amount of mana to remove.
    * @return the new player.
    */
  def removeMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")
    require(mana - amount >= 0, "Player must not have negative mana")

    copy(mana = mana - amount)
  } ensuring(_.mana == mana - amount, "Player mana must be decreased by the mana amount")

  /**
    * Draws a card from the deck.
 *
    * @param amount the amount of cards to draw.
    * @return the new player.
   */
  @tailrec
  def drawCards(amount: Int): Player = {
    require(amount >= 0, "Draw amount must be equal or greater than 0")
    if amount == 0 || hand.cards.length == hand.MaxHandSize then
      this
    else if deck.cards.isEmpty then
      copy(discardZone = DiscardZone(List.empty), deck = DeckZone(discardZone.cards).shuffle())
        .drawCards(amount)
    else
      val (newDeck, drawnCard) = deck.removeTopCard()
      val newHand = hand.addCard(drawnCard)
      copy(deck = newDeck, hand = newHand).drawCards(amount - 1)
  }

  /**
    * Move a card from the hand to the discard zone.
    * @param handCard the card to move.
    * @return the new player.
    */
  def moveHandCardToDiscardZone(handCard: HandCard): Player = {
    require(hand.cards.contains(handCard), "Card must be in the hand")

    copy(hand = hand.removeCard(handCard), discardZone = discardZone.addCard(handCard.card))
  }

  /**
    * Begins the player's turn.
    * @return the new player.
    */
  def startTurn(): Player = {
    drawCards(2)
  }

  /**
    * Refreshes the player's board.
    * @return the new player.
    */
  def refresh(): Player = {
    copy(minionBoard = minionBoard.refresh())
  }
}
