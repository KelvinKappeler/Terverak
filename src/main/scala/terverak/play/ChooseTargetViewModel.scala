// =======================================
// Terverak -> ChooseTargetViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.cardeffect.CardEffectWithTarget
import terverak.card.cardeffect.CardEffects
import terverak.card.cardeffect.CardEffectsDamage

/**
  * The view model of the choose target view.
  * @param linkedEffect the card linked to the description
  * @param isShown true if the plyer is choosing a target
  */
final case class ChooseTargetViewModel(
  linkedEffect: CardEffectWithTarget,
  isChoosingTarget: Boolean = false
)

object ChooseTargetViewModel {
  val initial = ChooseTargetViewModel(CardEffectsDamage.DamageMinion(1))
}
