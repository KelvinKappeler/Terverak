// =======================================
// Terverak -> TargetChoosingViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

/**
  * The view model of a target choosing.
  * @param isShown true if the view is shown
  */
final case class TargetChoosingViewModel(
  isShown: Boolean = false
)

object TargetChoosingViewModel {
  val initial = TargetChoosingViewModel()
}
