package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<String> getSalvoLocations() {

        return locations;
    }

    public void setSalvoLocations(List<String> locations) {
        this.locations = locations;
    }
}
