// =======================================
// Terverak -> HandView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a hand.
  */
object HandView {
  
  private val Height: Int = 72

  /**
    * Draws a hand.
    * @param hand the hand to draw
    * @param handViewModel the hand view model
    * @return the batch of the hand
    */
  def draw(hand: Hand, handViewModel: HandViewModel, depth: Int): Batch[SceneNode] = {
    Batch(
      Shape.Box(
        Rectangle(handViewModel.position.x, handViewModel.position.y, (HandViewModel.CardSpacing * hand.MaxHandSize) + HandViewModel.OffsetX, Height),
        Fill.Color(RGBA.Blue)).withDepth(Depth(depth)))
    ++ hand.cards.zip(handViewModel.cardsViewModel).foldLeft(Batch.empty)((batch, card) => batch ++ CardView.draw(card._1, card._2, depth - 1))
  }
}
