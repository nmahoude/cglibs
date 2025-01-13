package org.nmx.codingame.analyser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Stats {
    List<Stat> stats = new ArrayList<>();

    public Stats(List<Game> games, Function<Game, Stat> fn) {
        for (Game game : games) {
            stats.add(fn.apply(game));
        }
    }


    public void add(Stat stat) {
        stats.add(stat);
    }

    public void printWinLoseStats(String message, List<? extends Stat> stats) {
        int total = stats.size();
        int won = (int) stats.stream().filter(Stat::isWon).count();
        System.err.println(message + " Won : "+won+" / "+total+" ("+(100.0*won/total)+"%)");
    }

    public void printStat(String message, double value) {
        System.err.println(message+" "+value);
    }

    public void printStat(String message, int value) {
        System.err.println(message+" "+value);
    }

    public <T> List<T> stats(Class<T> clazz) {
        return (List<T>)stats;
    }

}
