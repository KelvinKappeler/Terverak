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

  val assets: Set[AssetType] = Cards.assets ++ Fonts.assets ++ Backgrounds.assets ++ Heroes.assets ++ Audio.assets

  /**
    * Cards assets.
    */
  object Cards {

    val bato: AssetName = AssetName("batoCard")
    val shinyBato: AssetName = AssetName("shinyBatoCard")
    val cardBack: AssetName = AssetName("cardBack")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(bato, AssetPath("assets/bato.png")),
        AssetType.Image(cardBack, AssetPath("assets/card_back.png")),
        AssetType.Image(shinyBato, AssetPath("assets/shiny_bato.png"))
      )
  }

  /**
    * Backgrounds assets.
    */
  object Backgrounds {

    val discardZone: AssetName = AssetName("discardZone")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(discardZone, AssetPath("assets/discard_zone.png"))
      )
  }

  /**
    * Heroes assets.
    */
  object Heroes {

    val human = AssetName("human")
    val troll = AssetName("troll")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(human, AssetPath("assets/hero_human.png")),
        AssetType.Image(troll, AssetPath("assets/hero_troll.png"))
      )
  }

  /**
    * Audio assets.
    */
  object Audio {

    val batoNoise: AssetName = AssetName("batoNoise")
    
    val assets: Set[AssetType] =
      Set(
        AssetType.Audio(batoNoise, AssetPath("assets/bato_noise.mp3"))
      )
  }

  /**
    * Fonts assets.
    */
  object Fonts {

    private val numbersFontName16: AssetName = AssetName("NumbersFont16")
    private val normalFont8Name: AssetName = AssetName("NumbersFont8")
    val fontKey16: FontKey = FontKey("NumbersFont16")
    val fontNormal8Key: FontKey = FontKey("NumbersFont8")
    val fontMaterial16: Material.ImageEffects = Material.ImageEffects(numbersFontName16)
    val fontNormal8Material: Material.ImageEffects = Material.ImageEffects(normalFont8Name)

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

    private val size8: Int = 8
    def fontInfo8: FontInfo =
      FontInfo(fontNormal8Key, 64, 64, FontChar("?", 56, 56, size8, size8))
        .addChar(FontChar("0", 0, 0, size8, size8))
        .addChar(FontChar("1", 12, 0, 4, size8))
        .addChar(FontChar("2", 16, 0, size8, size8))
        .addChar(FontChar("3", 24, 0, size8, size8))
        .addChar(FontChar("4", 32, 0, size8, size8))
        .addChar(FontChar("5", 40, 0, size8, size8))
        .addChar(FontChar("6", 48, 0, size8, size8))
        .addChar(FontChar("7", 56, 0, size8, size8))
        .addChar(FontChar("8", 0, 8, size8, size8))
        .addChar(FontChar("9", 8, 8, size8, size8))
        .addChar(FontChar("A", 16, 8, size8, size8))
        .addChar(FontChar("B", 24, 8, size8, size8))
        .addChar(FontChar("C", 32, 8, size8, size8))
        .addChar(FontChar("D", 40, 8, size8, size8))
        .addChar(FontChar("E", 48, 8, size8, size8))
        .addChar(FontChar("F", 56, 8, size8, size8))
        .addChar(FontChar("G", 0, 16, size8, size8))
        .addChar(FontChar("H", 8, 16, size8, size8))
        .addChar(FontChar("I", 16, 16, size8, size8))
        .addChar(FontChar("J", 24, 16, size8, size8))
        .addChar(FontChar("K", 32, 16, size8, size8))
        .addChar(FontChar("L", 40, 16, size8, size8))
        .addChar(FontChar("M", 48, 16, size8, size8))
        .addChar(FontChar("N", 56, 16, size8, size8))
        .addChar(FontChar("O", 0, 24, size8, size8))
        .addChar(FontChar("P", 8, 24, size8, size8))
        .addChar(FontChar("Q", 16, 24, size8, size8))
        .addChar(FontChar("R", 24, 24, size8, size8))
        .addChar(FontChar("S", 32, 24, size8, size8))
        .addChar(FontChar("T", 40, 24, size8, size8))
        .addChar(FontChar("U", 48, 24, size8, size8))
        .addChar(FontChar("V", 56, 24, size8, size8))
        .addChar(FontChar("W", 0, 32, size8, size8))
        .addChar(FontChar("X", 8, 32, size8, size8))
        .addChar(FontChar("Y", 16, 32, size8, size8))
        .addChar(FontChar("Z", 24, 32, size8, size8))
        .addChar(FontChar(" ", 24, 56, size8, size8))
        .addChar(FontChar(":", 32, 56, size8, size8))
        .addChar(FontChar(".", 40, 56, size8, size8))
        .addChar(FontChar("!", 48, 56, size8, size8))
        .addChar(FontChar("?", 56, 56, size8, size8))

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(Fonts.numbersFontName16, AssetPath(assetsDirectory + "font_numbers16.png")),
        AssetType.Image(Fonts.normalFont8Name, AssetPath(assetsDirectory + "font_normal8.png")),
      )
  }
}
