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
  
  private val AssetsDirectory: String = "assets/"

  val assets: Set[AssetType] = Cards.assets ++ Fonts.assets ++ Backgrounds.assets ++ Heroes.assets ++ Audio.assets ++ Buttons.assets

  /**
    * Cards assets.
    */
  object Cards {

    private val AssetCardsDirectory: String = AssetsDirectory + "cards/"

    //minions
    val bato: AssetName = AssetName("batoCard")
    val shinyBato: AssetName = AssetName("shinyBatoCard")
    val planet1: AssetName = AssetName("planet1")
    val planet2: AssetName = AssetName("planet2")
    val planet3: AssetName = AssetName("planet3")
    val planet4: AssetName = AssetName("planet4")
    val alienYellow: AssetName = AssetName("alienYellow")
    val alienGreen: AssetName = AssetName("alienGreen")
    val alienBlue: AssetName = AssetName("alienBlue")
    val alienRed: AssetName = AssetName("alienRed")
    val meteor: AssetName = AssetName("meteor")

    //spells
    val spell1: AssetName = AssetName("spell1")
    val blackHole: AssetName = AssetName("blackHole")

    val cardBack: AssetName = AssetName("cardBack")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(bato, AssetPath(AssetCardsDirectory + "bato.png")),
        AssetType.Image(cardBack, AssetPath(AssetCardsDirectory + "card_back.png")),
        AssetType.Image(shinyBato, AssetPath(AssetCardsDirectory + "shiny_bato.png")),
        AssetType.Image(planet1, AssetPath(AssetCardsDirectory + "planet1.png")),
        AssetType.Image(planet2, AssetPath(AssetCardsDirectory + "planet2.png")),
        AssetType.Image(planet3, AssetPath(AssetCardsDirectory + "planet3.png")),
        AssetType.Image(planet4, AssetPath(AssetCardsDirectory + "planet4.png")),
        AssetType.Image(alienYellow, AssetPath(AssetCardsDirectory + "yellow_alien.png")),
        AssetType.Image(alienGreen, AssetPath(AssetCardsDirectory + "green_alien.png")),
        AssetType.Image(alienBlue, AssetPath(AssetCardsDirectory + "blue_alien.png")),
        AssetType.Image(alienRed, AssetPath(AssetCardsDirectory + "red_alien.png")),
        AssetType.Image(spell1, AssetPath(AssetCardsDirectory + "spell1.png")),
        AssetType.Image(blackHole, AssetPath(AssetCardsDirectory + "black_hole.png")),
        AssetType.Image(meteor, AssetPath(AssetCardsDirectory + "spell2.png"))
      )
  }

  /**
    * Buttons assets.
    */
  object Buttons {
    private val AssetCardsDirectory: String = AssetsDirectory + "buttons/"

    val minionButton: AssetName = AssetName("minionButton")
    val spellButton: AssetName = AssetName("spellButton")

    val alienButton: AssetName = AssetName("alienButton")
    val planetButton: AssetName = AssetName("planetButton")
    
    val manaCostButton: AssetName = AssetName("manaCostButton")
    val attackPointsButton: AssetName = AssetName("attackPointsButton")
    val healthPointsButton: AssetName = AssetName("healthPointsButton")

    val clearButton: AssetName = AssetName("clearButton")
    val plusButton: AssetName = AssetName("plusButton")
    val minusButton: AssetName = AssetName("minusButton")

    val nextPageButton: AssetName = AssetName("nextPageButton")
    val previousPageButton: AssetName = AssetName("previousPageButton")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(alienButton, AssetPath(AssetCardsDirectory + "alien_button.png")),
        AssetType.Image(planetButton, AssetPath(AssetCardsDirectory + "planet_button.png")),
        AssetType.Image(clearButton, AssetPath(AssetCardsDirectory + "clear_button.png")),
        AssetType.Image(minionButton, AssetPath(AssetCardsDirectory + "minion_button.png")),
        AssetType.Image(spellButton, AssetPath(AssetCardsDirectory + "spell_button.png")),
        AssetType.Image(manaCostButton, AssetPath(AssetCardsDirectory + "manacost_button.png")),
        AssetType.Image(attackPointsButton, AssetPath(AssetCardsDirectory + "attackpoints_button.png")),
        AssetType.Image(healthPointsButton, AssetPath(AssetCardsDirectory + "healthpoints_button.png")),
        AssetType.Image(nextPageButton, AssetPath(AssetCardsDirectory + "next_page_button.png")),
        AssetType.Image(previousPageButton, AssetPath(AssetCardsDirectory + "previous_page_button.png")),
        AssetType.Image(plusButton, AssetPath(AssetCardsDirectory + "plus_button.png")),
        AssetType.Image(minusButton, AssetPath(AssetCardsDirectory + "minus_button.png"))
      )
  }

  /**
    * Backgrounds assets.
    */
  object Backgrounds {

    val discardZone: AssetName = AssetName("discardZone")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(discardZone, AssetPath(AssetsDirectory + "discard_zone.png"))
      )
  }

  /**
    * Heroes assets.
    */
  object Heroes {

    val human: AssetName = AssetName("human")
    val troll: AssetName = AssetName("troll")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(human, AssetPath(AssetsDirectory + "hero_human.png")),
        AssetType.Image(troll, AssetPath(AssetsDirectory + "hero_troll.png"))
      )
  }

  /**
    * Audio assets.
    */
  object Audio {

    private val AssetAudioDirectory: String = AssetsDirectory + "audio/"

    val batoNoise: AssetName = AssetName("batoNoise")
    
    val assets: Set[AssetType] =
      Set(
        AssetType.Audio(batoNoise, AssetPath(AssetAudioDirectory + "bato_noise.mp3"))
      )
  }

  /**
    * Fonts assets.
    */
  object Fonts {

    private val AssetFontsDirectory: String = AssetsDirectory + "fonts/"

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
        .addChar(FontChar("[", 48, 48, size8, size8))
        .addChar(FontChar("]", 56, 48, size8, size8))
        .addChar(FontChar(" ", 8, 56, size8, size8))
        .addChar(FontChar("'", 20, 56, 4, size8))
        .addChar(FontChar("/", 24, 56, size8, size8))
        .addChar(FontChar(":", 32, 56, size8, size8))
        .addChar(FontChar(".", 40, 56, size8, size8))
        .addChar(FontChar("!", 48, 56, size8, size8))
        .addChar(FontChar("?", 56, 56, size8, size8))

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(Fonts.numbersFontName16, AssetPath(AssetFontsDirectory + "font_numbers16.png")),
        AssetType.Image(Fonts.normalFont8Name, AssetPath(AssetFontsDirectory + "font_normal8.png")),
      )
  }
}
