// =======================================
// Terverak -> Plural.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

/**
  * Object containing methods to handle plurals.
  */
object Plural {
  
  /**
    * Returns the word with the good plural.
    * @param word the word
    * @param number the number
    * @return the word with the good plural
    */
  def getWordWithGoodPlural(word: String, number: Int): String = {
    if (number <= 1) word
    else word + "s"
  }

}
