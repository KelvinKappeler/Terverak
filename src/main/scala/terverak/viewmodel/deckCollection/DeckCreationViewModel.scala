// =======================================
// Terverak -> DeckCreationViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel.deckCollection

final case class DeckCreationViewModel(
  
)

object DeckCreationViewModel {
  val DefaultOffsetX = 10
  val DefaultWidth = 100
  val DefaultHeight = CardsCatalogViewModel.DefaultHeight

  val initial: DeckCreationViewModel = DeckCreationViewModel()

}
