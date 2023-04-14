// =======================================
// Terverak -> StringUtils.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.utils

/**
  * Object containing methods to add plurals and split text in multiple lines.
  */
object StringUtils {
  
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

  /** Returns a string with the text split in multiple lines if it is too long.
    * @param text the text to split
    * @param width the width of the text box
    * @param fontWidth the width of the font
    * @return the string split in multiple lines and the number of lines
    */
  def getMultilinesText(text: String, width: Int, fontWidth: Int): (String, Int) = {
    def rec(words: List[String]): List[String] = {
      val lists = words.foldLeft((List.empty[List[String]], List.empty[String])) {
        (acc, word) =>
          if acc._2.isEmpty then (acc._1, List(word))
          else if word == "\n" then (acc._1 ++ List(acc._2), List.empty)	
          else if (acc._2.foldLeft(0)((count, string) => count + string.length + 1) + word.length()) * fontWidth > width then (acc._1 ++ List(acc._2), List(word))
          else (acc._1, acc._2 ++ List(word))
      }

      val finalList = 
        if lists._2.isEmpty then lists._1
        else lists._1 ++ List(lists._2)

      finalList.map(_.mkString(" "))
    }
    
    val lines = rec(text.split(" ").toList)
    (lines.mkString("\n"), lines.length)
  }

}
