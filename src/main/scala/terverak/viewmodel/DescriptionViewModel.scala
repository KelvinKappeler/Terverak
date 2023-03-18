// =======================================
// Terverak -> DescriptionViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import terverak.data.GameAssets
import terverak.model.*
import terverak.utils.DescriptionsDatabase


/**
  * The view model of the description.
  * @param card the card to display the description of (if blank, no description is displayed)
  */
final case class DescriptionViewModel(card: Card) {
  val descriptions = DescriptionsDatabase.descriptions(card.name)
}

object DescriptionViewModel {
  val DescriptionOffset = 30
  val BlankCard = Cards.MinionCard("blank", GameAssets.Cards.bato, 0, Nil, Nil, 0, 1)
  val initialDescription: DescriptionViewModel = DescriptionViewModel(BlankCard)
}
