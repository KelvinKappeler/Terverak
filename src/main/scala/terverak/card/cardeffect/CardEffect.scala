// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.play.Game

/**
  * A card effect
  */
trait CardEffect {
  /**
    * Activates the effect of the card
    */
  def activateEffect(game: Game): Game

  override def toString: String = "Unknown effect"
}
