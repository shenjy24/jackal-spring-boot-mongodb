package com.jonas.data.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("player_statistics")
public class PlayerStatistic {

    public static final String TABLE = "player_statistics";

    private String playerId;
    private String playerName;
    private String gameType;
    private int points; //总积分
    private int wins;   //总胜利场次
    private Statistic streakWins = Statistic.EMPTY;  //最高连胜

    @Data
    public static class Statistic {

        private static final Statistic EMPTY = new Statistic();

        private int value;
        private String gameId;
        private String gameType;
        private boolean gameWins;
        private long updateTime;
    }
}
