package com.accountingmanager.Sys.Utils;

/**
 * 数字工具
 * Created by Home-Pc on 2017/5/2.
 */

public class NumberUtils {
    //数值取反
    public static Double unAbsForDouble(Double num){
        if(Double.valueOf(num) > 0){
            return - num;
        } else {
            return num;
        }
    }

    public static String unAbsForString(Double num){
        return String.valueOf(unAbsForDouble(num));
    }
}
