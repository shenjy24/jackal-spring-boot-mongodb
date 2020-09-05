package com.jonas.controller;

import com.jonas.common.enums.GameTypeEnum;
import com.jonas.data.dto.Aggregate;
import com.jonas.data.dto.GameInfo;
import com.jonas.data.dto.Peak;
import com.jonas.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description
 */
@Validated
@RestController
@RequestMapping("/game")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    /**
     * 获取玩家汇总数据
     *
     * @param playerId 角色ID
     * @param gameType 游戏模式 1.英雄联盟 2.王者荣耀
     * @return
     */
    @RequestMapping("/getAggregate")
    public Aggregate getAggregate(@NotBlank(message = "playerId不能为空") String playerId,
                                  @NotNull(message = "gameType不能为空") Integer gameType) {
        return statisticService.getAggregate(playerId, gameType);
    }

    /**
     * 获取玩家最近number场比赛数据
     *
     * @param playerId 角色ID
     * @param gameType 游戏模式 0.全部 1.英雄联盟 2.王者荣耀
     * @param number   条数
     * @return
     */
    @RequestMapping("/getRecentGames")
    public List<GameInfo> getRecentGames(@NotBlank(message = "playerId不能为空") String playerId,
                                         @NotNull(message = "gameType不能为空") Integer gameType,
                                         @NotNull(message = "number不能为空") Integer number) {
        return statisticService.getRecentGames(playerId, GameTypeEnum.getEnum(gameType), number);
    }

    /**
     * 分页获取玩家最近比赛数据
     *
     * @param playerId 角色ID
     * @param gameType 游戏模式 0.全部 1.英雄联盟 2.王者荣耀
     * @param lastTime 上一页最后一条数据时间戳
     * @param pageSize 每页条数
     * @return
     */
    @RequestMapping("/queryRecentGames")
    public List<GameInfo> queryRecentGames(@NotBlank(message = "playerId不能为空") String playerId,
                                           @NotNull(message = "gameType不能为空") Integer gameType,
                                           @NotNull(message = "lastTime不能为空") Long lastTime,
                                           @RequestParam(defaultValue = "20") Integer pageSize) {
        pageSize = 0 >= pageSize ? 20 : pageSize;
        return statisticService.queryRecentGames(playerId, GameTypeEnum.getEnum(gameType), lastTime, pageSize);
    }

    /**
     * 获取玩家巅峰数据
     *
     * @param playerId 角色ID
     * @param gameType 游戏模式 0.全部 1.英雄联盟 2.王者荣耀
     * @return
     */
    @RequestMapping("/getPeak")
    public Peak getPeak(@NotBlank(message = "roleId不能为空") String playerId,
                        @NotNull(message = "gameMode不能为空") Integer gameType) {
        return statisticService.getPeak(playerId, GameTypeEnum.getEnum(gameType));
    }
}
