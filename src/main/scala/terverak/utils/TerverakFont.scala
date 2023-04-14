// =======================================
// Terverak -> TerverakFont.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*

/**
  * Font definition.
  * @param fontKey the font key
  * @param fontMaterial the font material
  */
final case class TerverakFont(
  /**
    * The font key.
    */
  fontKey: FontKey,

  /**
    * The font material.
    */
  fontMaterial: Material.ImageEffects,

  /**
    * The width of a letter.
    */
  fontWidth: Int,
) {
  require(fontWidth > 0, "Font width must be greater than 0")
}
