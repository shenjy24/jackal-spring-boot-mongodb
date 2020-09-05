package com.jonas.data.mongo;

import com.jonas.common.constant.GameConstant;
import com.jonas.data.dto.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description
 */
@Repository
public class MongoManager {
    @Autowired
    private MongoTemplate mongoTemplate;

    public PlayerStatistic getPlayerStat(String playerId, String gameType) {
        Criteria criteria = Criteria.where("playerId").is(playerId).and("gameType").is(gameType);
        return mongoTemplate.findOne(new Query(criteria), PlayerStatistic.class);
    }

    public List<Aggregate.PersonPoints> aggregatePoint(String gameType) {
        Criteria criteria = Criteria.where("gameType").is(gameType);
        Sort sort = Sort.by(Sort.Direction.DESC, "points");
        Query query = new Query(criteria).with(sort).limit(GameConstant.RANK_NUM);
        return mongoTemplate.find(query, Aggregate.PersonPoints.class, PlayerStatistic.TABLE);
    }

    public List<GameStatistic> listGameStat(String playerId, String gameType, Integer number) {
        Criteria criteria = Criteria.where("players.playerId").is(playerId);
        if (null != gameType) {
            criteria.and("gameType").is(gameType);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "endTime");
        Query queue = new Query(criteria).with(sort).limit(number);
        return mongoTemplate.find(queue, GameStatistic.class);
    }

    public List<GameStatistic> queryGameStat(String playerId, String gameType, long lastTime, int pageSize) {
        Criteria criteria = Criteria.where("players.playerId").is(playerId)
                .and("gameType").is(gameType)
                .and("endTime").lt(lastTime);
        Sort sort = Sort.by(Sort.Direction.DESC, "endTime");
        Query query = new Query(criteria).with(sort).limit(pageSize);

        return mongoTemplate.find(query, GameStatistic.class);
    }
}
