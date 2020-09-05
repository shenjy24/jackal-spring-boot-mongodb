package com.jonas.data.dto;

import lombok.Data;

/**
 * @author shenjy
 * @date 2020/8/31
 * @description 巅峰数据
 */
@Data
public class Peak {

    private PeakData wins = PeakData.EMPTY;

    @Data
    public static class PeakData {

        private static final PeakData EMPTY = new PeakData();

        private String gameType = "";
        private int value;
        private long time;
        private Boolean isWin = false;
    }
}
