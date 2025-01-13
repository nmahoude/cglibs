package org.nmx.codingame.analyser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LastGamesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(LastGamesReader.class);

    private static final Path BASE = Paths.get(new File("target/battleAnalyser").getAbsolutePath());
    public static final String LAST_GAMES_URL = "https://www.codingame.com/services/gamesPlayersRanking/findLastBattlesByTestSessionHandle";
    public static final String GET_GAME_URL = "https://www.codingame.com/services/gameResult/findByGameId";
    private final List<Game> matches;


    private Path base;
    private final String rememberMe;
    private final long userId;
    private final String id;

    public LastGamesReader(String gameId) throws Exception {
        this(gameId, System.getenv().get("REMEMBER_ME"), Long.parseLong(System.getenv().get("MY_USER_ID")));
    }

    public LastGamesReader(String gameId, String rememberMe, long userId) throws Exception {
        this.id = gameId;
        this.rememberMe = rememberMe;
        this.userId = userId;
        base = BASE;

        matches = loadGames();
    }

    public List<Game> games() {
        return matches;
    }

    private List<Game> loadGames() throws IOException {

        String result = getAllGamesListAsJson();

        Gson gson = new Gson();
        JsonArray array = gson.fromJson(result, JsonArray.class);

        List<Game> infos = new ArrayList<>();
        for (int i=0;i<array.size();i++) {
            JsonObject o = (JsonObject)array.get(i);
            LOGGER.debug("reading all games info : {}", o);

            String gameId = o.get("gameId").toString();
            if (!o.get("done").getAsBoolean()) {
                LOGGER.info("Game "+gameId+" is not done yet ... passing");
                continue;
            }

            Game info = new Game(gameId, userId);
            if (!Files.exists(base.resolve(gameId+".json"))) {
                info.rawContent = getOneGame(gameId);
            } else {
                var fullGame = new GameContent(gameId, Files.readString(base.resolve(gameId+".json")));
                info.rawContent = fullGame;
            }


            JsonArray players = (JsonArray)o.get("players");

            for (int p=0;p<2;p++) {
                JsonObject playerAsJson = (JsonObject)players.get(p);
                String nickname = playerAsJson.get("nickname") != null ? playerAsJson.get("nickname").getAsString() : "?";
                long userId = playerAsJson.get("userId").getAsLong();
                String playerAgentId = playerAsJson.get("playerAgentId").getAsString();
                PlayerInfo p0 = new PlayerInfo(nickname, userId, playerAgentId);

                int position = playerAsJson.get("position").getAsInt();

                info.players.add(p0);
                info.positions.add(position);

            }
            infos.add(info);
        }

        LOGGER.info("Read full done");
        return infos;
    }

    private String getAllGamesListAsJson() throws IOException {
        String result;
        Path allGamesPath = base.resolve("allgames.json");
        if (!Files.exists(allGamesPath)) {
            LOGGER.info("Getting all games from codingame ...");
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(LAST_GAMES_URL);


            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.json("["+id+",null]"));

            result = response.readEntity(String.class);
            Files.createDirectories(base);
            Files.write(allGamesPath, result.getBytes());
        } else {
            LOGGER.info("Getting all games info from cache ...");
            result = Files.readString(allGamesPath);
        }
        return result;
    }

    private GameContent getOneGame(String gameId) {

        LOGGER.info("Reading game {}", gameId);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target(GET_GAME_URL);

        Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .cookie("rememberMe", rememberMe)
                .post(Entity.json("["+gameId+","+userId+"]"));

        String result = response.readEntity(String.class);

        GameContent game = new GameContent(gameId, result);
        try {
            Files.createDirectories(base);
            Files.write(base.resolve(gameId+".json"), result.getBytes());
        } catch (IOException e) {
            LOGGER.error("Error while reading game {}", gameId, e);
        }

        LOGGER.debug("content of game {} is {}", gameId, result);
        return game;
    }
}
