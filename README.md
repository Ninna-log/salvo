# salvo
Web-based multi-player game application

In this proyect I visualized myself as a developer who's been contacted by a board game company looking to use their brand recognition to market online games with a retro touch, asking for a multi-player online version of a Salvo-like game engine. Taking into account that Salvo was a pencil and paper game that was the basis for the popular Battleship game. The basic idea involves guessing where other players have hidden objects.

I created a front-end web application that game players interact with, and a back-end game server to manage the games, scoring, and player profiles. 
Using JavaScript for the front-end client, and the Spring Boot framework for the Java-based RESTful web server.

The core architecture is divided as follows:

A small Java back-end server that stores Salvo game data, and then sends that data to client apps via a RESTful API.
A front-end browser-based game interface that graphically shows players the state of the game, including ships they've placed, damage sustained, and scores.
And the game play business' logic is as follows:

Players can create new games and join games that others have created.
When a game has both players, players can place their ships on their grids.
When ships have been placed, players can begin trading salvos (shots) and seeing the results (hits, sinks, and misses).
When all of a player's ships have been sunk, the game ends and the winner is added to the leaderboard.

URL: https://salvo-battleship-multiplayer.herokuapp.com/web/games.html
