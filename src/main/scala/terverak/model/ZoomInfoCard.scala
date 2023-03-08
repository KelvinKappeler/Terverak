// =======================================
// Terverak -> ZoomInfoCard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.model

/**
  * A zoomed card.
  * @param isShown true if the view is shown
  * @param card the card
  */
final case class ZoomInfoCard(isShown: Boolean, card: Card)
