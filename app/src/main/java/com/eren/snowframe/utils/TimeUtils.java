package com.eren.snowframe.utils;

/**
 * Created by liht  on 2016/9/26 15:09.
 * Desc:
 */

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.Time;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeUtils {

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOURS = MINUTE * 60;
    public static final long DAY = HOURS * 24;

    public static boolean isToday(long time) {
        return isSameDay(time, System.currentTimeMillis());
    }

    public static boolean isSameDay(long time1, long time2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day1 = sdf.format(new Date(time1));
        String day2 = sdf.format(new Date(time2));
        return day1.equals(day2);
    }

    public static String formatTimeNormal(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal3(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal4(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal5(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal6(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal7(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal8(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal9(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal10(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal11(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal12(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal13(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        return sdf.format(new Date(when));
    }

    public static String formatTimeNormal14(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(new Date(when));
    }

    //字符串转时间戳
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getStrTime(timeStamp);
    }

    //时间戳转字符串
    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param cc_time
     * @return
     */
    public static String getStrTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        // 例如：
        re_StrTime = sdf.format(new Date(cc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static boolean isBiger(long time1, long time2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = sdf.parse(getStrTime(time1, "yyyy-MM-dd"));
            Date dt2 = sdf.parse(getStrTime(time2, "yyyy-MM-dd"));
           /* dt1 = sdf.parse(time1 + "");
            dt2 = sdf.parse(time2 + "");*/

            if (dt1.getTime() > dt2.getTime()) {
                isBigger = true;
            } else if (dt1.getTime() < dt2.getTime()) {
                isBigger = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isBigger;
    }

    public static String getStrTime(long cc_time, String format) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 例如：
        re_StrTime = sdf.format(new Date(cc_time * 1000L));
        return re_StrTime;
    }

    public static String duration(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            long noetime = (System.currentTimeMillis()) / 1000;//获取系统时间的10位的时间戳
            String str = String.valueOf(noetime);

            Date d1 = df.parse(getStrTime2(time));
            Date d2 = df.parse(getStrTime2(noetime));
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long sec = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
            return days + "天" + hours + "小时" + minutes + "分";
        } catch (Exception e) {

        }
        return "";
    }

    public static String getStrTime2(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：
        re_StrTime = sdf.format(new Date(cc_time * 1000L));
        return re_StrTime;
    }

    public static String getFormatDate(long timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ripeTime = "";

        try {
            Date d1 = longToDate(timestamp, "yyyy-MM-dd HH:mm:ss");
            Date d2 = longToDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            long day = diff / DAY;
            long hour = (diff - DAY * day) / HOURS;
            long minute = (diff - DAY * day - HOURS * hour) / MINUTE;
            long second = (diff - DAY * day - HOURS * hour - MINUTE * minute) / SECOND;


            ripeTime = String.format("%s天%s时%s分%s秒", day, hour, minute, second);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*if (day > 0) {
        } else if (hour > 0) {
            ripeTime = String.format(prefix + "%s时%s分%s秒", hour, minute, second);
        } else if (minute > 0) {
            ripeTime = String.format(prefix + "%s分%s秒", minute, second);
        } else {
            ripeTime = String.format(prefix + "%s秒", second);
        }*/
        return ripeTime;
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String formatCoverTime(long startTime, long stopTime) {
        int coverMinutes = 0;
        if (startTime <= stopTime) {
            long coverTime = stopTime - startTime;
            coverMinutes = (int) (coverTime / (60 * 1000));
        }
        if (coverMinutes < 24 * 60) {
            String returnString = (coverMinutes / 60) + "小时" + (coverMinutes % 60) + "分";
            return ((coverMinutes / 60) == 0 && (coverMinutes % 60) == 0) ? "0小时1分" : returnString;
        } else {
            int coverHour = (coverMinutes / 60);
            return (coverHour / 24) + "天" + (coverHour % 24) + "小时";
        }
    }

    public static String formatCoverTime2(long startTime, long stopTime) {
        int coverMinutes = 0;
        if (startTime <= stopTime) {
            long coverTime = stopTime - startTime;
            coverMinutes = (int) (coverTime / (60 * 1000));
        }
        if (coverMinutes < 24 * 60) {
            String returnString = (coverMinutes / 60) + "小时" + (coverMinutes % 60) + "分";
            return ((coverMinutes / 60) == 0 && (coverMinutes % 60) == 0) ? "0小时1分" : returnString;
        } else {
            int coverHour = (coverMinutes / 60);
            return (coverHour / 24) + "天" + (coverHour % 24) + "小时" + (coverMinutes % 60) + "分";
        }
    }

    public static String formatDuration(long duration) {
        DecimalFormat format = new DecimalFormat("00");
        int coverSeconds = 0;
        coverSeconds = (int) (duration / 1000);
        if (coverSeconds <= 1) {
            return "00:00:01";
        } else if (coverSeconds < 60) {
            return "00:00:" + format.format(coverSeconds);
        } else if (coverSeconds < 60 * 60) {
            return "00:" + format.format(coverSeconds / 60) + ":" + format.format(coverSeconds % 60);
        } else {
            int coverMinutes = coverSeconds / 60;
            return format.format(coverMinutes / 60) + ":" + format.format(coverMinutes % 60) + ":" + format.format(coverSeconds % 60);
        }
    }

    public static String formatDuration2(long duration) {
        DecimalFormat format = new DecimalFormat("00");
        int coverMinutes = 0;
        coverMinutes = (int) (duration / (1000 * 60));
        if (coverMinutes < 60) {
            return format.format(coverMinutes) + "分钟";
        } else if (coverMinutes < 60 * 24) {
            return format.format(coverMinutes / 60) + "小时" + format.format(coverMinutes % 60) + "分钟";
        } else {
            int coverHours = coverMinutes / 60;
            return format.format(coverHours / 24) + "天" + format.format(coverHours % 24) + "小时" + format.format(coverMinutes % 60) + "分钟";
        }
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatTimeNormal2(time);
        }
        return "1970-01-01";
    }

    public static String formatTimeNormal2(long when) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(when));
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String getWeekOfDate(long time) {

        String[] weekDays = {"Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat."};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String formatTime(Context context, long when, boolean showTimeIfNotToday) {

        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        StringBuilder patternBuilder = new StringBuilder("");

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            patternBuilder.append("yyyy年M月d日");
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append("H:mm");
            }
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            patternBuilder.append("M月d日");
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append("H:mm");
            }
        } else {
            // Otherwise, if the message is from today, show the time.
            patternBuilder.append("H:mm");
        }

        // If the caller has asked for full details, make sure to show the date
        // and time no matter what we've determined above (but still make showing
        // the year only happen if it is a different year from today).
        SimpleDateFormat sdf = new SimpleDateFormat(patternBuilder.toString());
        return sdf.format(new Date(when));
    }

    public static String formatTime2(Context context, long when, boolean showTimeIfNotToday) {

        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        StringBuilder patternBuilder = new StringBuilder("");

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            patternBuilder.append("yyyy年M月d日");
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append(systemFormatTime(context, when));
            }
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            if (then.yearDay + 1 == now.yearDay) {
                patternBuilder.append("昨天");
            } else {
                patternBuilder.append("M月d日");
            }
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append(systemFormatTime(context, when));
            }
        } else {
            // Otherwise, if the message is from today, show the time.
            patternBuilder.append(systemFormatTime(context, when));
        }

        // If the caller has asked for full details, make sure to show the date
        // and time no matter what we've determined above (but still make showing
        // the year only happen if it is a different year from today).
        SimpleDateFormat sdf = new SimpleDateFormat(patternBuilder.toString());
        return sdf.format(new Date(when));
    }

    public static String systemFormatTime(Context context, long when) {
        java.text.DateFormat dateFormat = DateFormat.getTimeFormat(context.getApplicationContext());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(when);
        String time = "\'" + dateFormat.format(new Date(when)) + "\'";
        if (calendar.get(Calendar.HOUR_OF_DAY) < 6 && calendar.get(Calendar.HOUR_OF_DAY) > 0) {
            time = time.replace("上午", "凌晨");
        }
        return time;
    }

    public static String formatTime3(Context context, long when, boolean showTimeIfNotToday) {

        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        StringBuilder patternBuilder = new StringBuilder("");

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            patternBuilder.append("yyyy/M/d");
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append(systemFormatTime(context, when));
            }
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            if (then.yearDay + 1 == now.yearDay) {
                patternBuilder.append("昨天");
            } else {
                patternBuilder.append("M/d");
            }
            if (showTimeIfNotToday) {
                patternBuilder.append(" ").append(systemFormatTime(context, when));
            }
        } else {
            // Otherwise, if the message is from today, show the time.
            patternBuilder.append(systemFormatTime(context, when));
        }

        // If the caller has asked for full details, make sure to show the date
        // and time no matter what we've determined above (but still make showing
        // the year only happen if it is a different year from today).
        SimpleDateFormat sdf = new SimpleDateFormat(patternBuilder.toString());
        return sdf.format(new Date(when));
    }

    public static boolean isSameYear(long when) {
        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();
        return then.year == now.year;
    }

    public static boolean isSameDay(Date day1, Date day2) {
        return day1.getYear() == day2.getYear()
                && day1.getMonth() == day2.getMonth()
                && day1.getDate() == day2.getDate();

    }

    /**
     * 判断day1 是不是day2的昨天
     *
     * @param day1
     * @return
     */
    public static boolean isYesterdayOf(Date day1, Date day2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(day1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(day2);
        d2.roll(Calendar.DAY_OF_MONTH, -1);
        return isSameDay(d1, d2);
    }

    public static boolean isSameDay(Calendar day1, Calendar day2) {
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)
                && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)
                && day1.get(Calendar.DAY_OF_MONTH) == day2.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getSevendate() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = "";
        boolean isNextMonth = false;
        int nextMonthDays = 1;
        for (int i = 0; i < 7; i++) {
            if (!isNextMonth) {
                mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            } else {
                mDay = String.valueOf(nextMonthDays);
            }
            if (Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), c.get(Calendar.MONTH) + 1)) {
                isNextMonth = true;
                mMonth = String.valueOf(c.get(Calendar.MONTH) + 2);
                nextMonthDays++;
                mDay = "1";
            }
            String date = mYear + "-" + (Integer.parseInt(mMonth) < 10 ? "0" + mMonth : mMonth) + "-" + mDay;
//            String date = mMonth + "月" + mDay + "日";
            dates.add(date);
        }
        return dates;
    }

    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
            if (format.parse(time).equals(format.parse(format.format(new Date())))) {
                return Week += "今天";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "周日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "周六";
        }
        return Week;
    }


}
