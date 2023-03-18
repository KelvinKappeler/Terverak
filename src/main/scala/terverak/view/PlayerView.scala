// =======================================
// Terverak -> PlayerView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo.*
import terverak.data.GameAssets
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a player.
  */
object PlayerView {
  
  def draw(player: Player, playerViewModel: PlayerViewModel): Batch[SceneNode] = {
    val x = playerViewModel.position.x
    val y = playerViewModel.position.y

    Batch(
      Graphic(x, y, PlayerViewModel.HeroSize.width, PlayerViewModel.HeroSize.height, 100, Material.Bitmap(playerViewModel.heroPicture)),
      Shape.Box(Rectangle(x + 1, y + PlayerViewModel.HeroSize.height - 9, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(90)),
      Shape.Box(Rectangle(x + PlayerViewModel.HeroSize.width - 9, y + PlayerViewModel.HeroSize.height - 9, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(90)),
      Text(player.healthPoints.toString(), x + 1, y + PlayerViewModel.HeroSize.height - 9, 80, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Red)),
      Text(player.mana.toString(), x + PlayerViewModel.HeroSize.width - 9, y + PlayerViewModel.HeroSize.height - 9, 80, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Blue)),
      )

  }
}
