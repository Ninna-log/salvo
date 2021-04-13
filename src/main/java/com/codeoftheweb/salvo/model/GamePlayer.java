package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvos = new HashSet<>();

    public GamePlayer(){
        this.salvos = new HashSet<>();
        this.ships = new HashSet<>();
    }

    public GamePlayer(Player player, Game game, LocalDateTime date) {
        this.player = player;
        this.game = game;
        this.date = date;
    }

    public GamePlayer getEnemy() {
        return this.game.getGamePlayers().stream().filter(gp -> gp.getId() != this.id).findFirst().orElse(new GamePlayer());
    }

    public GameStatus gameStateManagement() {

        GamePlayer enemy = this.getEnemy();
        if (this.getShips().isEmpty()) {
            return GameStatus.PLACESHIPS;
        } else if (enemy == null) { // if enemy doesn't exists yet
            return GameStatus.WAITINGFOROPP;
        } else {  // salvos porque se quiere recorrer el arreglo de salvos, y getSalvos() porque se quiere obtener el size directamente

            int salvosViewer = this.getSalvos().size();
            int salvosEnemy = this.getEnemy().getSalvos().size();
            int sunksViewer = 0;
            int sunksEnemy = 0;

            Optional<Salvo> gpTurn = this.salvos.stream().filter(salvo -> salvo.getTurn() == this.getSalvos().size()).findFirst(); // last turn played
            Optional<Salvo> enemyTurn = enemy.salvos.stream().filter(salvo -> salvo.getTurn() == enemy.getSalvos().size()).findFirst();

            if (gpTurn.isPresent()) { // vieweeerrr
                salvosViewer = gpTurn.get().getTurn(); // gets last turn played by the viewer
                sunksViewer = gpTurn.get().getSunkenShips().size(); // gets sunken ships from the last turn
            }
            if (enemyTurn.isPresent()) { // enemy
                salvosEnemy = enemyTurn.get().getTurn(); // gets last turn played by the enemy
                sunksEnemy = enemyTurn.get().getSunkenShips().size(); // gets sunken ships from the last turn
            }

            // States of the game when has already begun
            if (salvosViewer < salvosEnemy) {
                return GameStatus.PLAY;
            } else if (salvosViewer > salvosEnemy) {
                return GameStatus.WAIT;
            }else { // When salvosViewer == salvosEnemy starts to validate other scenarios
                if (sunksViewer < 5 && sunksEnemy == 5) {
                    return GameStatus.LOST;
                } else if (sunksViewer == 5 && sunksEnemy < 5) {
                    return GameStatus.WON;
                } else if (sunksViewer == 5 && sunksEnemy == 5) {
                    return GameStatus.TIE;
                }else {
                    if (this.id < enemy.getId()){
                        return GameStatus.PLAY;
                    }else {
                        return GameStatus.WAIT;
                    }
                }
            }
        }
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public LocalDateTime getDate() {

        return date;
    }

    public void setDate(LocalDateTime date) {

        this.date = date;
    }

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player player) {

        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {

        this.game = game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public void setSalvos(Set<Salvo> salvos) {
        this.salvos = salvos;
    }

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }

    public void addSalvo(Salvo salvo) {
        salvo.setGamePlayer(this);
        salvos.add(salvo);
    }

    public Score getScore() {
        return player.getScore(game);
    }
}
