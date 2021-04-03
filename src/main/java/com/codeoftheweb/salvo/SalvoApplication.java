package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository,
									  SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {

			Player player1 = new Player("j.bauer@ctu.gov");
			Player player2 = new Player("c.obrian@ctu.gov");
			Player player3 = new Player("kim_bauer@gmail.com");
			Player player4 = new Player("t.almeida@ctu.gov");
			// saving a couple of players
			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now().plusHours(1));
			Game game3 = new Game(LocalDateTime.now().plusHours(2));
			Game game4 = new Game(LocalDateTime.now().plusHours(2));
			// saving a couple of games
			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			gameRepository.save(game4);

			// creating ships locations arrays
			List<String> shipLocations1 = new ArrayList<>(Arrays.asList("B5", "B6", "B7", "B8", "B9", "D10", "D9"));
			List<String> shipLocations2 = new ArrayList<>(Arrays.asList("C5","C6", "C7", "C8", "C9", "A5", "A6"));
			List<String> shipLocations3 = new ArrayList<>(Arrays.asList("D5","D6", "D7", "D8", "D9", "A1", "A8"));
			List<String> shipLocations4 = new ArrayList<>(Arrays.asList("G5","G6", "G7", "G8", "G9", "F5", "F6"));
			List<String> shipLocations5 = new ArrayList<>(Arrays.asList("H5","H6", "H7", "H8", "H9", "C8", "C9"));
			List<String> shipLocations6 = new ArrayList<>(Arrays.asList("F3","G3", "H3", "H4", "H5", "D2", "D3"));
			List<String> shipLocations7 = new ArrayList<>(Arrays.asList("F4","G4", "H4", "F3", "F2", "B5", "B7"));
			List<String> shipLocations8 = new ArrayList<>(Arrays.asList("F5","F6", "F7", "G5", "H5", "A4", "A6"));
			List<String> shipLocations9 = new ArrayList<>(Arrays.asList("A1","B1", "C1", "D1", "E1", "F1", "G1"));
			List<String> shipLocations10 = new ArrayList<>(Arrays.asList("B2","C2", "D2", "E2", "F2", "G2", "H2"));
			List<String> shipLocations11 = new ArrayList<>(Arrays.asList("A3","B3", "C3", "D3", "E3", "F3", "G3"));
			List<String> shipLocations12= new ArrayList<>(Arrays.asList("A5","B5", "C5", "D5", "E5", "F5", "G5"));
			List<String> shipLocations13 = new ArrayList<>(Arrays.asList("A7","B7", "C7", "D7", "E7", "F7", "G7"));

			// creating salvoes locations
			List<String> salvoLocations1 = new ArrayList<>(Arrays.asList("C5","C6", "C7", "C8", "C9", "G4", "H4"));
			List<String> salvoLocations2 = new ArrayList<>(Arrays.asList("D4", "E4", "F4", "D5","D6", "F6", "F7"));
			List<String> salvoLocations3 = new ArrayList<>(Arrays.asList("E5","E6", "J6", "J7", "H5", "D6", "D7"));
			List<String> salvoLocations4 = new ArrayList<>(Arrays.asList("F5","F6", "H1", "H2", "H3", "C6", "C7"));
			List<String> salvoLocations5 = new ArrayList<>(Arrays.asList("G5","G6", "G8", "G9", "G10", "D5", "F5"));
			List<String> salvoLocations6 = new ArrayList<>(Arrays.asList("H3","H4", "D10", "F10", "G10", "F5", "G5"));
			List<String> salvoLocations7 = new ArrayList<>(Arrays.asList("F2","F3", "I3", "H5", "H6", "E6", "F6"));
			List<String> salvoLocations8 = new ArrayList<>(Arrays.asList("H5","H6", "H3", "B3", "B4", "B5", "B7", "D7"));
			List<String> salvoLocations9 = new ArrayList<>(Arrays.asList("I2","A3", "G3", "F5", "J6", "G6", "G7"));
			List<String> salvoLocations10 = new ArrayList<>(Arrays.asList("A1","A2", "A3", "A4", "B1", "B2", "B3", "B4"));
			List<String> salvoLocations11 = new ArrayList<>(Arrays.asList("B2","C2", "D2", "E2", "F2", "G2", "H2", "I2"));
			List<String> salvoLocations12 = new ArrayList<>(Arrays.asList("A9","B9", "C9", "D9", "E9", "F9", "G9"));
			List<String> salvoLocations13 = new ArrayList<>(Arrays.asList("J1","J2", "J3", "J4", "J5", "J6", "J7", "J8"));

			// creating ships with their locations as arrays
			Ship ship1 = new Ship("Carrier",shipLocations1);
			Ship ship2 = new Ship("Battleship", shipLocations2);
			Ship ship3 = new Ship("Submarine", shipLocations3);
			Ship ship4 = new Ship("Destroyer", shipLocations4);
			Ship ship5 = new Ship("Patrol Boat", shipLocations5);
			Ship ship6 = new Ship("Submarine", shipLocations6);
			Ship ship7 = new Ship("Destroyer", shipLocations7);
			Ship ship8 = new Ship("Patrol Boat", shipLocations8);
			Ship ship9 = new Ship("Submarine", shipLocations9);
			Ship ship10 = new Ship("Destroyer", shipLocations10);
			Ship ship11 = new Ship("Carrier",shipLocations11);
			Ship ship12 = new Ship("Battleship", shipLocations12);
			Ship ship13 = new Ship("Submarine", shipLocations13);


			// creating salvoes with their salvoLocations as arrays
			Salvo salvo1 = new Salvo(1,salvoLocations1);
			Salvo salvo2 = new Salvo(2,salvoLocations2);
			Salvo salvo3 = new Salvo(3,salvoLocations3);
			Salvo salvo4 = new Salvo(4,salvoLocations4);
			Salvo salvo5 = new Salvo(5,salvoLocations5);
			Salvo salvo6 = new Salvo(3,salvoLocations6);
			Salvo salvo7 = new Salvo(4,salvoLocations7);
			Salvo salvo8 = new Salvo(5,salvoLocations8);
			Salvo salvo9 = new Salvo(4,salvoLocations9);
			Salvo salvo10 = new Salvo(5,salvoLocations10);
			Salvo salvo11 = new Salvo(1,salvoLocations11);
			Salvo salvo12 = new Salvo(2,salvoLocations12);
			Salvo salvo13 = new Salvo(3,salvoLocations13);


			// associating everything that has to be with game and player with gamePlayer @Many-to-many
			GamePlayer gamePlayer1 = new GamePlayer(player1, game1, LocalDateTime.now());
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1, LocalDateTime.now());
			GamePlayer gamePlayer3 = new GamePlayer(player3, game2, LocalDateTime.now());
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2, LocalDateTime.now());
			GamePlayer gamePlayer5 = new GamePlayer(player4, game3, LocalDateTime.now());
			GamePlayer gamePlayer6 = new GamePlayer(player3, game3, LocalDateTime.now());
			GamePlayer gamePlayer7 = new GamePlayer(player2, game4, LocalDateTime.now());
			GamePlayer gamePlayer8 = new GamePlayer(player1, game4, LocalDateTime.now());


			//adding ships to every gamePlayer
			gamePlayer1.addShip(ship1);
			gamePlayer1.addShip(ship2);
			gamePlayer2.addShip(ship3);
			gamePlayer2.addShip(ship4);
			gamePlayer3.addShip(ship5);
			gamePlayer3.addShip(ship6);
			gamePlayer4.addShip(ship7);
			gamePlayer4.addShip(ship8);
			gamePlayer5.addShip(ship9);
			gamePlayer5.addShip(ship10);
			gamePlayer6.addShip(ship11);
			gamePlayer7.addShip(ship12);
			gamePlayer8.addShip(ship13);


			//adding salvoes to every gamePlayer
			gamePlayer1.addSalvo(salvo1);
			gamePlayer1.addSalvo(salvo2);
			gamePlayer2.addSalvo(salvo3);
			gamePlayer2.addSalvo(salvo4);
			gamePlayer3.addSalvo(salvo5);
			gamePlayer3.addSalvo(salvo6);
			gamePlayer4.addSalvo(salvo7);
			gamePlayer4.addSalvo(salvo8);
			gamePlayer5.addSalvo(salvo9);
			gamePlayer5.addSalvo(salvo10);
			gamePlayer6.addSalvo(salvo11);
			gamePlayer7.addSalvo(salvo12);
			gamePlayer8.addSalvo(salvo13);

			//saving a couple of gamePlayers
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);


			Score score1 = new Score(game1, player1, 1, LocalDateTime.now());
			Score score2 = new Score(game1, player2, 0, LocalDateTime.now());
			Score score3 = new Score(game2, player3, 0, LocalDateTime.now());
			Score score4 = new Score(game2, player4, 1, LocalDateTime.now());
			Score score5 = new Score(game3, player4, 0.5, LocalDateTime.now());
			Score score6 = new Score(game3, player3, 0.5, LocalDateTime.now());
			Score score7 = new Score(game4, player2, 1, LocalDateTime.now());
			Score score8 = new Score(game4, player1, 0, LocalDateTime.now());
			Score score9 = new Score(game4, player3, 1, LocalDateTime.now());
			Score score10 = new Score(game4, player4, 1, LocalDateTime.now());
			Score score11 = new Score(game3, player1, 0.5, LocalDateTime.now());
			Score score12 = new Score(game3, player2, 0.5, LocalDateTime.now());


			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);
			scoreRepository.save(score7);
			scoreRepository.save(score8);
			scoreRepository.save(score9);
			scoreRepository.save(score10);
			scoreRepository.save(score11);
			scoreRepository.save(score12);

		};
	}
}
