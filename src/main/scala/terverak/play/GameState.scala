// =======================================
// Terverak -> GameState.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.play

import terverak.card.cardeffect.*

/**
  * The state of the game.
  */
enum GameState {

  /**
    * Normal
    */
  case Playing

  /**
    * When the player chooses a target
    */
  case ChoosingTarget(handCard: IdObject.HandCard, effect: CardEffectWithTargetChoice, effects: List[CardEffect], invokingMinionIfMinionCard: Boolean, potentialTargets: List[IdObject])
}
