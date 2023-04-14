// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak

import indigo.GlobalEvent
import terverak.card.Card

/**
  * The global events of the game.
  */
object TerverakEvents {

    /**
    * Event when the mouse is over a card.
    * @param card the card
    */
  case class OnMouseHoverCard(card: Card) extends GlobalEvent

  /**
    * Event when the mouse is out of a card.
    */
  case class OnMouseOutHoverCard() extends GlobalEvent
}
