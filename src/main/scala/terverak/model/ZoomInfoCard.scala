// =======================================
// Terverak -> ZoomInfoCard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.model

/**
  * A zoomed card with some information.
  * @param isShown true if the view is shown
  * @param card the card
  */
final case class ZoomInfoCard(isShown: Boolean, card: Card) {

  /**
    * Shows the zoomed card.
    * @param newCard the new card to show
    * @return the new zoomed info card
    */
  def show(newCard: Card): ZoomInfoCard =
    copy(isShown = true, card = newCard)

  /**
    * Unshows the zoomed card.
    * @return the new zoomed info card
    */
  def unshow(): ZoomInfoCard =
    copy(isShown = false)

}
