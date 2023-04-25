// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.play.*

/**
  * A card effect
  */
trait CardEffect {
  /**
    * Activates the effect of the card
    */
  def activateEffect(game: Game): Game = game

  override def toString: String = "Unknown effect"
}

/**
  * A card effect that has a target
  */
trait CardEffectWithTargetChoice extends CardEffect {
  
  /**
    * Activates the effect of the card
    * @param game the game
    * @param selectedEntity the id object that was selected by the player
    * @return the new game
    */
  def activateEffect(game: Game, selectedIdObject: IdObject): Game

  /**
    * The target type of the card effect
    * @return the target
    */
  def target: TargetTypeForCardEffect

  /**
    * The filter for the minions, if the target can be a minion
    * @return the filter
    */
  def filterForMinions: FilterForMinions

  override def toString: String = "Choose " + target.toString() + " " + filterForMinions.toString + " : "
}
