package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int turn;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    private List<String> locations;

    public Salvo() { }

    public Salvo(int turn, List<String> locations) {
        this.turn = turn;
        this.locations = locations;
    }

    public List<String> getHits() {
   // DEVUELVE UN LISTADO DE LAS CON LAS LOCACIONES DONDE LOS BARCOS DEL ENEMIGO FUERON GOLPEADOS
        Optional<GamePlayer> enemy = this.gamePlayer.getEnemy(); // getEnemy() is defined in gamePlayer

        if(enemy.isPresent()){

            List<String> viewerLocations = this.locations; // viewer locations
            List<String> enemyLocations = new ArrayList<>();
            Set<Ship> enemyShips = enemy.get().getShips(); // enemy locations

            enemyShips.forEach(ship -> enemyLocations.addAll(ship.getLocations()));

            return viewerLocations.stream().filter(enemyLocations::contains).collect(Collectors.toList());
        }
        else{
            return new ArrayList<>();
        }
    }

    public List<Ship> getSunkenShips() {
        // DEVUELVE UN LISTADO DE LOS BARCOS HUNDIDOS DEL ENEMIGO, CUYAS LOCACIONES COINCIDEN CON LOS SALVOS DEL VIEWER
        Optional<GamePlayer> enemy = this.gamePlayer.getEnemy();

        if(enemy.isPresent()){
            List<String> allShots = new ArrayList<>();

            Set<Salvo> viewerSalvoes = this.gamePlayer.getSalvos().stream()
                    .filter(salvo -> salvo.getTurn() <= this.turn).collect(Collectors.toSet());

            Set<Ship> enemyShips = enemy.get().getShips();

            viewerSalvoes.forEach(salvo -> allShots.addAll(salvo.getLocations()));

            return enemyShips.stream().filter(ship -> allShots.containsAll(ship.getLocations()))
                    .collect(Collectors.toList());
        }
        else{
            return new ArrayList<>();
        }
    }

    /*public List<String> missed() {
        Optional<GamePlayer> enemy = this.gamePlayer.getEnemy(); // getEnemy() is defined in gamePlayer

        if(enemy.isPresent()){

            List<String> viewerLocations = this.locations; // viewer locations
            List<String> enemyLocations = new ArrayList<>();
            Set<Ship> enemyShips = enemy.get().getShips(); // enemy locations

            enemyShips.forEach(ship -> enemyLocations.addAll(ship.getLocations()));

            return viewerLocations.stream().filter(enemyLocations::contains).collect(Collectors.toList());
            return scores.stream().filter(score -> score.getGame().equals(game)).findFirst().orElse(null);
            return viewerLocations.stream().filter(loc -> loc.equals(enemyLocations)).collect(Collectors.toList());
        }
        else{
            return new ArrayList<>();
        }
    }*/


    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
