// =======================================
// Terverak -> GameAssets.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.data

import indigo.*

/**
  * The assets of the game.
  */
object GameAssets {
  
  private val assetsDirectory: String = "assets/"

  val assets: Set[AssetType] = Cards.assets ++ Fonts.assets

  /**
    * Cards assets.
    */
  object Cards {

    val bato: AssetName = AssetName("batoCard")
    val cardBack: AssetName = AssetName("cardBack")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(bato, AssetPath("assets/bato.png")),
        AssetType.Image(cardBack, AssetPath("assets/card_back.png"))
      )
  }

  /**
    * Fonts assets.
    */
  object Fonts {
    private val numbersFontName16: AssetName = AssetName("NumbersFont16")
    private val numbersFontName8: AssetName = AssetName("NumbersFont8")
    val fontKey16: FontKey = FontKey("NumbersFont16")
    val fontKey8: FontKey = FontKey("NumbersFont8")
    val fontMaterial16: Material.ImageEffects = Material.ImageEffects(numbersFontName16)
    val fontMaterial8: Material.ImageEffects = Material.ImageEffects(numbersFontName8)

    def fontInfo16: FontInfo =
      FontInfo(fontKey16, 160, 16, FontChar("0", 0, 0, 16, 16))
        .addChar(FontChar("0", 0, 0, 16, 16))
        .addChar(FontChar("1", 16, 0, 16, 16))
        .addChar(FontChar("2", 32, 0, 16, 16))
        .addChar(FontChar("3", 48, 0, 16, 16))
        .addChar(FontChar("4", 64, 0, 16, 16))
        .addChar(FontChar("5", 80, 0, 16, 16))
        .addChar(FontChar("6", 96, 0, 16, 16))
        .addChar(FontChar("7", 112, 0, 16, 16))
        .addChar(FontChar("8", 128, 0, 16, 16))
        .addChar(FontChar("9", 144, 0, 16, 16))

    def fontInfo8: FontInfo =
      FontInfo(fontKey8, 80, 8, FontChar("0", 0, 0, 8, 8))
        .addChar(FontChar("0", 0, 0, 8, 8))
        .addChar(FontChar("1", 13, 0, 3, 8))
        .addChar(FontChar("2", 16, 0, 8, 8))
        .addChar(FontChar("3", 24, 0, 8, 8))
        .addChar(FontChar("4", 32, 0, 8, 8))
        .addChar(FontChar("5", 40, 0, 8, 8))
        .addChar(FontChar("6", 48, 0, 8, 8))
        .addChar(FontChar("7", 56, 0, 8, 8))
        .addChar(FontChar("8", 64, 0, 8, 8))
        .addChar(FontChar("9", 72, 0, 8, 8))

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(Fonts.numbersFontName16, AssetPath(assetsDirectory + "font_numbers16.png")),
        AssetType.Image(Fonts.numbersFontName8, AssetPath(assetsDirectory + "font_numbers8.png")),
      )
  }
}
