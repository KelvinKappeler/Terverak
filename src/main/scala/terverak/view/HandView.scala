// =======================================
// Terverak -> HandView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo.*
import terverak.model.*

/**
  * The view of a hand.
  */
object HandView {
  
  /**
    * Draws a hand.
    * @param hand the hand to draw
    * @return the batch of the hand
    */
  def draw(hand: Hand, position: Point): Batch[SceneNode] = {
    def drawRec(cards: List[Card], index: Int): Batch[SceneNode] = {
      cards match {
        case Nil =>
          Batch.empty
        case (card :: tail) =>
          CardView.draw(card, position.x + 40 * index + 6, position.y + 4, 20) ++ drawRec(tail, index + 1)
      }
    }
    Batch(
      Shape.Box(
        Rectangle(position.x, position.y, 40 * (hand.MaxHandSize)+4, 72),
        Fill.Color(RGBA.Blue)).withDepth(Depth(40)))
    ++ drawRec(hand.cards, 0)
  }
}
