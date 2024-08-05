# salvo

Front-end web application where game players interact with, and a back-end game server to manage the games, scoring, and player profiles. 
Using JavaScript for the front-end client, and the Spring framework for the Java-based RESTful web server.

📌 The core architecture is divided as follows:

- A small Java back-end server that stores Salvo game data, and then sends that data to client apps via a RESTful API.
- A front-end browser-based game interface that graphically shows players the state of the game, including ships they've placed, damage sustained, and scores.

🕹 The game play business' logic is as follows:

- Players can create new games and join games that others have created.
- When a game has both players, players can place their ships on their grids.
- When ships have been placed, players can begin trading salvos (shots) and seeing the results (hits, sinks, and misses).
- When all of a player's ships have been sunk, the game ends and the winner is added to the leaderboard.

🔧 Technologies used:

- Project developed on Java.
- Spring 2.1.4
- Maven
- H2 as a Database and Java Persistence Api (JPA)
- Front-end developed on HTML5, CSS3, Bootstrap v4.1.3 and JavaScript.

❗ How to compile the project:

- Download and install JAVA 11
- Download and install Java SE [JDK]
- Download and install H2
- Run on console, from the root: mvn -

If everything was successfully installed and compiled you'll be able to access the project from:

🌐 http://localhost:8080/web/games.html

Or you can also access to the project on Heroku:

🌐 https://salvo-battleship-multiplayer.herokuapp.com/web/games.html


👀<strong>Interfaces Salvo Battleship<strong>
  
<img align="center" src="https://github.com/Ninna-log/salvo/blob/main/img/interface1.jpg" alt="interface1" height="600" width="1800" />
<br>
<br>

<img align="center" src="https://github.com/Ninna-log/salvo/blob/main/img/interface2.jpg" alt="interface2" height="600" width="1800" />
<br>
<br>

<img align="center" src="https://github.com/Ninna-log/salvo/blob/main/img/interface3.jpg" alt="interface2" height="600" width="1800" />
