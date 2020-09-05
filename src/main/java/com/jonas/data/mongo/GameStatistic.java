package com.jonas.data.mongo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document("game_statistics")
public class GameStatistic {

    public static final String TABLE = "game_statistics";

    private String gameId;
    private String gameType;
    private String gameMap;
    private Map<String, List<String>> gameTeams = Maps.newHashMap();
    private List<Player> players = Lists.newArrayList();
    private String winnerTeam;
    private String mvp;
    private long beginTime;
    private long endTime;

    @Data
    public static class Player {
        private String playerId;
        private String playerName;
        private int points;
        private boolean winner;
        private boolean mvp;
    }
}
