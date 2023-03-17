// =======================================
// Terverak -> HandViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.model.*

/**
  * The view model of a hand.
  */
final case class HandViewModel(position: Point, cardsViewModel: List[CardViewModel], isRevealed: Boolean) {
  
  def updateCardsPosition(hand: Hand): HandViewModel = {
    def rec(cards: List[HandCard], index: Int): List[CardViewModel] = {
      cards match {
        case Nil => List.empty
        case (card :: tail) => CardViewModel(
          Point(
            position.x + (HandViewModel.CardSpacing * index) + HandViewModel.OffsetX,
            position.y + HandViewModel.OffsetY
          ), isRevealed, false) :: rec(tail, index + 1)
      }
    }
    copy(cardsViewModel = rec(hand.cards, 0))
  }

  def updateUniqueCardPosition(hand: Hand, card: HandCard, newPos: Point): HandViewModel = {
    val index = hand.cards.indexOf(card)
    val cardViewModel = CardViewModel(newPos, isRevealed, true)
    copy(cardsViewModel = cardsViewModel.updated(index, cardViewModel))
  }

  /**
    * Gets the first card that was right clicked on.
    * @param mouse the mouse
    * @param hand the hand
    * @return the first card that was clicked on
    */
  def getCardUnderMouse(mouse: Mouse, hand: Hand): Option[HandCard] = {
    cardsViewModel.zip(hand.cards).find((cardViewModel, _) => cardViewModel.checkMouseRightClickedOnCard(mouse)) match {
      case Some(_, card) => Some(card)
      case None => None
    }
  }
  
}

object HandViewModel {

  val OffsetX: Int = 6
  val OffsetY: Int = 4
  val CardSpacing: Int = 8 + CardViewModel.CardSize.width

  val initialCurrentPlayerHand: HandViewModel = HandViewModel(Point(0, 3 * 72), List.empty, true)
  val initialWaitingPlayerHand: HandViewModel = HandViewModel(Point(0, 0), List.empty, false)

}
