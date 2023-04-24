// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.card.cardeffect.*
import terverak.deckCollection.*
import terverak.play.*

/**
  * The events of the game.
  */
object PlayEvents {

  /**
    * Triggers when the hand of a player changes.
    * @param isCurrentPlayer true if the hand belongs to the current player
    * @param hand the hand
    */
  final case class HandChanged(isCurrentPlayer: Boolean, hand: Hand) extends GlobalEvent

  /**
    * Triggers when the minion board of a player changes.
    * @param isCurrentPlayer true if the minion board belongs to the current player
    * @param minionBoard the minion board
    */
  final case class MinionBoardChanged(isCurrentPlayer: Boolean, minionBoard: MinionBoard) extends GlobalEvent

  /**
    * Triggers when the players position swap
    */
  final case class SwapPlayers() extends GlobalEvent
  
  /**
   * Triggers when the drag of a card stops.
   * @param idObject the object to drag
   * @param position the position of the object
   */
  final case class StopDrag(idObject: IdObject, position: Point) extends GlobalEvent

  /**
   * Triggers when the drag of a card starts.
   * @param idObject the object to drag
   * @param position the position of the object
   */
  final case class Drag(idObject: IdObject, position: Point) extends GlobalEvent

  /**
   * You can use this event to show the description of a card.
   *
   * @param idObject the object id
   */
  final case class ShowDescription(idObject: IdObject) extends GlobalEvent

  /**
   * You can use this event to clear the description of a card.
   */
  final case class ClearDescription() extends GlobalEvent

  /**
   * You can use this event to play a card.
   *
   * @param handCard the card
   */
  final case class PlayCard(handCard: IdObject.HandCard) extends GlobalEvent

  /**
    * You can use this event to activate the effects of a card.
    * @param handCard the card
    * @param effects the effects
    */
  final case class ActivateEffects(handCard: IdObject.HandCard, effects: List[CardEffect], invokingMinionIfMinionCard: Boolean) extends GlobalEvent

  /**
    * You can use this event to activate the effects of a card.
    * @param idObject the object
    */
  final case class ActivateTargetEffect(idObject: IdObject, handCard: IdObject.HandCard, effect: CardEffectWithTargetChoice, effects: List[CardEffect], invokingMinionIfMinionCard: Boolean) extends GlobalEvent

  /**
    * You can use this event to choose a target for a card.
    */
  final case class ChooseTarget(handCard: IdObject.HandCard, effect: CardEffectWithTargetChoice, effects: List[CardEffect], invokingMinionIfMinionCard: Boolean) extends GlobalEvent

  /**
    * When the player clicks on an object.
    * @param idObject the object
    */
  final case class OnClickOnIdObject(idObject: IdObject) extends GlobalEvent

  /**
   * You can use this event to discard a card.
   *
   * @param handCard the card
   */
  final case class DiscardCard(handCard: IdObject.HandCard) extends GlobalEvent

  /**
   * You can use this event to attack a minion.
   *
   * @param attackingMinion the attacking minion
   * @param attackedMinion  the attacked minion
   */
  final case class AttackMinion(attackingMinion: IdObject.MinionWithId, attackedMinion: IdObject.MinionWithId) extends GlobalEvent

  /**
   * You can use this event to attack the opponent.
   *
   * @param attackingMinion the attacking minion
   */
  final case class AttackOpponent(attackingMinion: IdObject.MinionWithId) extends GlobalEvent

  /**
    * Event when the game starts.
    * @param deckCurrentPlayer the deck of the current player
    * @param deckWaitingPlayer the deck of the waiting player
    */
  final case class OnStartGame(deckCurrentPlayer: Deck, deckWaitingPlayer: Deck) extends GlobalEvent
}
