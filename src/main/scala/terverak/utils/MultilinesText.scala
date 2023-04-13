// =======================================
// Terverak -> MultilinesText.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*

object MultilinesText {

  /*
  def getMultilinesText(text: String, width: Int, fontWidth: Int): String = {
    val words = text.split(" ")
    val lines = words.foldLeft(List.empty[String]) { (acc, word) =>
      val currentLine = if (acc.isEmpty) word else s"${acc.last} $word"
      if ((currentLine.length + word.length + 1) * fontWidth > width) acc :+ word
      if (acc.length > 0) acc.init :+ currentLine
      else
        if (acc.isEmpty) List(currentLine)
        else acc.init :+ word
    }
    (lines :+ "").mkString("\n")
  }*/

  /** Returns a string with the text split in multiple lines.
    * @param text the text to split
    * @param width the width of the text
    * @param fontWidth the width of the font
    * @return the text split in multiple lines
    */
  def getMultilinesText(text: String, width: Int, fontWidth: Int): (String, Int) = {
    def rec(words: List[String]): List[String] = {
      val lists = words.foldLeft((List.empty[List[String]], List.empty[String])) {
        (acc, word) =>
          if acc._2.isEmpty then (acc._1, List(word))
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
