package com.eren.snowframe.utils;

import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 准备第一个模板，从字符串中提取出日期数字
     */
    private static String pat1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 准备第二个模板，将提取后的日期数字变为指定的格式
     */
    private static String pat2 = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 实例化模板对象
     */
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);
    private static long timeMilliseconds;

    public static Date Dates() {
        Date datetimeDate;
        Long dates = 1361515285070L;
        datetimeDate = new Date(dates);
        return datetimeDate;
    }

    public static String getTime(String commitDate) {
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        Date date = null;
        try {
            if (commitDate.length() > 19) {
                commitDate = commitDate.substring(0, 18);
            }
            if (commitDate.length() == 16) {
                StringBuffer buffer = new StringBuffer(commitDate);
                buffer.append(":00");
                commitDate = buffer.toString();
            }
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(commitDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int nowDate = Integer.valueOf(currDate.substring(8, 10));
        int commit = Integer.valueOf(commitDate.substring(8, 10));

        String monthDay = sdf2.format(date).substring(5, 12);
        String yearMonthDay = sdf2.format(date).substring(0, 12);
        int month = Integer.valueOf(monthDay.substring(0, 2));
        int day = Integer.valueOf(monthDay.substring(3, 5));
        if (month < 10 && day < 10) {
            monthDay = monthDay.substring(1, 3) + monthDay.substring(4);
        } else if (month < 10) {
            monthDay = monthDay.substring(1);
        } else if (day < 10) {
            monthDay = monthDay.substring(0, 3) + monthDay.substring(4);
        }
        int yearMonth = Integer.valueOf(yearMonthDay.substring(5, 7));
        int yearDay = Integer.valueOf(yearMonthDay.substring(8, 10));
        if (yearMonth < 10 && yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5)
                    + yearMonthDay.substring(6, 8) + yearMonthDay.substring(9);
        } else if (yearMonth < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5)
                    + yearMonthDay.substring(6);
        } else if (yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 8)
                    + yearMonthDay.substring(9);
        }
        String str = " 00:00:00";
        float currDay = farmatTime(currDate.substring(0, 10) + str);
        float commitDay = farmatTime(commitDate.substring(0, 10) + str);
        int currYear = Integer.valueOf(currDate.substring(0, 4));
        int commitYear = Integer.valueOf(commitDate.substring(0, 4));
        int flag = (int) (farmatTime(currDate) / 1000 - farmatTime(commitDate) / 1000);
        String des = null;
        String hourMin = commitDate.substring(11, 16);
        int temp = flag;
        if (temp < 60) {
            System.out.println("A");
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = "刚刚";
            }
        } else if (temp < 60 * 60) {
            System.out.println("B");
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = temp / 60 + "分钟前";
            }
        } else if (temp < 60 * 60 * 24) {
            System.out.println("C");
            int hour = temp / (60 * 60);
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                if (hour < 6) {
                    des = hour + "小时前";
                } else {
                    des = hourMin;
                }
            }
        } else if (temp < (60 * 60 * 24 * 2)) {
            System.out.println("D");
            if (nowDate - commit == 1) {
                des = "昨天  " + hourMin;
            } else {
                des = "前天  " + hourMin;
            }
        } else if (temp < 60 * 60 * 60 * 3) {
            System.out.println("E");
            if (nowDate - commit == 2) {
                des = "前天  " + hourMin;
            } else {
                if (commitYear < currYear) {
                    des = yearMonthDay + hourMin;
                } else {
                    des = monthDay + hourMin;
                }
            }
        } else {
            System.out.println("F");
            if (commitYear < currYear) {
                des = yearMonthDay + hourMin;
            } else {
                des = monthDay + hourMin;
            }
        }
        if (des == null) {
            des = commitDate;
        }
        return des;
    }

    public static Long farmatTime(String string) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = Date(sf.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Date Date(Date date) {
        Date datetimeDate;
        datetimeDate = new Date(date.getTime());
        return datetimeDate;
    }

    public static Date Date() {
        Date datetimeDate;
        Long dates = 1361514787384L;
        datetimeDate = new Date(dates);
        return datetimeDate;
    }

    /**
     * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
     * 如果在今年的1天之外的只显示“月-日”，例如“05-03” 如果不是今年的显示“年-月-日”，例如“2014-03-11”
     *
     * @param time
     * @return
     */
    public static String translateTime(String time) {
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
        }

        long timeDifferent = currentMilliseconds - timeMilliseconds;
        if (timeDifferent < 60000) {// 一分钟之内

            return "刚刚";
        }
        if (timeDifferent < 3600000) {// 一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟前";
        }
        long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
        if (timeDifferent < l) {// 小于一天
            long longHour = timeDifferent / 3600000;
            int hour = (int) (longHour % 100);
            return hour + "小时前";
        }
        if (timeDifferent >= l) {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
        return time;
    }


    /**
     * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
     * 如果在今年的1天之外的只显示“月-日”，例如“05-03” 如果不是今年的显示“年-月-日”，例如“2014-03-11”
     *
     * @param time
     * @return
     */
    public static String getTranslateTime(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        long putMilliseconds = Long.valueOf(time) * 1000;// 发布日期的毫秒值

        long timeDifferent = currentMilliseconds - Long.valueOf(time) * 1000;

        Log.d("TimeUtil", "currentMilliseconds1:" + currentMilliseconds);
        Log.d("TimeUtil", "currentMilliseconds2:" + putMilliseconds);
        Log.d("TimeUtil", "currentMilliseconds3:" + timeDifferent);

        if (timeDifferent < 60000) {// 一分钟之内

            return "刚刚";
        }
        if (timeDifferent < 3600000) {// 一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟前";
        }
        long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
        if (timeDifferent < l) {// 小于一天
            long longHour = timeDifferent / 3600000;
            int hour = (int) (longHour % 100);
            return hour + "小时前";
        }
        long day = 24 * 60 * 60 * 1000 * 30L; // 每月的毫秒数
        if (timeDifferent < day) {// 小于一月
            long longDay = timeDifferent / 86400000;
            int dayNum = (int) (longDay % 100);
            return dayNum + "天前";
        }
        // 将给定的字符串中的日期提取出来
//        try {
//            Date date = sdf1.parse(String.valueOf(timeDifferent));
//            if (date != null) {
//                timeMilliseconds = date.getTime();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if (timeDifferent >= day) {
            String currYear = currDate.substring(0, 4);
            String year = formatDataTime(putMilliseconds).substring(0, 4);
            if (!year.equals(currYear)) {
                return formatDataTime(putMilliseconds).substring(0, 10);
            }
            Log.d("TimeUtil", "currentMilliseconds4:" + formatDataTime(putMilliseconds).substring(0, 10));
            Log.d("TimeUtil", "currentMilliseconds4:" + formatDataTime(putMilliseconds).substring(5, 10));
            return formatDataTime(putMilliseconds).substring(5, 10);
        }
        return String.valueOf(timeDifferent + "秒前");
    }

    public static String formatDataTime(long timeMilliseconds) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = sDateFormat.format(timeMilliseconds);
            return date;
        } catch (Exception e) {
            return getData();
        }
    }

    /**
     * 获取当前日期
     */
    public static String getData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取当前时间是否大于12：30
     */
    public static boolean isRightTime() {
        // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int hour = t.hour; // 0-23
        int minute = t.minute;
        return hour > 12 || (hour == 12 && minute >= 30);
    }

    /**
     * 得到上一天的时间
     */
    public static ArrayList<String> getLastTime(String year, String month, String day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));//月份是从0开始的，所以11表示12月

        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int inDay = ca.get(Calendar.DATE);
        ca.set(Calendar.DATE, inDay - 1);

        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(ca.get(Calendar.YEAR)));
        list.add(String.valueOf(ca.get(Calendar.MONTH) + 1));
        list.add(String.valueOf(ca.get(Calendar.DATE)));
        return list;
    }

    public static Date getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        try {
            return sDateFormat.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 比较日期与当前日期的大小
     */
    public static boolean DateCompare(String s1) throws ParseException {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(getData());
        //比较
        if (((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000)) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean DateCompare(String data1, String data2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = null;
        try {
            d1 = sdf.parse(data1);
        } catch (ParseException e) {
            return false;
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(data2);
        } catch (ParseException e) {
            return true;
        }
        //比较
        if (((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000)) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String timeFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf1.format(date);
    }

    public static String timeFormatStr(String time) {
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(date);
    }

    public static String timeFormatYYYYMMDD(String time) {
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(date);
    }

    public static String getInterval(String createtime) { // 传入的时间格式必须类似于2012-8-21 17:53:20这样的格式
        String interval = null;

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date d1 = (Date) sd.parse(createtime, pos);

        // 用现在距离1970年的时间间隔new
        // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒

        if (time / 1000 < 10 && time / 1000 >= 0) {
            // 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒[0秒,10秒)
            interval = "刚刚";

        } else if (time / 1000 < 60 && time / 1000 >= 10) {
            // 如果时间间隔小于60秒则显示多少秒前  [10秒-60秒)
            int se = (int) ((time % 60000) / 1000);
            interval = se + "秒前";

        } else if (time / 60000 < 60 && time / 60000 >= 1) {
            // 如果时间间隔小于60分钟则显示多少分钟前  [1分钟，60分钟)
            int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
            interval = m + "分钟前";

        } else if (time / 3600000 < 24 && time / 3600000 >= 1) {
            // 如果时间间隔小于24小时则显示多少小时前 [1小时，24小时)
            int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
            interval = h + "小时前";

        } else {
            // 大于24小时，则显示正常的时间，但是不显示秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            ParsePosition pos2 = new ParsePosition(0);
            Date d2 = (Date) sdf.parse(createtime, pos2);

            interval = sdf.format(d2);
        }
        return interval;

    }
}