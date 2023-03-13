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
object HandViewModel {

  def update(hand: Hand) = {
    def updateRec(cards: List[Card]): Unit = {
      cards match {
        case Nil => ()
        case (card :: tail) =>
          CardViewModel.update(card)
          updateRec(tail)
      }
    }
    updateRec(hand.cards)
  }
}
