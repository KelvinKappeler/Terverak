// =======================================
// Terverak -> DeckCreationViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel.deckCollection

import indigo.*
import terverak.data.*
import terverak.viewmodel.ui.*

final case class DeckCreationViewModel(
  deckNumber: Int = 0
) {
  require(deckNumber >= 0 && deckNumber <= 2, "Deck number must be between 0 and 2")

  /**
    * Returns the next deck.
    * @return the next deck.
    */
  def nextDeck(): DeckCreationViewModel = {
    copy(deckNumber = (deckNumber + 1) % 3)
  }

  /**
    * Returns the previous deck.
    * @return the previous deck.
    */
  def previousDeck(): DeckCreationViewModel = {
    copy(deckNumber = (deckNumber + 2) % 3)
  }
}

object DeckCreationViewModel {
  val DefaultOffsetX = 10
  val DefaultOffsetY = 10
  val DefaultWidth = 100
  val DefaultHeight: Int = CardsCatalogViewModel.DefaultHeight

  val initial: DeckCreationViewModel = DeckCreationViewModel()

}
