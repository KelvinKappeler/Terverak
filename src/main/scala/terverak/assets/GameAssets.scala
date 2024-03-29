// =======================================
// Terverak -> GameAssets.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.assets

import indigo.*
import terverak.utils.*

/**
  * The assets of the game.
  */
object GameAssets {
  
  private val AssetsDirectory: String = "assets/"

  val assets: Set[AssetType] = Cards.assets ++ Fonts.assets ++ Backgrounds.assets ++ Heroes.assets ++ Buttons.assets

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
    val brownCreature: AssetName = AssetName("brownCreature")
    val bacteria: AssetName = AssetName("bacteria")
    val planetBurning: AssetName = AssetName("planetBurning")
    val smallAstronaut: AssetName = AssetName("smallAstronaut")
    val ship1: AssetName = AssetName("ship1")
    val demoniacCreature: AssetName = AssetName("demoniacCreature")

    //spells
    val spell1: AssetName = AssetName("spell1")
    val blackHole: AssetName = AssetName("blackHole")
    val gemBlue: AssetName = AssetName("gemBlue")
    val gemRed: AssetName = AssetName("gemRed")
    val gemOrange: AssetName = AssetName("gemOrange")

    val cardBack: AssetName = AssetName("cardBack")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(bato, AssetPath(AssetCardsDirectory + "bato.png")),
        AssetType.Image(cardBack, AssetPath(AssetCardsDirectory + "card_back.png")),
        AssetType.Image(shinyBato, AssetPath(AssetCardsDirectory + "bato_shiny.png")),
        AssetType.Image(planet1, AssetPath(AssetCardsDirectory + "planet1.png")),
        AssetType.Image(planet2, AssetPath(AssetCardsDirectory + "planet2.png")),
        AssetType.Image(planet3, AssetPath(AssetCardsDirectory + "planet3.png")),
        AssetType.Image(planet4, AssetPath(AssetCardsDirectory + "planet4.png")),
        AssetType.Image(alienYellow, AssetPath(AssetCardsDirectory + "alien_yellow.png")),
        AssetType.Image(alienGreen, AssetPath(AssetCardsDirectory + "alien_green.png")),
        AssetType.Image(alienBlue, AssetPath(AssetCardsDirectory + "alien_blue.png")),
        AssetType.Image(alienRed, AssetPath(AssetCardsDirectory + "alien_red.png")),
        AssetType.Image(spell1, AssetPath(AssetCardsDirectory + "spell1.png")),
        AssetType.Image(blackHole, AssetPath(AssetCardsDirectory + "black_hole.png")),
        AssetType.Image(meteor, AssetPath(AssetCardsDirectory + "spell2.png")),
        AssetType.Image(gemBlue, AssetPath(AssetCardsDirectory + "gem_blue.png")),
        AssetType.Image(gemRed, AssetPath(AssetCardsDirectory + "gem_red.png")),
        AssetType.Image(gemOrange, AssetPath(AssetCardsDirectory + "gem_orange.png")),
        AssetType.Image(brownCreature, AssetPath(AssetCardsDirectory + "brown_creature.png")),
        AssetType.Image(bacteria, AssetPath(AssetCardsDirectory + "bacteria.png")),
        AssetType.Image(smallAstronaut, AssetPath(AssetCardsDirectory + "small_astronaut.png")),
        AssetType.Image(planetBurning, AssetPath(AssetCardsDirectory + "burning_planet.png")),
        AssetType.Image(ship1, AssetPath(AssetCardsDirectory + "ship1.png")),
        AssetType.Image(demoniacCreature, AssetPath(AssetCardsDirectory + "demoniac_creature.png"))
      )
  }

  /**
    * Button assets.
    */
  object Buttons {
    private val AssetCardsDirectory: String = AssetsDirectory + "buttons/"

    val minionButton: AssetName = AssetName("minionButton")
    val spellButton: AssetName = AssetName("spellButton")

    val alienButton: AssetName = AssetName("alienButton")
    val planetButton: AssetName = AssetName("planetButton")
    val gemButton: AssetName = AssetName("gemButton")
    
    val manaCostButton: AssetName = AssetName("manaCostButton")
    val attackPointsButton: AssetName = AssetName("attackPointsButton")
    val healthPointsButton: AssetName = AssetName("healthPointsButton")

    val clearButton: AssetName = AssetName("clearButton")
    val plusButton: AssetName = AssetName("plusButton")
    val minusButton: AssetName = AssetName("minusButton")

    val rightArrow: AssetName = AssetName("rightArrow")
    val leftArrow: AssetName = AssetName("leftArrow")

    val playButton: AssetName = AssetName("playButton")
    val saveButton: AssetName = AssetName("loadButton")
    val loadButton: AssetName = AssetName("saveButton")

    val endTurnButton: AssetName = AssetName("endTurnButton")
    val backToMenuButton: AssetName = AssetName("backToMenuButton")
    val createDeckButton: AssetName = AssetName("createDeckButton")

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
        AssetType.Image(rightArrow, AssetPath(AssetCardsDirectory + "right_arrow.png")),
        AssetType.Image(leftArrow, AssetPath(AssetCardsDirectory + "left_arrow.png")),
        AssetType.Image(plusButton, AssetPath(AssetCardsDirectory + "plus_button.png")),
        AssetType.Image(minusButton, AssetPath(AssetCardsDirectory + "minus_button.png")),
        AssetType.Image(playButton, AssetPath(AssetCardsDirectory + "play_button.png")),
        AssetType.Image(loadButton, AssetPath(AssetCardsDirectory + "load_button.png")),
        AssetType.Image(saveButton, AssetPath(AssetCardsDirectory + "save_button.png")),
        AssetType.Image(gemButton, AssetPath(AssetCardsDirectory + "gem_button.png")),
        AssetType.Image(endTurnButton, AssetPath(AssetCardsDirectory + "end_turn_button.png")),
        AssetType.Image(backToMenuButton, AssetPath(AssetCardsDirectory + "menu_button.png")),
        AssetType.Image(createDeckButton, AssetPath(AssetCardsDirectory + "create_deck_button.png"))
      )
  }

  /**
    * Backgrounds assets.
    */
  object Backgrounds {

    private val AssetCardsDirectory: String = AssetsDirectory + "backgrounds/"

    val discardZone: AssetName = AssetName("discardZone")
    val title: AssetName = AssetName("title")
    val background: AssetName = AssetName("background")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(discardZone, AssetPath(AssetCardsDirectory + "discard_zone.png")),
        AssetType.Image(title, AssetPath(AssetCardsDirectory + "title.png")),
        AssetType.Image(background, AssetPath(AssetCardsDirectory + "background.png")),
      )
  }

  /**
    * Heroes assets.
    */
  object Heroes {

    val player1: AssetName = AssetName("player1")
    val player2: AssetName = AssetName("player2")

    val assets: Set[AssetType] =
      Set(
        AssetType.Image(player1, AssetPath(AssetsDirectory + "player1.png")),
        AssetType.Image(player2, AssetPath(AssetsDirectory + "player2.png"))
      )
  }

  /**
    * Fonts assets.
    */
  object Fonts {

    private val AssetFontsDirectory: String = AssetsDirectory + "fonts/"

    private val numbersFontName16: AssetName = AssetName("NumbersFont16")
    private val normalFont8Name: AssetName = AssetName("NumbersFont8")
    private val fontKey16: FontKey = FontKey("NumbersFont16")
    private val fontNormal8Key: FontKey = FontKey("NumbersFont8")
    private val size16: Int = 16
    private val size8: Int = 8

    def fontInfo16: FontInfo =
      FontInfo(fontKey16, 160, 16, FontChar("0", 0, 0, size16, size16))
        .addChar(FontChar("0", 0, 0, size16, size16))
        .addChar(FontChar("1", 16, 0, size16, size16))
        .addChar(FontChar("2", 32, 0, size16, size16))
        .addChar(FontChar("3", 48, 0, size16, size16))
        .addChar(FontChar("4", 64, 0, size16, size16))
        .addChar(FontChar("5", 80, 0, size16, size16))
        .addChar(FontChar("6", 96, 0, size16, size16))
        .addChar(FontChar("7", 112, 0, size16, size16))
        .addChar(FontChar("8", 128, 0, size16, size16))
        .addChar(FontChar("9", 144, 0, size16, size16))

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
        .addChar(FontChar(",", 24, 48, size8, size8))
        .addChar(FontChar("-", 32, 48, size8, size8))
        .addChar(FontChar("+", 40, 48, size8, size8))
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

    val defaultFont16: TerverakFont = TerverakFont(fontKey16, Material.ImageEffects(numbersFontName16), size16)
    val defaultFont8: TerverakFont = TerverakFont(fontNormal8Key, Material.ImageEffects(normalFont8Name), size8)
  }
}
