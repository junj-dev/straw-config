package cn.tedu.straw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期格式化工具类
 */

/**
 */
public class DateUtils {

    /**
     * 格式 yyyy年MM月dd日 HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String getDateTimeDisplayString(LocalDateTime dateTime) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2 = dtf2.format(dateTime);

        return strDate2;
    }


    public static String getDistanceTime(Date date) {
        long nowTime = System.currentTimeMillis();
        long diff = nowTime - date.getTime();
        ;
        if (diff < 0) {
            throw new RuntimeException("输入的时间不能大于当前时间");
        } else {

            long second = diff / 1000; //秒
            long min = second / 60; //分钟
            long hour = min / 60; //小时
            long day = hour / 24; //天数
            long month = day / 30; //为了简化计算，默认30天为一个月，月数
            long year = month / 12; //年数

            if (year > 0) {
                return year + "年前";

            } else if (month > 0) {
                return month + "个月前";

            } else if (day > 0) {
                return day + "天前";

            } else if (hour > 0) {
                return hour + "小时前";

            } else if (min > 0) {
                return min + "分钟前";

            } else{
                return "刚刚";

            }
        }


    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1=sdf.parse("2020-02-8 10:22:88");
        String distanceTime = getDistanceTime(date1);
        System.out.println(distanceTime);
    }

}
