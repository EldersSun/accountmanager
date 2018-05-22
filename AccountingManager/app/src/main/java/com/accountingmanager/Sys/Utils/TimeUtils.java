package com.accountingmanager.Sys.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**时间格式化工具
 * Created by Home-Pc on 2017/5/2.
 */

public class TimeUtils {
    public static String YYYYMMDD = "yyyy-MM-dd";

    public static String dateToYMD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
        String time = format.format(date);
        return time;
    }
}
