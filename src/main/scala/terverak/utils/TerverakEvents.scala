// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*
import terverak.model.*

object TerverakEvents {
  
  final case class HandChanged(isCurrentPlayer: Boolean, hand: Hand) extends GlobalEvent
  final case class LeftClickOnCard(handCard: HandCard) extends GlobalEvent
  final case class RightClickOnCard(handCard: HandCard) extends GlobalEvent

}
