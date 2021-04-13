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

    public Salvo() {
        this.locations = new ArrayList<>();
        this.turn = 0;
    }

    public Salvo(int turn, List<String> locations, GamePlayer gamePlayer) {
        this.turn = turn;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }

    public List<Ship> getSunkenShips() {  // list enemy's ships
        GamePlayer enemy = this.gamePlayer.getEnemy();

        if(enemy != null){
            List<String> allShots = new ArrayList<>();

            Set<Salvo> viewerSalvoes = this.gamePlayer.getSalvos().stream()
                    .filter(salvo -> salvo.getTurn() <= this.turn).collect(Collectors.toSet());

            Set<Ship> enemyShips = enemy.getShips();

            viewerSalvoes.forEach(salvo -> allShots.addAll(salvo.getLocations()));

            return enemyShips.stream().filter(ship -> allShots.containsAll(ship.getLocations()))
                    .collect(Collectors.toList());
        }
        else{
            return new ArrayList<>();
        }
    }

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
