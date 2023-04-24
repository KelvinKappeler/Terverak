// =======================================
// Terverak -> HandViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import indigoextras.ui.HitArea
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
        case head :: tail => 
          val posX = position.x + (HandViewModel.CardSpacing * index) + HandViewModel.OffsetX
          val posY = position.y + HandViewModel.OffsetY
          CardViewModel(
          Point(
            posX,
            posY
          ),
          isRevealed = isRevealed).initHitArea(head.card) :: rec(tail, index + 1)
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
    val cardViewModel = CardViewModel(newPos, HitArea(Rectangle(newPos, CardViewModel.CardSize)), isRevealed = isRevealed, isDragged = true)
    copy(cardsViewModel = cardsViewModel.updated(index, cardViewModel))
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
  
    /**
    * Updates the hit area of the cards.
    * @param mouse the mouse
    * @return the updated hand view model
    */
  def updateHitArea(mouse: Mouse): Outcome[HandViewModel] = {
    cardsViewModel.zipWithIndex.foldLeft(Outcome(this))((viewModel, cardViewModelWithIndex) =>
      viewModel.flatMap(vm =>
        cardViewModelWithIndex._1.updateHitArea(mouse).map(newCardViewModel =>
          vm.copy(cardsViewModel = vm.cardsViewModel.updated(cardViewModelWithIndex._2, newCardViewModel)
        )
      )
    ))
  }
}

object HandViewModel {

  val OffsetX: Int = 6
  val OffsetY: Int = 4
  val CardSpacing: Int = 8 + CardViewModel.CardSize.width

  val HandSize: Size = Size((HandViewModel.CardSpacing * Hand.MaxHandSize) + HandViewModel.OffsetX, 72)

  val initialCurrentPlayerHand: HandViewModel = HandViewModel(Point(0, HandViewModel.HandSize.height + 2 * DiscardZoneViewModel.DiscardZoneSize.height), List.empty, true)
  val initialWaitingPlayerHand: HandViewModel = HandViewModel(Point(0, 0), List.empty, false)

}
