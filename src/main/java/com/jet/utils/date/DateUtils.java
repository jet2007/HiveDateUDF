package com.jet.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jet.utils.string.StringUtils;


public class DateUtils {

    /**
     * @param date      date
     * @param formatStr exg. yyyyMMddHHmmss,yyyy-MM-dd HH:mm:ss
     * @return date format String
     */
    public static String getFormatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String formatDate = sdf.format(date);
        return formatDate;
    }

    /**
     * @param dateStr date format String
     * @param format  exg. yyyyMMddHHmmss,yyyy-MM-dd HH:mm:ss
     * @return date type
     */
    public static Date parse(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 仿python relativedelta实现日期相加操作,返回String
     *
     * @param dateStr    支持yyyyMMddHHmmss和yyyy-MM-dd HH:mm:ss
     * @param offsetsStr delta日期的单位与长度,分隔符分别为逗号与等号
     *                   示例： offsetsStr："year=+2,month=8,day=-16"  年份加2，月份为8，日期减16
     *                   单位支持有year,month,day,hour,minute,second(或y,m,d,h,n,s)；
     *                   长度支持+N,-N,N
     * @return 返回String的格式与源参数dateStr相似；
     * dateDeltaStr("20180102030405","day=+3")   "20180105030405"
     * dateDeltaStr("2018-01-02 03:04:05","day=+3")   "2018-01-05 03:04:05"
     * dateDeltaStr("20180102","day=+3")   "20180105"
     * dateDeltaStr("2018-01-02","day=+3")   "2018-01-05"
     */
    public static String dateDeltaStr(String dateStr, String offsetsStr) {
        return dateDeltaStr(dateStr, offsetsStr, null);

    }


    /**
     * 获取日期的当周的日期值；示例2020-12-17的周一为2020-12-14
     *
     * @param dt        输入日期
     * @param dayOfWeek 周几(一周第一天为周一；周一为1)
     * @return 输出日期：获取日期的当周的日期值
     */
    public static Date getDateByDayofWeekChina(Date dt, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1); // 星期天
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, dayOfWeek - 1);
        return cal.getTime();

    }


    /**
     * 仿python relativedelta实现日期相加操作,返回String
     * 示例： offsetsStr：[year=+2, month=8, day=-16]  年份加2，月份为8，日期减16
     *
     * @param dateStr    支持yyyyMMddHHmmss和yyyy-MM-dd HH:mm:ss
     * @param offsetsStr delta日期的单位与长度,分隔符分别为逗号与等号
     * @param formatStr  date format string exg.yyyyMMddHHmmss,yyyy-MM-dd HH:mm:ss
     *                   单位支持有year,month,day,hour,minute,second；
     *                   长度支持+N,-N,N
     *                   示例有year=+2, month=8, day=-16
     */
    public static String dateDeltaStr(String dateStr, String offsetsStr, String formatStr) {
        Date dt = relativedelta(dateStr, offsetsStr);
        if (StringUtils.isBlank(formatStr)) {
            String ds = dateStr.replace(" ", "").replace("-", "").replace(":", "");
            boolean f2 = dateStr.contains(" ") || dateStr.contains(":") || dateStr.contains("-"); // 包含[:-空格]任何一个
            if (ds.length() == 8) {//日期或日期+时间格式
                if (f2) {
                    formatStr = "yyyy-MM-dd";
                } else {
                    formatStr = "yyyyMMdd";
                }
            } else {
                if (f2) {
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                } else {
                    formatStr = "yyyyMMddHHmmss";
                }
            }
        }
        return getFormatDate(dt, formatStr);
    }

    /**
     * 仿python relativedelta实现日期相加操作
     * 示例： offsetsStr：[year=+2, month=8, day=-16]  年份加2，月份为8，日期减16
     *
     * @param dateStr    支持yyyyMMddHHmmss和yyyy-MM-dd HH:mm:ss
     * @param offsetsStr delta日期的单位与长度,分隔符分别为逗号与等号
     *                   单位支持有year,month,day,hour,minute,second；
     *                   长度支持+N,-N,N
     *                   示例有year=+2, month=8, day=-16
     */
    public static Date relativedelta(String dateStr, String offsetsStr) {
        String ds = dateStr.replace(" ", "").replace("-", "").replace(":", "");
        if (ds.length() == 8) {
            ds = ds + "000000";
        }
        Date date = parse(ds, "yyyyMMddHHmmss");
        return relativedelta(date, offsetsStr);
    }


    /**
     * 仿python relativedelta实现日期相加操作
     * 示例： offsetsStr：[year=+2, month=8, day=-16]  年份加2，月份为8，日期减16
     *
     * @param date       日期类型
     * @param offsetsStr delta日期的单位与长度,分隔符分别为逗号与等号
     *                   单位支持有year,month,day,hour,minute,second；
     *                   长度支持+N,-N,N
     *                   示例有year=+2, month=8, day=-16
     *                   特殊(单个日期差)：-1,+1,1等价于day=-1,day=+1,day=1
     */
    public static Date relativedelta(Date date, String offsetsStr) {
        if (StringUtils.isNotBlank(offsetsStr)) {
            String[] offsets = offsetsStr2Array(offsetsStr);
            return relativedelta(date, offsets);
        } else {
            return date;
        }
    }


    public static String[] offsetsStr2Array(String offsetsStr) {
        String[] offsets = null;
        String reg1 = "^[+|-]\\d+$";


        if (offsetsStr.contains(",")) {
            // d+1,m=1,y-1  ==>  d=+1,m=1,y=-1
            offsets = offsetsStr.split(",");
            for (int i = 0; i < offsets.length; i++) {
                String ele = offsets[i];
                if ((ele.contains("+") || ele.contains("-")) && !ele.contains("=")) {
                    // d+1 ,y-1 ==>  d=+1, ,y=-1
                    String ele2 = ele.replace("+", "=+").replace("-", "=-");
                    offsets[i] = ele2;
                }
            }
        }else if(offsetsStr.contains("=")){
            // d=1
            offsets=offsetsStr.split(",");
        }else if(offsetsStr.startsWith("+") || offsetsStr.startsWith("-")){
            // +1 或 -1  ==>   day=+1, day=-1
            offsets = ("d=" + offsetsStr).split(",");
        }else if(offsetsStr.matches("\\d+")){
            // 1  ==> day=1
            offsets = ("d=" + offsetsStr).split(",");
        }else {
            // d-1  ==> d-1
            String ele2 = offsetsStr.replace("+", "=+").replace("-", "=-");
            offsets=ele2.split(",");
            System.out.println("##############");
        }
        return offsets;
    }


    /**
     * 仿python relativedelta实现日期相加操作
     * 示例： offsets：[year=+2, month=8, day=-16]  年份加2，月份为8，日期减16
     *
     * @param date    日期类型
     * @param offsets delta日期的单位与长度。
     *                单位支持有year,month,day,hour,minute,second；
     *                长度支持+N,-N,N
     *                示例有year=+2, month=8, day=-16
     */
    public static Date relativedelta(Date date, String... offsets) {
        if (offsets == null || offsets.length == 0) {
            return date;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            for (int i = 0; i < offsets.length; i++) {
                String[] offset = offsets[i].trim().split("=");
                int field = offsetUnit(offset[0].trim());
                String value = offset[1].trim();

                //包含+或-号，如d=+3,d=-2
                if (value.contains("+") || value.contains("-")) {
                    if (field != Calendar.DAY_OF_WEEK) {// 不处理周内的加减；只处理年月日时分秒的加减
                        cal.add(field, Integer.parseInt(value));
                    }
                } else {//只有=号，如d=3,m=1
                    int offInput = Integer.parseInt(value);
                    if (field == Calendar.MONTH) { // 月
                        cal.set(field, offInput - 1);
                    } else if (field == Calendar.DAY_OF_WEEK) { // 周几的赋值
                        Date d1 = getDateByDayofWeekChina(cal.getTime(), offInput);
                        cal.setTime(d1);
                    } else { //处理年日时分秒
                        cal.set(field, offInput);
                    }
                }
            }
            return cal.getTime();
        }
    }

    /**
     * year,month,day,hour,minute,second字符串转化Calendar的常量INT类型
     *
     * @param offsetStr
     * @return
     */
    public static int offsetUnit(String offsetStr) {
        if (offsetStr.equals("year") || offsetStr.equals("y")) {
            return Calendar.YEAR;
        } else if (offsetStr.equals("month") || offsetStr.equals("m")) {
            return Calendar.MONTH;
        } else if (offsetStr.equals("day") || offsetStr.equals("d")) {
            return Calendar.DAY_OF_MONTH;
        } else if (offsetStr.equals("hour") || offsetStr.equals("h")) {
            return Calendar.HOUR;
        } else if (offsetStr.equals("minute") || offsetStr.equals("n")) {
            return Calendar.MINUTE;
        } else if (offsetStr.equals("second") || offsetStr.equals("s")) {
            return Calendar.SECOND;
        } else if (offsetStr.equals("week") || offsetStr.equals("w")) {
            return Calendar.DAY_OF_WEEK;
        } else {
            return -1;
        }
    }



}
