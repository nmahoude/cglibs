package org.nmx.codingame.analyser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GameContent {
    private final List<Frame> frames = new ArrayList<>();

    public final String gameId;
    public final JsonObject content;
    private List<CodinGamer> players = new ArrayList<>();
    private List<CodinGamer> ranks = new ArrayList<>();

    public CodinGamer playerWithUserId(long userId) {
        for (CodinGamer player : players) {
            if (player.userId == userId) {
                return player;
            }
        }
        throw new RuntimeException("Player not found with userId: " + userId);
    }

    public record CodinGamer(int index, long userId, String pseudo) {
    }

    public GameContent(String gameId, String content) {
        this.gameId = gameId;
        Gson gson = new Gson();
        this.content = gson.fromJson(content, JsonObject.class);
        extractFrames();
        extractCodinGamers();
        extractRanks();
    }

    private void extractCodinGamers() {
        JsonArray codinGamers = content.get("agents").getAsJsonArray();
        for (int i=0;i<codinGamers.size();i++) {
            JsonObject codinGamer = codinGamers.get(i).getAsJsonObject();
            int index = codinGamer.get("index").getAsInt();
            long userId = codinGamer.getAsJsonObject("codingamer").get("userId").getAsLong();
            String pseudo = codinGamer.getAsJsonObject("codingamer").get("pseudo").getAsString();
            players.add(new CodinGamer(index, userId, pseudo));
        }
    }

    private void extractRanks() {
        JsonArray ranks = content.get("ranks").getAsJsonArray();
        for (int i=0;i<ranks.size();i++) {
            int index = ranks.get(i).getAsInt();
            this.ranks.add(this.players.get(index));
        }
    }

    public List<Frame> frames() {
        return frames;
    }

    private void extractFrames() {
        frames.clear();
        for (int i=0;i<content.get("frames").getAsJsonArray().size();i++) {
            Frame frame = new Frame(content.get("frames").getAsJsonArray().get(i).getAsJsonObject());
            frames.add(frame);
        }
    }

    public List<Integer> scores() {
        JsonArray scores = content.get("scores").getAsJsonArray();
        return List.of(scores.get(0).getAsInt(), scores.get(1).getAsInt());
    }

}
