// =======================================
// Terverak -> PlayerView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.play

import indigo.*
import terverak.assets.GameAssets
import terverak.play.Player
import terverak.utils.*

/**
  * The view of a player.
  */
object PlayerView {
  
  /**
    * Draws a player.
    * 
    * @param player the player to draw
    * @param playerViewModel the view model of the player
    * @return the batch of the player
    */
  def draw(player: Player, playerViewModel: PlayerViewModel): Batch[SceneNode] = {
    val x = playerViewModel.position.x
    val y = playerViewModel.position.y

    Batch(
      Graphic(x, y, PlayerViewModel.HeroSize.width, PlayerViewModel.HeroSize.height, 100, Material.Bitmap(player.heroPicture)),
      Shape.Box(Rectangle(x + 1, y + PlayerViewModel.HeroSize.height - 9, 16, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(90)),
      Shape.Box(Rectangle(x + PlayerViewModel.HeroSize.width - 9, y + PlayerViewModel.HeroSize.height - 9, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(90)),
    ) ++ TerverakText.drawText(player.healthPoints.toString, x + 1, y + PlayerViewModel.HeroSize.height - 9, 80, GameAssets.Fonts.defaultFont8, RGBA.Red)
    ++ TerverakText.drawText(player.mana.toString, x + PlayerViewModel.HeroSize.width - 9, y + PlayerViewModel.HeroSize.height - 9, 80, GameAssets.Fonts.defaultFont8, RGBA.Blue)

  }
}
