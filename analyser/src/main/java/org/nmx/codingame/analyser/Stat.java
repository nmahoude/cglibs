package org.nmx.codingame.analyser;

public class Stat {
    public boolean isWon;
    public int framesCount;

    public Stat(Game game) {
        isWon = game.isWon();
        framesCount = game.myFrames().size();
    }

    public boolean isWon()  {
        return isWon;
    }
}
