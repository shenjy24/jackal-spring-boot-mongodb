package com.jonas.service;

import com.jonas.common.BizException;
import com.jonas.common.SystemCode;
import com.jonas.common.enums.GameTypeEnum;
import com.jonas.common.enums.RankEnum;
import com.jonas.data.dto.Aggregate;
import com.jonas.data.dto.GameInfo;
import com.jonas.data.dto.Peak;
import com.jonas.data.mongo.GameStatistic;
import com.jonas.data.mongo.MongoManager;
import com.jonas.data.mongo.PlayerStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")  //设置缓存的共有属性
public class StatisticService {
    @Autowired
    private MongoManager mongoManager;

    @Cacheable(keyGenerator = "caffeineKeyGenerator")
    public Peak getPeak(String playerId, GameTypeEnum gameType) {
        if (null == playerId || null == gameType) {
            throw new BizException(SystemCode.PARAM_ERROR);
        }

        PlayerStatistic playerStatistic = mongoManager.getPlayerStat(playerId, gameType.getMessage());
        if (null == playerStatistic) {
            return null;
        }
        return buildPeak(playerStatistic);
    }

    @Cacheable(keyGenerator = "caffeineKeyGenerator")
    public List<GameInfo> getRecentGames(String playerId, GameTypeEnum gameType, Integer number) {
        if (null == playerId || null == gameType || null == number || 0 > number) {
            throw new BizException(SystemCode.PARAM_ERROR);
        }

        String gameModeArg = GameTypeEnum.ALL.equals(gameType) ? null : gameType.getMessage();
        List<GameStatistic> gameStatistics = mongoManager.listGameStat(playerId, gameModeArg, number);

        List<GameInfo> gameInfos = new ArrayList<>(gameStatistics.size());
        for (GameStatistic gameStatistic : gameStatistics) {
            gameInfos.add(buildGameInfo(gameStatistic));
        }
        return gameInfos;
    }

    @Cacheable(keyGenerator = "caffeineKeyGenerator")
    public List<GameInfo> queryRecentGames(String playerId, GameTypeEnum gameType, Long lastTime, Integer pageSize) {
        if (null == playerId || null == gameType || null == lastTime) {
            throw new BizException(SystemCode.PARAM_ERROR);
        }

        String gameModeArg = GameTypeEnum.ALL.equals(gameType) ? null : gameType.getMessage();
        List<GameStatistic> gameStatistics = mongoManager.queryGameStat(playerId, gameModeArg, lastTime, pageSize);
        List<GameInfo> gameInfos = new ArrayList<>(gameStatistics.size());
        for (GameStatistic gameStatistic : gameStatistics) {
            gameInfos.add(buildGameInfo(gameStatistic));
        }
        return gameInfos;
    }

    @Cacheable(keyGenerator = "caffeineKeyGenerator")
    public Aggregate getAggregate(String playerId, Integer gameType) {
        GameTypeEnum gameTypeEnum = GameTypeEnum.getEnum(gameType);
        if (null == gameTypeEnum) {
            throw new BizException(SystemCode.PARAM_ERROR);
        }

        PlayerStatistic playerStatistic = mongoManager.getPlayerStat(playerId, gameTypeEnum.getMessage());
        if (null == playerStatistic) {
            return null;
        }

        Aggregate aggregate = new Aggregate(playerStatistic);
        aggregate.setRank(getRank(playerId, gameTypeEnum).getMessage());
        return aggregate;
    }

    public RankEnum getRank(String id, GameTypeEnum gameType) {
        if (null == id || null == gameType) {
            throw new BizException(SystemCode.PARAM_ERROR);
        }

        List<Aggregate.PersonPoints> personPoints = mongoManager.aggregatePoint(gameType.getMessage());
        int rank = 1;
        for (Aggregate.PersonPoints points : personPoints) {
            if (points.getId().equals(id)) {
                return RankEnum.getEnum(rank);
            }
            rank++;
        }
        return RankEnum.getEnum(rank);
    }

    private GameInfo buildGameInfo(GameStatistic gameStatistic) {
        GameInfo gameInfo = GameInfo.builder()
                .gameId(gameStatistic.getGameId())
                .beginTime(gameStatistic.getBeginTime())
                .endTime(gameStatistic.getEndTime())
                .gameType(gameStatistic.getGameType())
                .gameMap(gameStatistic.getGameMap())
                .winnerTeam(gameStatistic.getWinnerTeam())
                .mvp(gameStatistic.getMvp())
                .build();

        List<GameInfo.TeamInfo> teamInfos = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : gameStatistic.getGameTeams().entrySet()) {
            GameInfo.TeamInfo teamInfo = new GameInfo.TeamInfo();
            teamInfo.setTeam(entry.getKey());
            List<GameInfo.PlayerInfo> playerInfos = new ArrayList<>(entry.getValue().size());
            for (String playerId : entry.getValue()) {
                GameStatistic.Player player = gameStatistic.getPlayers().stream()
                        .filter(e -> e.getPlayerId().equals(playerId)).findFirst().orElse(null);
                if (null != player) {
                    GameInfo.PlayerInfo playerInfo = new GameInfo.PlayerInfo();
                    playerInfo.setPlayerId(player.getPlayerId());
                    playerInfo.setPlayerName(player.getPlayerName());
                    playerInfo.setIsWin(player.isWinner());
                    playerInfo.setPoints(player.getPoints());
                    playerInfos.add(playerInfo);
                }
            }
            teamInfo.setPersons(playerInfos);
            teamInfos.add(teamInfo);
        }

        gameInfo.setTeams(teamInfos);
        return gameInfo;
    }

    private Peak buildPeak(PlayerStatistic pStatistic) {
        Peak peak = new Peak();
        peak.setWins(buildPeakData(pStatistic.getStreakWins()));
        return peak;
    }

    private Peak.PeakData buildPeakData(PlayerStatistic.Statistic statistic) {
        Peak.PeakData peakData = new Peak.PeakData();
        peakData.setGameType(statistic.getGameType());
        peakData.setIsWin(statistic.isGameWins());
        peakData.setTime(statistic.getUpdateTime());
        peakData.setValue(statistic.getValue());
        return peakData;
    }
}
