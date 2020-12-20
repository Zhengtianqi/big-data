package com.liuyaqi.common;

import java.util.regex.Pattern;

public class Constants {
    /**
     * hadoop连接
     */
    public static String hdfsUrl = "hdfs://127.0.0.1:9000";

    /**
     * hbase连接
     */
    public static String hbaseUrl = "127.0.0.1";

    /**
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
