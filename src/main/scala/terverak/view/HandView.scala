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
  /**
    * Draws a hand.
    * @param hand the hand to draw
    * @param handViewModel the hand view model
    * @return the batch of the hand
    */
  def draw(hand: Hand, handViewModel: HandViewModel, depth: Int): Batch[SceneNode] = {
    Batch(
      Shape.Box(
        Rectangle(handViewModel.position.x, handViewModel.position.y, HandViewModel.HandSize.width, HandViewModel.HandSize.height),
        Fill.Color(RGBA.Blue)).withDepth(Depth(depth)))
    ++ hand.cards.zip(handViewModel.cardsViewModel).foldLeft(Batch.empty)((batch, cardAndViewModel) =>
      batch ++ CardView.draw(cardAndViewModel._1.card, cardAndViewModel._2, depth - 1))
  }
}
