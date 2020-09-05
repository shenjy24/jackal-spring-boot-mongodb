package com.jonas.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author shenjy
 * @date 2020/8/31
 * @description
 */
@Data
@Builder
public class GameInfo {
    private String gameId;
    private long beginTime;
    private long endTime;
    private String gameType;
    private String gameMap;
    private String winnerTeam;
    private String mvp;
    private List<TeamInfo> teams;

    @Data
    public static class TeamInfo {
        private String team;
        private List<PlayerInfo> persons;
    }

    @Data
    public static class PlayerInfo {
        private String playerId;
        private String playerName;
        private Boolean isWin;
        private int points;
    }
}
