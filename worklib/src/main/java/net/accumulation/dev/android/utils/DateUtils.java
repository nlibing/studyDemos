package net.accumulation.dev.android.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/4/3.
 */

public class DateUtils {
    public static String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    public static long getMillis(String dataStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = df.parse(dataStr);
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @return 当前月份【1月就是1】
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 从字符串中获取年份【字符串形式类似2017-09-29 xx(后面无所谓)】
     *
     * @param dataStr 日期时间字符串
     * @return 年份（0代表解析错误的年份）
     */
    public static int getYearFromString(String dataStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = df.parse(dataStr);
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从字符串中获取月份【字符串形式类似2017-09-29 xx(后面无所谓)】
     *
     * @param dataStr 日期时间字符串
     * @return 月份（1月就是1,0代表解析错误的月份）
     */
    public static int getMonthFromString(String dataStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = df.parse(dataStr);
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * @return 当前校历中当前的周数
     */
    public static int getWeek() {
        return 0;
    }


    /**
     * @return 今天是本周第几天（注：周一是1）
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;
        //Logger.e("日期，周" + dayOfWeek);
        return dayOfWeek;
    }

    /**
     * @return 本周包含的日期
     */
    public static int[] getDatesOfWeek() {
        int date[] = new int[7];
        Calendar calendar = Calendar.getInstance();
        int data = getDate(getCurrentMonth(), getCurrentYear());
        int newData = 1;
        for (int i = 0; i < 7; i++) {
            // calendar.add(Calendar.DATE, 1);
            if (data < calendar.get(Calendar.DATE) + i) {
                date[i] = newData;
                newData++;
            } else {
                date[i] = calendar.get(Calendar.DATE) + i;
            }

        }
        return date;
    }

    public static int getDate(int month, int year) {
        int date = 0;
        if (month == 1 || month == 3 || month == 5 ||
                month == 7 || month == 8 || month == 10 ||
                month == 12) {
            date=31;
            return date;
        } else if (month == 4 || month == 6 || month == 9 || month == 10) {
            date=30;
            return date;
        } else  if(month==2){
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                date=29;
                return date;
            } else {
                date=28;
                return date;
            }
        }
        return date;
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


        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "周天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        if (str_num.length() == 13) {
            date = sdf.format(new Date(toLong(str_num)));
        }else {
            date = sdf.format(new Date(Long.parseLong(str_num)));
        }
        return date;
    }
    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }
    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     *                    DateUtils.getTimeStamp(" 2016-03-09 16:09:30","yyyy-MM-dd HH:mm:ss")/1000;
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String formatTime(long mss) {
        String DateTimes = null;
        long days = mss / (60  * 24);
        long hours =(mss-days*24*60)/60;
        long minutes = mss-hours*60-days*60*24;
        if (days > 0) {
            DateTimes = days + "天" + hours + "小时" + minutes + "分钟";
        } else if (hours > 0) {
            DateTimes = hours + "小时" + minutes + "分钟";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟";
        } else {
            DateTimes = minutes + "分钟";
        }

        return DateTimes;
    }
    public static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
            Log.d("getStringToDate", date.getTime() + "");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
