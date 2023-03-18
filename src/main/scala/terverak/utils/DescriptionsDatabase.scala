// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

object DescriptionsDatabase {
  
  /**
    * Map the card name to its description, returning a tuple of (description, effect when played, effect when discarded).
    */
  val descriptions: Map[String, (String, String, String)] = 
    Map(
      "blank" -> ("","",""),
      "Bato" -> ("Small boat chilling in the water.","Draw a card.","Give 2 mana.")
    )
}
