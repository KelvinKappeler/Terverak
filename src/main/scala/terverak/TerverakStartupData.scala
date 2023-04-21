// =======================================
// Terverak -> TerverakStartupData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak

import terverak.deckCollection.User

/**
  * The boot data of the game.
  * @param user the user
  */
final case class TerverakStartupData(user: User)
