// =======================================
// Terverak -> CardDescriptionViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card

/**
  * The view model of a card description.
  * @param linkedCard the card linked to the description
  * @param isShown true if the description is shown
  */
final case class CardDescriptionViewModel(
  linkedCard: Card,
  isShown: Boolean = false
)

object CardDescriptionViewModel {
  val initial = CardDescriptionViewModel(CardsData.cards.head)
}
