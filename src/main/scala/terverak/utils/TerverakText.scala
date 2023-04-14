// =======================================
// Terverak -> TerverakFont.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*

/**
  * Terverak Text support.
  */
object TerverakText {
  
  /**
    * Draws text with TerverakFont support.
    * @param text the text to draw
    * @param x the x position
    * @param y the y position
    * @param depth the depth
    * @param font the font
    * @param color the color
    * @return the batch
    */
  def drawText(text: String, x: Int, y: Int, depth: Int, font: TerverakFont, color: RGBA): Batch[Group] =
    Batch(
      Group(
        Text(
          text,
          x,
          y,
          depth,
          font.fontKey,
          font.fontMaterial.withTint(color)
        )
      ).withDepth(Depth(depth))
    )

}
