package com.jonas;

import com.jonas.common.enums.GameTypeEnum;
import com.jonas.data.mongo.GameStatistic;
import com.jonas.data.mongo.PlayerStatistic;
import com.jonas.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Unit test for simple App.
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StatisticService statisticService;

    @Test
    public void addPlayerStat() {
        PlayerStatistic playerStatistic = new PlayerStatistic();
        playerStatistic.setPlayerId("3e5b942b-17c3-3c58-a611-2c99578dabb9");
        playerStatistic.setPlayerName("yun");
        playerStatistic.setGameType(GameTypeEnum.LOL.getMessage());
        playerStatistic.setWins(RandomUtils.nextInt(0, 100));
        playerStatistic.setPoints(RandomUtils.nextInt(0, 1000));
        playerStatistic.setStreakWins(asValue());
        mongoTemplate.save(playerStatistic);
    }

    @Test
    public void addGameStat() {
        GameStatistic gameStatistic = new GameStatistic();
        gameStatistic.setGameId(UUID.randomUUID().toString());
        gameStatistic.setGameType(GameTypeEnum.LOL.getMessage());
        gameStatistic.setGameMap("峡谷之颠");
        Map<String, List<String>> gameTeams = new HashMap<String, List<String>>() {{
            put("红队", Arrays.asList("3f118c8a-e57a-387f-8c5e-cd8e88c0ce74", "0014dbe1-dc2e-34e3-a163-ca1ad78cbc1a"));
            put("蓝队", Arrays.asList("1e0393ce-d509-38a5-9006-493522864fd9", "13852052-f342-3930-ae97-c9bb052b8de0"));
        }};
        gameStatistic.setGameTeams(gameTeams);
        List<GameStatistic.Player> players = new ArrayList<>();
        players.add(buildPerson("3f118c8a-e57a-387f-8c5e-cd8e88c0ce74", "Tom1", true, true));
        players.add(buildPerson("0014dbe1-dc2e-34e3-a163-ca1ad78cbc1a", "Tom2", true, false));
        players.add(buildPerson("1e0393ce-d509-38a5-9006-493522864fd9", "Tom3", false, false));
        players.add(buildPerson("13852052-f342-3930-ae97-c9bb052b8de0", "Tom4", false, false));
        gameStatistic.setPlayers(players);
        gameStatistic.setWinnerTeam("红队");
        gameStatistic.setMvp("Tom1");
        gameStatistic.setBeginTime(1598776500000L);
        gameStatistic.setEndTime(1598777100000L);
        mongoTemplate.save(gameStatistic);
    }

    private GameStatistic.Player buildPerson(String id, String name, boolean isWin, boolean isMvp) {
        GameStatistic.Player player = new GameStatistic.Player();
        player.setPlayerId(id);
        player.setPlayerName(name);
        player.setPoints(RandomUtils.nextInt(0, 100));
        player.setWinner(isWin);
        player.setMvp(isMvp);
        return player;
    }

    private PlayerStatistic.Statistic asValue() {
        PlayerStatistic.Statistic stats = new PlayerStatistic.Statistic();
        stats.setValue(RandomUtils.nextInt(1, 100));
        stats.setGameId(UUID.randomUUID().toString());
        stats.setGameWins(RandomUtils.nextBoolean());
        stats.setGameType(GameTypeEnum.LOL.getMessage());
        stats.setUpdateTime(System.currentTimeMillis());
        return stats;
    }

    @Test
    public void testAggregate() {
        System.out.println(statisticService.getAggregate("3e5b942b-17c3-3c58-a611-2c99578dabb9", 1));
    }

    @Test
    public void testLog() {
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }
}
