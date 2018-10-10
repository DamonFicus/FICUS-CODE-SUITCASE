package ficus.suitcase.dateutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DamonFicus on 2018/10/10.
 * 时间工具;
 * 如果org.apache.commons.lang3.time.DateUtils;包中能够满足的
 * 尽量使用lang3包的工具控件；
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 方法说明：比较两个时间相差分钟数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getIntervalMins(Date startDate, Date endDate)
    {
        int intervalMins = 0;
        try{
            long len = endDate.getTime() - startDate.getTime();
            intervalMins = (int) (len / ( 60 * 1000));
        }catch (Exception e){
            logger.error("时间换算异常",e);
        }
        return intervalMins;
    }



    /**
     * 返回指定日期的月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }


    /**
     * 返回指定日期的月的最后一天
     * @param date
     * @return date
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 日期加减指定天数
     * @param date
     * @param dd  天数
     */
    public static Date addDay(Date date,int dd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,dd);
        return calendar.getTime();
    }

    /**
     * 获取到当天24点的小时数。
     * @return
     */
    public static int getHoursToDayEnd(){
        int iRet = 0;
        Date currDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        iRet = 24 - hour;
        return iRet;
    }

    /**
     * 获取星期  从1到7
     * @return
     */
    public static String getWeekNum(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sw = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String weekDayDesc="";
        switch (sw==0?7:sw){
            case 1:weekDayDesc= "星期一"; break;
            case 2:weekDayDesc= "星期二"; break;
            case 3:weekDayDesc= "星期三"; break;
            case 4:weekDayDesc= "星期四"; break;
            case 5:weekDayDesc= "星期五"; break;
            case 6:weekDayDesc= "星期六"; break;
            case 7:weekDayDesc= "星期七"; break;
            default:weekDayDesc= StringUtils.EMPTY;
        }
        return weekDayDesc;
    }

}
