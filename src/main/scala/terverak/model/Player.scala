// =======================================
// Terverak -> Player.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import stainless.collection.*

/**
  * A player.
  */
final case class Player(
  name: String,
  maxHealthPoints: Int,
  healthPoints: Int,
  mana: Int,
  deck: Deck,
  hand: Hand,
  minionBoard: MinionBoard
) {

  /**
    * Takes damage for the player.
    * @param amount the amount of damage to take.
    * @return the new player.
    */
  def takeDamage(amount: Int): Player = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    copy(healthPoints = stainless.math.max(healthPoints - amount, 0))
  } ensuring(_.healthPoints == stainless.math.max(healthPoints - amount, 0), "Player health points must be decreased by the damage amount")

  /**
    * Heals the player.
    * @param amount the amount of health to heal.
    * @return the new player.
    */
  def heal(amount: Int): Player = {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    copy(healthPoints = stainless.math.min(healthPoints + amount, maxHealthPoints))
  } ensuring(player => player.healthPoints == stainless.math.min(healthPoints + amount, player.maxHealthPoints), "Player health points must be increased by the healing amount")

  def addMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    copy(mana = mana + amount)
  } ensuring(_.mana == mana + amount, "Player mana must be increased by the mana amount")

  def removeMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")
    require(mana - amount >= 0, "Player must not have negative mana")

    copy(mana = mana - amount)
  } ensuring(_.mana == mana - amount, "Player mana must be decreased by the mana amount")

  /**
    * Draws a card from the deck.
    * @param amount the amount of cards to draw.
    * @return the new player.
    */
  def drawCards(amount: Int): Player = {
    require(amount >= 0, "Draw amount must be equal or greater than 0")
    //TODO: Need to define what to do when the deck is empty
    if (amount == 0 || deck.cards.length == 0) then
      this
    else
      val (newDeck, drawnCard) = deck.removeTopCard()
      copy(deck = newDeck, hand = hand.addCard(drawnCard))
  }

  /**
    * Discards a card from the hand.
    * @param card the card to discard.
    * @return the new player
    */
  def discardCard(card: Card): Player = {
    require(hand.cards.contains(card), "Card must be in the hand")

    val newHand = hand.removeCard(card)

    copy(hand = newHand)
  }

  /**
    * Plays a card from the hand.
    *
    * @param card The card to play.
    * @return The new player and the effects of the played card.
    */
  def playCard(card: Card): Player = {
    require(hand.cards.contains(card), "Card must be in the hand")
    require(mana >= card.manaCost, "Player must have enough mana to play the card")

    val newHand = hand.removeCard(card)
    val newMana = mana - card.manaCost

    copy(hand = newHand, mana = newMana)
  }

  def startTurn(): Player = {
    drawCards(1)
  }

  def refresh(): Player = {
    copy(minionBoard = minionBoard.refresh())
  }
}
