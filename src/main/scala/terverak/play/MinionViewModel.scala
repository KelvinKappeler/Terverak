// =======================================
// Terverak -> MinionViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import indigoextras.ui.*
import terverak.TerverakEvents
import terverak.card.*
import terverak.play.Minion

/**
  * The view model of a minion.
  */
final case class MinionViewModel(
  position: Point, 
  hitArea: HitArea = HitArea(Rectangle(0, 0, 0, 0)),
  isDragged: Boolean = false, 
  isDescriptionShown: Boolean = false
) {

  private val bounds = Rectangle(position.x, position.y, MinionViewModel.MinionSize.width, MinionViewModel.MinionSize.height)

  /**
    * Check if the mouse is over the minion.
    * @param mouse the mouse
    * @return true if the mouse is over the minion
    */
  def checkMouseOverMinion(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

  /**
    * Initialize the hit area of the card.
    * @param card the card
    * @return the card view model
    */
  def initHitArea(card: Card): MinionViewModel = {
    copy(hitArea = HitArea(bounds)
    .withHoverOverActions(TerverakEvents.OnMouseHoverCard(card))
    .withHoverOutActions(TerverakEvents.OnMouseOutHoverCard()))
  }

  /**
   * Update the hit area of the card.
   * @param mouse the mouse
   * @return the outcome of the card view model
   */
  def updateHitArea(mouse: Mouse): Outcome[MinionViewModel] = {
    hitArea.update(mouse).map(ha => this.copy(hitArea = ha))
  }

}

object MinionViewModel {
  val DescriptionOffset = 30
  val MinionSize: Size = Size(32, 64)
}
