package com.jonas.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 【 时间工具类 】
 *
 * @author shenjy 2018/08/25
 */
public class DateUtils {

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_ZERO_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final Integer MINUTE_SECOND = 60;

    public static final Integer HOUR_SECOND = 60 * 60;

    public static final Integer DAY_SECOND = HOUR_SECOND * 24;

    public static final Integer MONTH_SECOND = DAY_SECOND * 30;

    public static final Integer YEAR_SECOND = MONTH_SECOND * 12;

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String currentDate() {
        LocalDate now = LocalDate.now();
        return now.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
    }

    /**
     * 获取昨天日期字符串
     *
     * @return
     */
    public static String getYesterdayDate() {
        LocalDate now = LocalDate.now().minusDays(1);
        return now.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String currentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获取某一个时间段之后的毫秒时间戳
     *
     * @param stamp
     * @param amount
     * @param unit
     * @return
     */
    public static Long getNextStamp(Long stamp, Integer amount, ChronoUnit unit) {
        LocalDateTime now = Instant.ofEpochMilli(stamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime next = now.plus(amount, unit);
        return next.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 根据毫秒时间戳比较日期大小
     *
     * @param stamp1
     * @param stamp2
     * @return
     */
    public static Integer compareDate(Long stamp1, Long stamp2) {
        LocalDate localDate1 = Instant.ofEpochMilli(stamp1).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = Instant.ofEpochMilli(stamp2).atZone(ZoneId.systemDefault()).toLocalDate();

        Integer result = localDate1.compareTo(localDate2);
        if (0 == result) {
            return 0;
        } else if (0 > result) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * "yyyy-MM-dd"格式 转化为 毫秒时间戳
     *
     * @param date
     * @return
     */
    public static Long getStampFromDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"格式 转化为 毫秒时间戳
     *
     * @param dateTime
     * @return
     */
    public static Long getStampFromTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS));
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 处理0时区时间为本地时间
     *
     * @param time
     * @return
     */
    public static Long parseZeroZoneTime(String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(FORMAT_ZERO_ZONE));
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * 毫秒时间戳 转化为 "yyyy-MM-dd"
     *
     * @param stamp
     * @return
     */
    public static String getDate(Long stamp) {
        LocalDate localDate = Instant.ofEpochMilli(stamp).atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
    }

    /**
     * 毫秒时间戳 转化为 "yyyy-MM-dd HH:mm:ss"
     *
     * @param stamp
     * @return
     */
    public static String getDateTime(Long stamp) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(stamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 秒时间戳
     *
     * @return
     */
    public static int currentSecond() {
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     * 秒时间戳
     *
     * @return
     */
    public static int parseToSecond(Long millisecond) {
        return Long.valueOf(millisecond / 1000).intValue();
    }

    public static Integer getSundayEnd() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);
        TemporalAdjuster dateAdjuster = TemporalAdjusters.ofDateAdjuster(localData -> localData.plusDays(DayOfWeek.SUNDAY.getValue() - localData.getDayOfWeek().getValue()));
        String sunday = df.format(LocalDate.now().with(dateAdjuster));

        LocalDateTime localDateTime = LocalDateTime.parse(sunday + " 23:59:59", DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS));
        return Math.toIntExact(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000);
    }

    public static LocalDateTime getLocalDateTime(Integer timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 日期范围判断
     *
     * @param validPeriod 格式：2019-12-18 13:50:00 ~ 2020-01-01 12:00:00
     * @return
     */
    public static boolean isRange(String validPeriod) {
        String pattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\s+~\\s+(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(validPeriod);
        if (m.find()) {
            return isRange(m.group(1), m.group(2));
        }
        return false;
    }

    private static boolean isRange(String start, String end) {
        Long startTimeStamp = getStampFromTime(start);
        Long endTimeStamp = getStampFromTime(end);
        Integer now = currentSecond();
        return now >= startTimeStamp && now <= endTimeStamp;
    }

    /**
     * 当前时间是否为给定时间范围，start为奇数时为有效范围，否则为无效范围
     *
     * @param dates 日期格式: 2019-12-19 12:00:00-15:00:00
     * @return
     */
    public static boolean checkRange(List<String> dates) {
        Long[] dateIndex = getDateIndex(dates);
        if (0 == dateIndex.length) {
            return false;
        }

        long current = System.currentTimeMillis();
        if (current < dateIndex[0] || current > dateIndex[dateIndex.length - 1]) {
            return false;
        }

        int start = 0;
        int end = dateIndex.length - 1;
        while (end - start > 1) {
            int middle = (start + end) / 2;
            if (current < dateIndex[middle]) {
                end = middle;
            } else {
                start = middle;
            }
        }

        return start % 2 == 0;
    }

    private static Long[] getDateIndex(List<String> dates) {
        //现将数据缓存到数组里面
        List<Long> dateArray = new LinkedList<>();
        dates.forEach(line -> {
            if (!line.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}-\\d{2}:\\d{2}:\\d{2}")) {
                return;
            }

            String date = line.substring(0, line.indexOf(" "));
            String timeStart = line.substring(line.indexOf(" ") + 1, line.lastIndexOf("-"));
            String timeEnd = line.substring(line.lastIndexOf("-") + 1);

            Long start = getStampFromTime(date + " " + timeStart);
            Long end = getStampFromTime(date + " " + timeEnd);

            //判断数据是否按顺序增加
            if (end > start && (dateArray.size() == 0 || start > dateArray.get(dateArray.size() - 1))) {
                dateArray.add(start);
                dateArray.add(end);
            }
        });

        Long[] dateIndex = new Long[dateArray.size()];
        return dateArray.toArray(dateIndex);
    }

    /**
     * 获取当天凌晨0点毫秒时间戳
     *
     * @return
     */
    public static long getTodayMorning() {
        return getStampFromDate(currentDate());
    }
}
