package terverak.view

import indigo.*
import terverak.model.*

/**
  * The view of a hand.
  */
object HandView {
  
  /**
    * Draws a hand.
    * 
    * @param hand the hand to draw
    * @return the batch of the hand
    */
    def drawHand(hand: Hand, position: Point): Batch[SceneNode] = {
      def drawHandRec(cards: List[Card], index: Int): Batch[SceneNode] = {
        cards match {
          case Nil =>
            Batch.empty
          case (card :: tail) =>
            CardView.drawCard(card, position.x + 40 * index, position.y) ++ drawHandRec(tail, index + 1)
        }
      }
      Batch(Shape.Box(Rectangle(position.x, position.y, 40 * (hand.MaxHandSize+1), 64), Fill.Color(RGBA.Blue)).withDepth(Depth(1))) ++ drawHandRec(hand.cards, 0)
    }
}
