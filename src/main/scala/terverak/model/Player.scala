// =======================================
// Terverak -> Player.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import terverak.model.Cards.*

final case class Player(name: String, healthPoints: Int, mana: Int,  deck: Deck, hand: Hand, minions: List[Minion]) {

  /**
    * Takes damage for the player.
    *
    * @param amount The amount of damage to take.
    * @return The new player.
    */
  def takeDamage(amount: Int): Player = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")
    require(healthPoints - amount >= 0, "Player must not die")

    Player(name, healthPoints - amount, mana, deck, hand, minions)
  } ensuring(_.healthPoints == healthPoints - amount, "Player health points must be decreased by the damage amount")

  /**
    * Heals the player.
    *
    * @param amount The amount of health to heal.
    * @return The new player.
    */
  def heal(amount: Int): Player = {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    Player(name, healthPoints + amount, mana, deck, hand, minions)
  } ensuring(_.healthPoints == healthPoints + amount, "Player health points must be increased by the healing amount")

  def addMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")

    Player(name, healthPoints, mana + amount, deck, hand, minions)
  } ensuring(_.mana == mana + amount, "Player mana must be increased by the mana amount")

  def removeMana(amount: Int): Player = {
    require(amount >= 0, "Mana amount must be equal or greater than 0")
    require(mana - amount >= 0, "Player must not have negative mana")

    Player(name, healthPoints, mana - amount, deck, hand, minions)
  } ensuring(_.mana == mana - amount, "Player mana must be decreased by the mana amount")

  /**
    * Draws a card from the deck.
    *
    * @param amount The amount of cards to draw.
    * @return The new player.
    */
  def drawCards(amount: Int): Player = {
    require(amount >= 0, "Draw amount must be equal or greater than 0")
    //TODO: Need to define what to do when the deck is empty
    if amount == 0 || deck.cards.length == 0 then
      this
    else       
      val (newDeck, drawnCard) = deck.removeTopCard()
      Player(name, healthPoints, mana, newDeck, hand.addCard(drawnCard), minions).drawCards(amount - 1)
  }

  /**
    * Discards a card from the hand.
    *
    * @param card The card to discard.
    * @return The new player and the effects of the discarded card.
    */
  def discardCard(card: Card): (Player, List[CardEffect]) = {
    require(hand.cards.contains(card), "Card must be in the hand")

    val newHand = hand.removeCard(card)
    
    (Player(name, healthPoints, mana, deck, newHand, minions), card.effectsWhenDiscard)
  }

  /**
    * Plays a card from the hand.
    *
    * @param card The card to play.
    * @return The new player and the effects of the played card.
    */
  def playCard(card: Card): (Player, List[CardEffect]) = {
    require(hand.cards.contains(card), "Card must be in the hand")
    require(mana >= card.manaCost, "Player must have enough mana to play the card")

    val newHand = hand.removeCard(card)
    val newMana = mana - card.manaCost
    
    card match
      case minionCard@MinionCard(name, _, effectsWhenPlayed, _, damage, life) => 
        (Player(name, healthPoints, newMana, deck, newHand, minionCard.toMinion::minions), effectsWhenPlayed)
      case SpellCard(_, _, effectsWhenPlayed, _) => 
        (Player(name, healthPoints, newMana, deck, newHand, minions), effectsWhenPlayed)
  }
  
  /**
    * Remove all minions that have 0 or less health points.
    * Need to call it after each turn.
    * 
    * @return The new player.
    */
  def removeDeadMinions(): Player = {
    Player(name, healthPoints, mana, deck, hand, minions.filter(_.healthPoints > 0))
  }

  def startTurn(): Player = {
    drawCards(1).removeDeadMinions()
  }
  
}
