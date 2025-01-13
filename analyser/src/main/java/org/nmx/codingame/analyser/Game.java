package org.nmx.codingame.analyser;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final long userId;
    public final String gameId;

    public GameContent rawContent = null;

    public List<PlayerInfo> players = new ArrayList<>();
    public List<Integer> positions = new ArrayList<>();

    public Game(String gameId, long userId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "" + gameId + " - " + players.get(0).nickname() + " vs " + players.get(1).nickname();
    }

    public GameContent fullGame() {
        return rawContent;
    }

    public List<Frame> myFrames() {
        int myIndex = rawContent.playerWithUserId(userId).index();
        return rawContent.frames().stream().filter(f -> f.agentIndex == myIndex).toList();
    }


    public PlayerInfo player(int index) {
        return players.get(index);
    }

    public PlayerInfo p0() {
        return players.get(0);
    }

    public PlayerInfo p1() {
        return players.get(1);
    }

    public boolean isWon() {
        return userId == player(0).userId();
    }

    public boolean isDraw() {
        return positions.get(1) == 0;
    }

}
