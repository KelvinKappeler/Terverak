// =======================================
// Terverak -> CardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card

import indigo.*
import indigoextras.geometry.*
import indigoextras.ui.*
import terverak.TerverakEvents
import terverak.play.*
import terverak.utils.*

/**
  * The view model of a card.
  */
final case class CardViewModel(
  position: Point,
  hitArea: HitArea = HitArea(Rectangle(0, 0, 0, 0)),
  isRevealed: Boolean = true,
  isDragged: Boolean = false,
  isSmallVersion: Boolean = false,
) {

  private val bounds =
    if (isSmallVersion) Rectangle(position.x, position.y, (CardViewModel.CardSize.width * CardViewModel.SmallVersionScale.x).toInt, (CardViewModel.CardSize.height * CardViewModel.SmallVersionScale.y).toInt)
    else Rectangle(position.x, position.y, CardViewModel.CardSize.width, CardViewModel.CardSize.height)

  /**
    * Check if the mouse is over the card.
    * @param mouse the mouse
    * @return true if the mouse is over the card
    */
  def checkMouseOverCard(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

  /**
    * Initialize the hit area of the card.
    * @param card the card
    * @return the card view model
    */
  def initHitArea(card: Card): CardViewModel = {
    copy(hitArea = HitArea(bounds)
    .withHoverOverActions(TerverakEvents.OnMouseHoverCard(card))
    .withHoverOutActions(TerverakEvents.OnMouseOutHoverCard()))
  }

  /**
   * Update the hit area of the card.
   * @param mouse the mouse
   * @return the outcome of the card view model
   */
  def updateHitArea(mouse: Mouse): Outcome[CardViewModel] = {
    hitArea.update(mouse).map(ha => this.copy(hitArea = ha))
  }
}

object CardViewModel {
  val SmallVersionScale = Vector2(0.4, 0.4)
  val CardSize: Size = Size(32, 64)
  val SmallVersionCardSize = Size((CardSize.width * SmallVersionScale.x).toInt, (CardSize.height * SmallVersionScale.y).toInt)
}
