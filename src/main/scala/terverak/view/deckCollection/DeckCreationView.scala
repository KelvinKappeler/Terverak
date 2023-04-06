// =======================================
// Terverak -> DeckCreation.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view.deckCollection

import indigo.*
import terverak.model.*
import terverak.model.deckCollection.*
import terverak.viewmodel.deckCollection.*
import terverak.viewmodel.*

object DeckCreationView {
  
  private val initialPoint = 
    Point(
      CardsCatalogViewModel.Position.x + CardsCatalogViewModel.DefaultColumnsPerPage * (CardViewModel.CardSize.width + 2 * CardsCatalogViewModel.DefaultOffset.x) + DeckCreationViewModel.DefaultOffsetX,
      CardsCatalogViewModel.Position.y
    )

    def draw(model: DeckCreation, viewModel: DeckCreationViewModel): Batch[SceneNode] = {
      val backgroundBatch = Batch(
        Shape.Box(Rectangle(
          initialPoint.x,
          initialPoint.y,
        ), Fill.Color(RGBA.Purple)).withDepth(Depth(30))
      )

      val cardsBatch =
        viewModel.cards.foldLeft(Batch.empty[SceneNode]) { case (batch, cardViewModel) =>
          batch ++ CardView.draw(cardViewModel.card, cardViewModel, 30)
        }

      val buttonsBatch =
        viewModel.buttons.foldLeft(Batch.empty[SceneNode]) { case (batch, button) =>
          batch ++ ButtonView.draw(button)
        }

      val deckNameText =
        Batch(
          Text("Deck name:", 10, 10, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White))
        ).withDepth(Depth(1))

      val deckNameInput =
        Batch(
          Text(model.deck.name, 10, 20, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White))
        ).withDepth(Depth(1))

      backgroundBatch ++ cardsBatch ++ buttonsBatch ++ deckNameText ++ deckNameInput
    }
}
