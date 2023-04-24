// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.play.Game
import terverak.play.IdObject
import terverak.play.IdObject.MinionWithId

/**
  * A card effect
  */
trait CardEffect {
  /**
    * Activates the effect of the card
    */
  def activateEffect(game: Game, target: Option[MinionWithId]): Game

  //def targetType: CardEffectTarget = CardEffectTarget.None

  override def toString: String = "Unknown effect"
}

trait CardEffectWithTarget extends CardEffect {
  def targetType: CardEffectTarget = CardEffectTarget.Minion
}
