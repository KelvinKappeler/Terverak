// =======================================
// Terverak -> CardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
package terverak.view

import indigo.*
import terverak.data.GameAssets
import terverak.model.Card
import terverak.viewmodel.*

object CardDescriptionView {

  /**
    * Draws a card description.
    * @param cardDescription the card description to draw
    * @return the batch of the card description
    */
  def draw(descriptionViewModel: DescriptionViewModel): Batch[SceneNode] = {
    val base_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
    val base_y = HandViewModel.HandSize.height
    val textOffset = 12
    val desc = descriptionViewModel.descriptions
    if descriptionViewModel.card.name == "blank" then
      Batch.empty
    else
      Batch(
        Text(desc._1, base_x, base_y, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
        Text("Effect when played:", base_x, base_y + DescriptionViewModel.DescriptionOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
        Text(desc._2, base_x, base_y + DescriptionViewModel.DescriptionOffset + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
        Text("Effect when discard:", base_x, base_y + 2*DescriptionViewModel.DescriptionOffset + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
        Text(desc._3, base_x, base_y + 2*DescriptionViewModel.DescriptionOffset + 2*textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White))
        )
      }
  
}
