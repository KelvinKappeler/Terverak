# Terverak

This repo is Bastien Jolidon and Kelvin Kappeler's Bachelor project for the IC section of EPFL entitled: _Creation of a video game in Scala, and test the properties of its model with Stainless._

The project is composed of two parts:
- The first part is the video game, playable, which you can find in the main branch. You can test the game by clicking on this [link](https://kelvinkappeler.github.io/Terverak/).
- The second part is in the Stainless branch and is the code that allowed us to demonstrate the properties of the game model via Stainless. You can, on Windows, run the following command to analyze the game yourself with Stainless :

```
.\stainless.bat .\src\main\scala\terverak\card\* .\src\main\scala\terverak\utils\* .\src\main\scala\terverak\play\* .\src\main\ scala\terverak\deckCollection\*
```

## Rules of the game

There are two types of card:
 - Minion Card
 - Spell Card

Each card can be activated in two different ways:
 - You can spend some mana to play the card
 - You can discard for free the card (for example, to get 3 mana for the turn)

Each new turn, we draw 2 more cards.
All the cards played and destroyed are found in the deck again when the deck is empty.
The mana is reset to 0 at the beginning of each turn.

## How to play
You can play the game by :
 - Dragging the cards to the board to play them (it will cost mana indicated on the card left upper corner)
 - Dragging the cards to the discard zone to discard them (it is free)
 - When one of your minion have a red border, you can drag it to the opponent's minion or hero to attack it
 - Press the button "End turn" to end your turn and start the opponent's one
 
## Enjoy the game !