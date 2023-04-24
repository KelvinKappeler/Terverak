// =======================================
// Terverak -> TargetChoosingViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

import terverak.card.cardeffect.CardEffect
import terverak.card.cardeffect.CardEffectsHeal

/**
  * The view model of a target choosing.
  * @param isShown true if the view is shown
  */
final case class TargetChoosingViewModel(
  currentEffect: CardEffect = CardEffectsHeal.HealHero(0),
  isShown: Boolean = false
)

object TargetChoosingViewModel {
  val initial = TargetChoosingViewModel()
}
