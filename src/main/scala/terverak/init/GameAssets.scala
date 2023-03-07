// =======================================
// Terverak -> GameAssets.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.init

import indigo.*

/**
  * The assets of the game.
  */
object GameAssets {
  
  val assets: Set[AssetType] =
    Cards.assets ++ Fonts.assets

  /**
    * Cards assets.
    */
  object Cards {

    val bato: AssetName = AssetName("batoCard")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(bato, AssetPath("assets/ExampleCard.png")),
      )
  }

  /**
    * Fonts assets.
    */
  object Fonts {
    private val numbersFontName: AssetName = AssetName("NumbersFont")
    val fontKey: FontKey = FontKey("NumbersFont")
    val fontMaterial: Material.ImageEffects = Material.ImageEffects(numbersFontName)

    def fontInfo: FontInfo =
      FontInfo(fontKey, 160, 16, FontChar("0", 0, 0, 16, 16))
        .addChar(FontChar("0", 0, 0, 16, 16))
        .addChar(FontChar("1", 16, 0, 16, 16))

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(Fonts.numbersFontName, AssetPath("assets/font_numbers.png")),
      )
  }
}
