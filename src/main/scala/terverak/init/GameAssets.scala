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
  
  val bato: AssetName = AssetName("batoCard")

  //def batoCard(): Graphic[Material.Bitmap] =
  //  Graphic(0, 0, 32, 64, 1, Material.Bitmap(bato))

  val assets: Set[AssetType] =
    Set(
      AssetType.Image(bato, AssetPath("assets/ExampleCard.png")),
    )

}
