// =======================================
// Terverak -> CardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import indigoextras.geometry.*
import indigoextras.ui.*
import terverak.model.*
import indigoextras.geometry.LineIntersectionResult.IntersectionVertex
import terverak.utils.*

/**
  * The view model of a card.
  */
final case class CardViewModel(
  position: Point,
  isRevealed: Boolean,
  hitArea: HitArea = HitArea(Polygon.Closed(CardViewModel.Points))
) {

  def updateHitArea(card: Card, mouse: Mouse): Outcome[CardViewModel] = {
    hitArea.update(mouse).map(ha =>
      copy(hitArea = ha
        .moveTo(position)
        .withClickActions(TerverakEvents.ClickOnCard(MouseButton.LeftMouseButton, card))))
  }

}

object CardViewModel {
  val CardSize: Size = Size(32, 64)
  val Points: Batch[Vertex] = Batch(
    Point(0, 0),
    Point(CardSize.width, 0),
    Point(CardSize.width, CardSize.height),
    Point(0, CardSize.height)).map(Vertex.fromPoint)
}
