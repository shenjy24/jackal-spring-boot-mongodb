package com.jonas.common.enums;

/**
 * @author shenjy
 * @date 2020/8/28
 * @description
 */
public enum GameTypeEnum {
    ALL(0, "全部"),
    LOL(1, "英雄联盟"),
    GOK(2, "王者荣耀"),
    ;

    private final Integer code;
    private final String message;

    GameTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static GameTypeEnum getEnum(Integer code) {
        for (GameTypeEnum gameServerEnum : values()) {
            if (code.equals(gameServerEnum.getCode())) {
                return gameServerEnum;
            }
        }
        return null;
    }
}
