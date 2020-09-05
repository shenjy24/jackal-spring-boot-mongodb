package com.jonas.common.enums;

/**
 * @author shenjy
 * @date 2020/8/28
 * @description
 */
public enum RankEnum {
    FIRST(1, 3, "最强王者"),
    SECOND(4, 10, "傲视宗师"),
    THIRD(11, 50, "超凡大师"),
    FOURTH(51, 100, "璀璨钻石"),
    FIFTH(101, 500, "华贵铂金"),
    SIXTH(501, 2000, "荣耀黄金"),
    SEVENTH(2001, 5000, "不屈白银"),
    EIGHTH(5001, 10000, "英勇黄铜"),
    NINTH(10001, Integer.MAX_VALUE, "坚韧黑铁")
    ;

    private final Integer min;
    private final Integer max;
    private final String message;

    RankEnum(Integer min, Integer max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    Integer getMin() {
        return min;
    }

    Integer getMax() {
        return max;
    }

    public String getMessage() {
        return message;
    }

    public static RankEnum getEnum(Integer rank) {
        for (RankEnum rankEnum : values()) {
            if (rank >= rankEnum.getMin() && rank <= rankEnum.getMax()) {
                return rankEnum;
            }
        }
        return null;
    }
}
