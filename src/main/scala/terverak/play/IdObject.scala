// =======================================
// Terverak -> IdObject.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.Card

/**
  * An object with an id.
  */
trait IdObject {
  def id: Int

  require(id >= 0, "Id must be positive")
}

object IdObject {
  
  val BaseIdIncrement = 4
  
  /**
    * A minion on the board.
    */
  final case class MinionWithId(
    minion: Minion,
    id: Int,
  ) extends IdObject {
    require(id >= 0, "Id must be positive")
  }

  /**
    * A card in a hand.
    * @param card the card
    * @param id the id of the card in the hand
    */
  final case class HandCard(
    card: Card,
    id: Int
  ) extends IdObject {
    require(id >= 0, "Id must be positive")
  }
}
