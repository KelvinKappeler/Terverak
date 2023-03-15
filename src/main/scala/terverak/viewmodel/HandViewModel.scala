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
final case class HandViewModel(position: Point, cardsViewModel: List[CardViewModel]) {
  
  def updateCardsPosition(hand: Hand): HandViewModel = {
    def rec(cards: List[Card], index: Int): List[CardViewModel] = {
      cards match {
        case Nil => List.empty
        case (card :: tail) => CardViewModel(
          Point(
            position.x + (HandViewModel.cardSpacing * index) + HandViewModel.offsetX,
            position.y + HandViewModel.offsetY
          )) :: rec(tail, index + 1)
      }
    }
    copy(cardsViewModel = rec(hand.cards, 0))
  }
  
}

object HandViewModel {

  val offsetX: Int = 6
  val offsetY: Int = 4
  val cardSpacing: Int = 8 + CardViewModel.CardSize.width

  val initialCurrentPlayerHand: HandViewModel = HandViewModel(Point(0, 3 * 72), List.empty)
  val initialWaitingPlayerHand: HandViewModel = HandViewModel(Point(0, 0), List.empty)

}
