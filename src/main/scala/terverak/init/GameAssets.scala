// =======================================
// Terverak -> GameAssets.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.init

import indigo.*

object GameAssets {
  
  val batoTexture: AssetName = AssetName("batoCard")
  val batoMaterial: Material.Bitmap = Material.Bitmap(batoTexture)

  def batoCard(): Graphic[Material.Bitmap] =
    Graphic(0, 0, 32, 64, 1, batoMaterial)

  def assets(baseUrl: String): Set[AssetType] =
    Set(
      AssetType.Image(batoTexture, AssetPath(baseUrl + "assets/ExampleCard.png")),
    )

}
