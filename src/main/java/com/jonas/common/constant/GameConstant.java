package com.jonas.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author shenjy
 * @date 2020/8/28
 * @description 游戏类型常量
 */
public class GameConstant {
    //PE起床、无限火力
    public static final List<String> PE_BED = Arrays.asList("起床2队8人", "起床4队4人", "起床4队8人", "起床8队1人");
    public static final List<String> PE_FIRE = Arrays.asList("无限火力");
    //PC起床、无限火力
    public static final List<String> PC_BED = Arrays.asList("单人起床", "组队起床");
    public static final List<String> PC_FIRE = Arrays.asList("职业起床", "无限火力32人", "无限火力64人");

    //排名数
    public static final int RANK_NUM = 10000;
}
