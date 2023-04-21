// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak

import indigo.GlobalEvent
import terverak.card.Card
import terverak.deckCollection.User

/**
  * The global events of the game.
  */
object TerverakEvents {

  /**
    * Event when the mouse is over a card.
    * @param card the card
    */
  final case class OnMouseHoverCard(card: Card) extends GlobalEvent

  /**
    * Event when the mouse is out of a card.
    */
  final case class OnMouseOutHoverCard() extends GlobalEvent

    /**
    * Event when the user clicks on the start game button.
    */
  final case class OnClickOnStartGame() extends GlobalEvent

  /**
    * Event when the user changes scene and we want the user
    */
  final case class OnChangeSceneForUser(user: User) extends GlobalEvent
}
