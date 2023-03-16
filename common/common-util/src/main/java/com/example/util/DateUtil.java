package com.example.util;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenweida on 2017/5/19.
 */
public class DateUtil {

    public static final String yyyy_MM_dd_HH_mm_ss="yyyy-MM-dd HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YY = "yy";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_M_D_HH_MM_SS = "yyyy/M/d HH:mm:ss";
    public static final String YYYY_MM_DD_ = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_SS_ = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYYMMddHHmmssSSS  = "yyyyMMddHHmmssSSS";
    public static final String YYYY_MM ="yyyy-MM";
    public static final String YYYYMMDD_HH_MM_SS = "yyyyMMdd HH:mm:ss";

    public static Date dateTimeParse(String date) throws ParseException {
        return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).parse(date);
    }

    private static ThreadLocal<SimpleDateFormat> simpleDateTimeFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        }
    };

    private static ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(YYYY_MM_DD);
        }
    };

    public static Date simpleDateParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = simpleDateFormat.get().parse(date);
        return parsedDate;
    }

    public static String simpleDateTimeFormat(final Date date){
        String time = simpleDateTimeFormat.get().format(date);
        return time;
    }

    /**
     * 时间格式转中文格式
     */
    public static String dateToChinese(Date date) {
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy年MM月dd日 EEEEaaaa hh:mm", Locale.CHINA);
        return formatter.format(date);
    }

    public static String dateToChineseDate(Date date) {
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy年MM月dd日", Locale.CHINA);
        return formatter.format(date);
    }

    public static String dateToChineseTime(Date date) {
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        return formatter.format(date);
    }

    public static String dateToChineseTime2(Date date) {
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy年MM月dd日 hh:mm", Locale.CHINA);
        return formatter.format(date);
    }

    public static Date getNight23(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,22);
        return cal.getTime();
    }

    public static Date getToday(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }


    /**
     * 字符串转时间格式
     */
    public static Date strToDate(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        else{
            int length = strDate.length();
            if(strDate.contains("/"))
            {
                strDate = strDate.replace("/","-");
            }

            if(strDate.contains("-"))
            {
                if(length == 10)
                {
                    return strToDate(strDate,YYYY_MM_DD);
                }
                else if(length == 19)
                {
                    return strToDate(strDate,YYYY_MM_DD_HH_MM_SS);
                }
            }
            else{
                if(length == 8)
                {
                    return strToDate(strDate,YYYYMMDD);
                }
                else if(length == 14)
                {
                    return strToDate(strDate,YYYYMMDDHHMMSS);
                }
            }
        }

        return null;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateString, pos);
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        String dateString = formatter.format(currentTime);
        return strToDate(dateString, YYYY_MM_DD);
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDateUpper() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return formatter.format(currentTime);
    }

    public static String getYyyymmddhhmmss(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return formatter.format(date);
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(currentTime);
    }
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDatePre(String format,String days) {
        Date currentTime = new Date();
        long preTimes = currentTime.getTime()-24*60*60*1000;
        if (!StringUtils.isEmpty(days)){
            preTimes = currentTime.getTime()-Long.parseLong(days)*24*60*60*1000;
        }
        Date preDate = new Date(preTimes);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(preDate);
    }
    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        return formatter.format(currentTime);
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat(HH_MM_SS);
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyyMMdd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToYmdDateLong(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD_HH_MM_SS);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    public static Date strToDateShort(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return formatter.format(dateDate);
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrFormatLong(Date dateDate) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return formatter.format(dateDate);
    }
    public static String dateToStrNoSecond(Date dateDate) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        return formatter.format(dateDate);
    }
    /**
     * 将长时间格式时间转换为字符串 YYYYMMDD
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrFormatShort(Date dateDate) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD);
        return formatter.format(dateDate);
    }

    /**
     * 将长时间格式时间转换为字符串 yyyyMMdd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrShort(Date dateDate) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        return formatter.format(dateDate);
    }

    /**
     * 时间字符串转日期
     * @param dateDate
     * @return
     */
    public static String strToStrShort(String dateDate) {
        if (dateDate == null) {
            return "";
        }
        return dateToStrShort(strToDate(dateDate));
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     */
    public static String dateToStr(Date dateDate, String format) {
        if (dateDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(dateDate);
    }

    /**
     * 将短时间格式字符串转换为时间
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate, String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }



    public static Date strToDateAppendNowTime(String strDate, String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        strDate += " " + getTimeShort();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat
     *            yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(YYYY_MM_DD);
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 时间前推或后推天数（负数前推正数后推）
     * date 基准时间
     */
    public static Date getPreDays(Date date, int days) {
        Date day = null;
        try {
            Calendar c = Calendar.getInstance();

            c.setTime(date);
            c.add(Calendar.DATE, days);
            day = c.getTime();
        } catch (Exception e) {
        }
        return day;
    }

    /**
     * 得到一个时间延后或前移几分钟的时间,nowdate为时间,delay为前移或后延的分钟数
     */
    public static Date getNextMin(Date date, int delay) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, delay);
            return cal.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextMin(String nowdate, int delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(HH_MM);
            String mdate = "";
            Date d = strToDate(nowdate, HH_MM);
            long myTime = (d.getTime() / 1000) + delay * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNextMinute(String nowdate, int delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            String mdate = "";
            Date date = strToDate(nowdate, YYYY_MM_DD_HH_MM_SS);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, delay);
            mdate = format.format(cal.getTime());
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, int delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            String mdate = "";
            Date d = strToDate(nowdate, YYYY_MM_DD);
            long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToString(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(String s){
        long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }

    public static String getNextMinute(int minutes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minutes);
        return dateToStrLong(c.getTime());
    }

    public static String getNextDay(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        return dateToStrShort(c.getTime());
    }

    public static Date getNextDay1(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getNextWeek(Date d, int weeks) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_WEEK, weeks);
        return c.getTime();
    }

    public static String getNextMonth(Date d, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, months);
        return dateToStrShort(c.getTime());
    }

    public static Date getNextMonthReturnDate(Date d, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    public static String getNextYear(Date d, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.YEAR, year);
        return dateToStrShort(c.getTime());
    }

    /**
     * 获取本月第一天
     * @return
     */
    public static String getCurMonthFirstDayShort(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return dateToStrShort(c.getTime());
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate, YYYY_MM_DD);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0){
            return true;
        } else if ((year % 4) == 0) {
            if ((year % 100) == 0){
                return false;
            } else{
                return true;
            }
        } else{
            return false;
        }
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }


    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1){
            week = "0" + week;
        }
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = DateUtil.strToDate(sdate, YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")){ // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        else if (num.equals("2")){ // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        }
        else if (num.equals("3")){ // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        }
        else if (num.equals("4")){ // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        }
        else if (num.equals("5")){ // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }
        else if (num.equals("6")){ // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }
        else if (num.equals("0")){ // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDate(sdate, YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static String getWeekStr(String sdate) {
        String str = "";
        str = DateUtil.getWeek(sdate);
        if ("1".equals(str)||"Sunday".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)||"Monday".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)||"Tuesday".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)||"Wednesday".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)||"Thursday".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)||"Friday".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)||"Saturday".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 日期比较大小
     * @param s1
     * @param s2
     * @return
     */
    public static long compareDate(Date s1, Date s2) {
        try {
            return compareDate(YYYY_MM_DD, dateToStrShort(s1), dateToStrShort(s2));
        } catch (Exception e) {
            return -1;
        }

    }

    /**
     * 字符串日期比较大小
     * @param format
     * @param s1
     * @param s2
     * @return
     */
    public static long compareDate(String format, String s1, String s2) {

        SimpleDateFormat f = new SimpleDateFormat(format);

        try {
            return f.parse(s1).getTime() - f.parse(s2).getTime();
        } catch (Exception e) {
            return -1;
        }

    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if(date1 == null || date1.equals("")){
            return 0;
        }
        if(date2 == null || date2.equals("")){
            return 0;
        }
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(YYYY_MM_DD);
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 返回两个日期相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(Date date1, Date date2) {
        if (date1 == null || date2 == null){
            return 0;
        }
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 返回两个日期相差的小时数(保留2位小数)
     * @param date1
     * @param date2
     * @return
     */
    public static float getHours(Date date1, Date date2) {
        if (date1 == null || date2 == null){
            return 0;
        }
        float hours = (date1.getTime() - date2.getTime()) / (float)(60 * 60 * 1000);
        BigDecimal decimal = new BigDecimal(hours);
        float hour = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return hour;
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param sdate
     * @return
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";
        // 得到这个月的1号是星期几
        Date date = DateUtil.strToDate(sdate, YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = DateUtil.getNextDay(sdate, 1 - u);
        return newday;
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */
    public static String getNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 取得流水号 生成格式为yyyymmddhhmmss+k位随机UUID
     *
     * @param k 表示是取几位随机数，可以自己定
     */
    public static String getUidNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + UUID.randomUUID().toString().replaceAll("-", "").substring(0, k);
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        if(i == 0){
            return "";
        }
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        try {
            int age = 0;

            if (birthday == null) {
                return age;
            }

            String birth = new SimpleDateFormat("yyyyMMdd").format(birthday);

            int year = Integer.valueOf(birth.substring(0, 4));
            int month = Integer.valueOf(birth.substring(4, 6));
            int day = Integer.valueOf(birth.substring(6));
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;
            //周岁计算
            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }

            return age;
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     *  字符串转时间
     * @param str 时间字符串
     * @param eg 格式
     * @return
     */
    public static Date stringToDate(String str, String eg) {
        DateFormat format = new SimpleDateFormat(eg);
        Date date = null;
        if (str != null && !"".equals(str)) {
            try {
                date = format.parse(str);
            } catch (Exception e) {
                return null;
            }
        }
        return date;
    }

    public static int getNowMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH)+1;
    }

    public static int getNowYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取周一
     * @return
     */
    public static String getMondayOfThisWeek() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        return df2.format(c.getTime());
    }

    /**
     * 获取某个时间的周一
     * @returnc
     */
    public static String getMondayOfThisWeek(Date date) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        return df2.format(c.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 7);
        return df2.format(c.getTime());
    }

    /**
     * 获取某个时间的周末
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek(Date date) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 7);
        return df2.format(c.getTime());
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static String getFristDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return format.format(c.getTime());
    }
    /**
     * 获取当月最后一天
     */
    public static String getLastDayOfMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime());
    }

    public static int getSignYear(){
        Calendar ca = Calendar.getInstance();
        if(getNowMonth()>=7){
            return getNowYear();
        }
        return getNowYear()-1;
    }

    /**
     * 计算预产期
     * 末次月经开始日期(第一天)，月份+9，日期+7
     * @param date
     * @return
     */
    public static Date getDueDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,9);
        cal.add(Calendar.DAY_OF_YEAR,7);
        return cal.getTime();
    }

    /**
     * 计算产检时间
     * @param date
     * @param day
     * @return
     */
    public static Date getPrenatalInspectorDate(Date date,Integer day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,day);
        return cal.getTime();
    }

    /**
     * 将HH:MM格式的字符串转TIME
     * @param hhmm
     * @return
     */
    public static Time hhmmStrToTime(String hhmm){
        Time time = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date = sdf.parse(hhmm);
            time = new Time(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;

    }

    /**
     * 获取当前时间的Timestamp
     * @return
     */
    public static Timestamp getNowTimestamp(){
        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        return nousedate;
    }

    /**
     *时间字符串转为 时间戳
     * @param str
     * @param format
     * @return
     */
    public static Timestamp toTimestamp(String str, String format) {

        if (str == null) {
            return null;
        }

        try {
            return new Timestamp(stringToDate(str, format).getTime());
        } catch (Exception e) {
            return null;
        }

    }
    /**
     *时间字符串转为 时间戳
     * @param str
     * @param
     * @return
     */
    public static Timestamp toTimestamp(String str) {

        if (str == null) {
            return null;
        }

        try {
            return Timestamp.valueOf(str.trim());
        } catch (IllegalArgumentException iae) {
            return null;
        }

    }

    public static long compareDateTime(Date s1, Date s2) {
        return s1.getTime() - s2.getTime();
    }

    /**
     *  日期加减天数
     * @param date 时间
     * @param days 天数�?
     * @return
     */
    public static Date setDateTime(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) +(days));
        return  cal.getTime();
    }

    public static Date setDateHours(Date date, int hours){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) +(hours));
        return  cal.getTime();
    }

    /**
     * 获取去年日期
     * @return
     */
    public static String getLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,-1);
        return dateToStrLong(cal.getTime());
    }

    /**
     * 获取儿童的年龄
     * @param date
     * @return
     */
    public static String getChildAge(Date date){
        Calendar now = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = now.get(Calendar.YEAR)-cal.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH)-cal.get(Calendar.MONTH);
        if(month<0){
            year--;
            month+=12;
        }
        int day = now.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH);
        if(day<0){
            month--;
            Calendar temp = Calendar.getInstance();
            temp.setTime(date);
            temp.add(Calendar.MONTH,1);
            temp.set(Calendar.DAY_OF_MONTH,1);
            temp.add(Calendar.DAY_OF_MONTH,-1);
            day = temp.get(Calendar.DAY_OF_MONTH)+now.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH);
        }
        String y = year>0? year+"岁":"";
        String m = month>0? month+"月":"";
        String d = day>0? day+"天":"";
        return y+m+d;
    }

    public static int getWeekOfMonth(String dateString){
        Date date = strToDate(dateString);

        return getWeekOfMonth(date);
    }

    public static int getWeekOfMonth(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        return weekOfMonth;
    }

    //将时间维度查出来的quotaDate转成yyyy-MM
    public static String changeQuotaDate(Date quotaDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String  date = sdf.format(quotaDate);
        return date;
    }

    public static String getYear(String strDate, String format){
        Date date = strToDate(strDate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR)+"";
    }

    public static String getMonth(String dateString){

        Date date = strToDate(dateString);

        return new SimpleDateFormat(YYYY_MM).format(date);
    }

    /**
     * 获取某个时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getDateShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        String dateString = formatter.format(date);
        return strToDate(dateString, YYYY_MM_DD);
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static String getFristDayOfMonthThisDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return format.format(c.getTime());
    }
    /**
     * 获取当月最后一天
     */
    public static String getLastDayOfMonthThisDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime());
    }

    /**
     * 根据日期对象返回星期几
     * @param date
     * @return
     */
    public static int getWeekByDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * 获取当天0点
     * @return
     */
    public static Date getDateStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获取当天23:59:59
     * @return
     */
    public static Date getDateEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date zero = calendar.getTime();
        return zero;
    }

    public static void main(String[] args) {
        System.out.println(dateToWeek("2022-10-31"));
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的年龄
     *
     * @param
     * @throws Exception
     */
    public static int getAgeForIdcard(String idcard) {
        try {
            int age = 0;

            if (org.apache.commons.lang3.StringUtils.isNotBlank(idcard)) {
                return age;
            }

            String birth = "";

            if (idcard.length() == 18) {
                birth = idcard.substring(6, 14);
            } else if (idcard.length() == 15) {
                birth = "19" + idcard.substring(6, 12);
            }

            int year = Integer.valueOf(birth.substring(0, 4));
            int month = Integer.valueOf(birth.substring(4, 6));
            int day = Integer.valueOf(birth.substring(6));
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;
            //周岁计算
            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }

            return age;
        } catch (Exception e) {

        }
        return -1;
    }

    public static List<Map<String,Object>> findDates(Date dBegin, Date dEnd) {
        List<Map<String,Object>> lDate = new ArrayList();
        Map<String,Object> st = new HashedMap();
        st.put("date",DateUtil.dateToStr(dBegin,"yyyy-MM-dd"));
        st.put("avg",0);
        st.put("count", 0);
        lDate.add(st);

        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);

        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);

        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            if(!dEnd.after(calBegin.getTime())){
                break;
            }
            Map<String,Object> stt = new HashedMap();
            stt.put("date",DateUtil.dateToStr(calBegin.getTime(),"yyyy-MM-dd"));
            stt.put("avg",0);
            stt.put("count", 0);
            lDate.add(stt);
        }
        return lDate;
    }

    public static String getDateFromDateTime(String dateTime) {

        if (dateTime == null || dateTime.length() < YYYY_MM_DD.length()) {
            return null;
        }

        return dateTime.substring(0, 10);
    }

    /**
     * 判断时间是否在某个区间内
     * @param time
     * @param begin
     * @param end
     * @return
     */
    public static boolean isInArea(Date time,Date begin,Date end) {
        Long beginTime = DateUtil.compareDateTime(time,begin);
        Long endTime = DateUtil.compareDateTime(time, end);
        if (beginTime > 0 && endTime < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  返回 xx天XX小时XX分钟XX秒前
     * @param date1 当前时间
     * @param date2 过去时间
     * @param fliter 0不携带秒 1携带秒
     * @return
     */
    public static String getDifferentTimeInfo(Date date1, Date date2,Integer fliter){
        if (date1 == null || date2 == null){
            return null;
        }
        StringBuilder result = new StringBuilder("");
        long millisecondsDiff = date1.getTime() - date2.getTime();
        long secondsDiff = millisecondsDiff / TimeUnit.SECONDS.toMillis(1L);
        long minutesDiff = millisecondsDiff / TimeUnit.MINUTES.toMillis(1L);
        long hoursDiff = millisecondsDiff / TimeUnit.HOURS.toMillis(1L);
        long daysDiff = millisecondsDiff / TimeUnit.DAYS.toMillis(1L);
        long hourFieldDiff = hoursDiff - TimeUnit.DAYS.toHours(daysDiff);
        long minuteFieldDiff = minutesDiff - TimeUnit.HOURS.toMinutes(hoursDiff);
        long secondFieldDiff = secondsDiff - TimeUnit.MINUTES.toSeconds(minutesDiff);
        if (daysDiff > 0L) {
            result.append(String.format("%d天", daysDiff));
            result.append("前");
            return result.toString();
        }

        if (hourFieldDiff > 0L) {
            result.append(String.format("%d小时", hourFieldDiff));
        }
        if (minuteFieldDiff > 0L) {
            result.append(String.format("%d分钟", minuteFieldDiff));
            if (result.indexOf("小时")>0){
                result.append("前");
                return result.toString();
            }
        }
        if (1==fliter){
            if (secondFieldDiff > 0L) {
                result.append(String.format("%d秒", secondFieldDiff));
            }
        }else {
            if (result.indexOf("分钟")==-1){
                result.append("1分钟前");
                return result.toString();
            }
        }
        result.append("前");
        return result.toString();
    }

    /**
     *  返回 两个时间间隔天数/小时数
     * @param date1 当前时间
     * @param date2 过去时间
     * @return
     */
    public static String getDifferentTimeInfo1(Date date1, Date date2){
        if (date1 == null || date2 == null){
            return null;
        }
        String result = null;
        long millisecondsDiff = date1.getTime() - date2.getTime();
        long secondsDiff = millisecondsDiff / TimeUnit.SECONDS.toMillis(1L);
        long minutesDiff = millisecondsDiff / TimeUnit.MINUTES.toMillis(1L);
        long hoursDiff = millisecondsDiff / TimeUnit.HOURS.toMillis(1L);
        long daysDiff = millisecondsDiff / TimeUnit.DAYS.toMillis(1L);
        long hourFieldDiff = hoursDiff - TimeUnit.DAYS.toHours(daysDiff);
        long minuteFieldDiff = minutesDiff - TimeUnit.HOURS.toMinutes(hoursDiff);
        long secondFieldDiff = secondsDiff - TimeUnit.MINUTES.toSeconds(minutesDiff);
        if (daysDiff > 0L) {
            result = (String.format("%d天", daysDiff));
            return result.toString();
        }
        else if (hourFieldDiff > 0L) {
            result=String.format("%d小时", hourFieldDiff);
        }
        return result;
    }


    /**
     * 1970年1月1日来的 秒转化成时间
     * @param seconds
     * @return
     */
    public static Date secondTransfor(int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,Calendar.JANUARY,1,0,0,0);
        calendar.add(Calendar.SECOND,seconds);
        return calendar.getTime();
    }

    /**
     * 获取指定时间和当前时间的距离
     * 几秒前、几分钟前、几小时前、几天前
     * @param l1 秒级时间戳
     * @return
     */
    public static String getTimeAgeStr(Long l1){
        String timeAgoStr = "";
        BigDecimal b1 = new BigDecimal(l1);
        BigDecimal b2 = new BigDecimal(System.currentTimeMillis()/1000);
        BigDecimal subtract = b2.subtract(b1);//秒数
        if(subtract.compareTo(new BigDecimal(60))<0){//小于一分钟前
            timeAgoStr = subtract.toString()+"秒前";
        }else if(subtract.compareTo(new BigDecimal(3600))<0){//小于一小时
            timeAgoStr = subtract.divide(new BigDecimal(60),0,BigDecimal.ROUND_HALF_DOWN).toString()+"分钟前";
        }else if(subtract.compareTo(new BigDecimal(86400))<0){//小于一天
            timeAgoStr = subtract.divide(new BigDecimal(3600),0,BigDecimal.ROUND_HALF_DOWN).toString()+"小时前";
        }else {//超过一天
            timeAgoStr = subtract.divide(new BigDecimal(86400),0,BigDecimal.ROUND_HALF_DOWN).toString()+"天前";
        }
        return timeAgoStr;
    }

    /**
     * 获取指定时间的前后若干时间戳
     * list[0]前
     * list[1]后
     * @return
     */
    public static List<String> getTimeByBeforeAndAfterTime(String time,long l){
        List<String> list = new ArrayList<>();
        Date date = DateUtil.strToDate(time);
        Date beforeTime = new Date(date.getTime()-l);
        Date afterTime = new Date(date.getTime()+l);
        list.add(DateUtil.dateToStrLong(beforeTime));
        list.add(DateUtil.dateToStrLong(afterTime));
        return list;
    }
    /**
     * 计算当前时间和传入时间的相差
     * 秒数
     */
    public static Long behindSubBefore(String date){
        Date startDate = DateUtil.strToDate(date);
        Date currentDate = DateUtil.getNowDate();
        return (startDate.getTime() - currentDate.getTime()) / 1000;
    }

    public static int getWeekByString(String date){
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = simpleDateParse(date);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return w;
    }

    public static String dateToWeek(String date) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        return weekDays[getWeekByString(date)];
    }


}
