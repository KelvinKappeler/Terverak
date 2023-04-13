// =======================================
// Terverak -> HandViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.card.CardViewModel
import terverak.play.DiscardZoneView
import terverak.play.Hand
import terverak.play.IdObject.*

/**
  * The view model of a hand.
  */
final case class HandViewModel(
  position: Point, 
  cardsViewModel: List[CardViewModel], 
  isRevealed: Boolean
  ) {
  
  /**
    * Updates the position of all cards.
    * @param hand the hand
    * @return the updated hand view model
    */
  def updateCardsPosition(hand: Hand): HandViewModel = {
    def rec(cards: List[HandCard], index: Int): List[CardViewModel] = {
      cards match {
        case Nil => List.empty
        case _ :: tail => CardViewModel(
          Point(
            position.x + (HandViewModel.CardSpacing * index) + HandViewModel.OffsetX,
            position.y + HandViewModel.OffsetY
          ), isRevealed) :: rec(tail, index + 1)
      }
    }
    copy(cardsViewModel = rec(hand.cards, 0))
  }

  /**
    * Moves a unique card to a new position.
    * @param hand the hand
    * @param card the card
    * @param newPos the new position
    * @return the updated hand view model
    */
  def moveUniqueCard(hand: Hand, card: HandCard, newPos: Point): HandViewModel = {
    val index = hand.cards.indexOf(card)
    val cardViewModel = CardViewModel(newPos, isRevealed, isDragged = true)
    copy(cardsViewModel = cardsViewModel.updated(index, cardViewModel))
  }

  /**
    * Shows the description of a card.
    * @param hand the hand
    * @param card the card
    * @return the updated hand view model
    */
  def showDescription(hand: Hand, card: HandCard): HandViewModel = {
    val index = hand.cards.indexOf(card)
    val cardViewModel = cardsViewModel(index).copy(isDescriptionShown = true)
    copy(cardsViewModel = cardsViewModel.updated(index, cardViewModel))
  }

  /**
    * Clears the description of cards.
    * @param hand the hand
    * @return the updated hand view model
    */
  def clearDescription(hand: Hand): HandViewModel = {
    copy(cardsViewModel = cardsViewModel.map(_.copy(isDescriptionShown = false)))
  }

  /**
    * Gets the first card under the mouse.
    * @param mouse the mouse
    * @param hand the hand
    * @return the first card that was under the mouse.
    */
  def getCardUnderMouse(mouse: Mouse, hand: Hand): Option[HandCard] = {
    cardsViewModel.zip(hand.cards).find((cardViewModel, _) => cardViewModel.checkMouseOverCard(mouse)) match {
      case Some(_, card) => Some(card)
      case None => None
    }
  }
  
}

object HandViewModel {

  val OffsetX: Int = 6
  val OffsetY: Int = 4
  val CardSpacing: Int = 8 + CardViewModel.CardSize.width

  val HandSize: Size = Size((HandViewModel.CardSpacing * Hand(List.empty).MaxHandSize) + HandViewModel.OffsetX, 72)

  val initialCurrentPlayerHand: HandViewModel = HandViewModel(Point(0, HandViewModel.HandSize.height + 2 * DiscardZoneViewModel.DiscardZoneSize.height), List.empty, true)
  val initialWaitingPlayerHand: HandViewModel = HandViewModel(Point(0, 0), List.empty, false)

}