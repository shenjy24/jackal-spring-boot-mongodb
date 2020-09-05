package com.jonas.data.dto;

import com.jonas.data.mongo.PlayerStatistic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenjy
 * @date 2020/8/28
 * @description 汇总数据返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aggregate {
    private String playerName = "";
    private String rank = "";
    private int points;
    private int wins;

    public Aggregate(PlayerStatistic pStat) {
        this.playerName = pStat.getPlayerName();
        this.rank = "";
        this.points = pStat.getPoints();
        this.wins = pStat.getWins();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonPoints {
        private String id;
        private int points;
    }
}
