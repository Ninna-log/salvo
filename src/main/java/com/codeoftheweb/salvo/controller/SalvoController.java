package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/games")
    public Map<String, Object> getGames(Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        }else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", makePlayerDTO(player));
        }
        dto.put("games", gameRepository.findAll().stream().map(this::makeGameDTO).collect(Collectors.toList()));
        return dto;
    }

    /*@RequestMapping("/games") // method to get the IDs
    public List<Long> getGames(){
        return gameRepository.findAll().stream().map(Game::getId).collect(toList());
    }*/

    @PostMapping("/players")
    public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password) {

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Missing data"), HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(email) != null) {
            return new ResponseEntity<>(makeMap("error", "Name already in use"), HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable Long gamePlayerId, Authentication authentication) {
        Player player = playerRepository.findByUserName(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).get();
        if (gamePlayer.getPlayer().getId() == player.getId()) {
            return new ResponseEntity<>(toGameViewDTO(gamePlayer), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(makeMap("error", "Nope, muy dude"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/game/{gameId}/players") // adding the ability to join a game
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameId, Authentication authentication) {
        if (isGuest(authentication)) { // is a guest
            return new ResponseEntity<>(makeMap("error", "Nope, my dude"), HttpStatus.UNAUTHORIZED);
        } else {
            Optional<Game> game = gameRepository.findById(gameId);
            if (game.isEmpty()) {
                return new ResponseEntity<>(makeMap("error", "Game not found"), HttpStatus.NOT_FOUND);
            } else if (game.get().getPlayers().size() > 1) {
                return new ResponseEntity<>(makeMap("error", "Sorry, game is full"), HttpStatus.FORBIDDEN);
            } else { // On the contrary, game has only one player
                Player player = playerRepository.findByUserName(authentication.getName());
                if (game.get().getPlayers().stream().findAny().get().getId() == player.getId()) {
                    return new ResponseEntity<>(makeMap("error", "Nope, my dude"), HttpStatus.FORBIDDEN);
                }else{
                    GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game.get(), LocalDateTime.now()));
                    return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
                }
            }
        }
    }


    @PostMapping("/games") // adding the ability to create a game
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Nope, my dude"), HttpStatus.UNAUTHORIZED);
            // gets the current user with the authentication object, and if there is none, it is sent an Unauthorized response
        } else {
            Game game = gameRepository.save(new Game(LocalDateTime.now()));
            Player player = playerRepository.findByUserName(authentication.getName());
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game, LocalDateTime.now()));
            // creates and saves a new game, and then saves a new gamePlayer for this game and the current user
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
            // and then sends a Created response, with json containing the new gamePlayer id, e.g, {"gpid": 32}
        }
    }

    @PostMapping("/games/players/{gamePlayerId}/ships")
    public ResponseEntity<Map<String, Object>> placingShips(@PathVariable Long gamePlayerId, @RequestBody List<Ship>ships, Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Nope, my dude"), HttpStatus.UNAUTHORIZED);
        }else{
            Optional<GamePlayer> gamePlayerOptional = gamePlayerRepository.findById(gamePlayerId);
            if(gamePlayerOptional.isEmpty()){
                return new ResponseEntity<>(makeMap("error", "No such gameplayer"), HttpStatus.NOT_FOUND);
            }else{
                Player player = playerRepository.findByUserName(authentication.getName());
                // a gamePlayer variable is declared to get rid of the .get()
                GamePlayer gamePlayer = gamePlayerOptional.get();

                if(gamePlayer.getPlayer().getId() != player.getId()){
                    // and if the gamePlayer doesn't match with the current user forbidden response is sent
                    return new ResponseEntity<>(makeMap("error", "Nope, my dude"), HttpStatus.FORBIDDEN);
                }else if (gamePlayer.getShips().size() > 0){
                    return new ResponseEntity<>(makeMap("error", "Nope, you've already placed your ships, my dude"), HttpStatus.FORBIDDEN);
                }else if(ships.size() != 5) {
                    return new ResponseEntity<>(makeMap("error", "You should add 5 ships"), HttpStatus.FORBIDDEN);
                }else{
                    ships.forEach((ship)-> {
                        gamePlayer.addShip(ship);
                        gamePlayerRepository.save(gamePlayer);
                    });
                    //Otherwise, the ships should be added to the game player and saved, and a Created response should be sent.
                    return new ResponseEntity<>(makeMap("gpid", "Ships were successfully added"), HttpStatus.CREATED);
                }
            }
        }
    }

    // <---- Data transfer Objects ----> //

    private Map<String, Object> toGameViewDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Map<String, Object> hits = new LinkedHashMap<String, Object>();
        hits.put("self", new ArrayList<>());
        hits.put("opponent", new ArrayList<>());
        dto.put("id", gamePlayer.getId());
        dto.put("created", gamePlayer.getDate());
        dto.put("gameState", "PLACESHIPS");
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(this::makeGamePlayerDTO).collect(Collectors.toList()));
        dto.put("ships", gamePlayer.getShips().stream().map(this::makeShipDTO).collect(Collectors.toList()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream().flatMap(gamePlayer1 -> gamePlayer1.getSalvos().stream()).map(this::makeSalvoDTO).collect(Collectors.toList()));
        dto.put("hits", hits);
        return dto;
    }

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(this::makeGamePlayerDTO).collect(toList()));
        dto.put("scores", game.getScores().stream().map(this::makeScoreDTO).collect(toList()));
        return dto;
    }

    private Object makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    private Map<String, Object> makeScoreDTO(Score score) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("player", score.getPlayer().getId());
        dto.put("score", score.getScore());
        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getLocations());
        return dto;
    }

    private Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getLocations());
        return dto;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}
