package com.app.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期类工具
 *
 */
public class DateUtil {

    /**
     * 日期转化为cron表达式
     * @param date
     * @return
     */
    public static String getCron(String  date) {
        String dateFormat="ss mm HH * * ?";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        try{
            return  DateUtil.fmtDateToStr(simpleDateFormat.parse(date), dateFormat);
        }catch (ParseException e){
            return "";
        }

    }

    /**
     * cron表达式转为日期
     * @param cron
     * @return
     */
    public static Date getCronToDate(String cron) {
        String dateFormat="ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * Description:格式化日期,String字符串转化为Date
     *
     * @param date
     * @param dtFormat
     *            例如:yyyy-MM-dd HH:mm:ss yyyyMMdd
     * @return
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串时间相加
     * 20221220 jjy
     */
    public static String strToDate(String date,int days){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        Date da = new Date();
        try{
            da = simpleDateFormat.parse(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(da);
        calendar.add(calendar.DATE, days);
        return simpleDateFormat.format(calendar.getTime());

    }
}
