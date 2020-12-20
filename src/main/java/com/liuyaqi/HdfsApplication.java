package com.liuyaqi;

import com.liuyaqi.util.HbaseUtils;
import com.liuyaqi.util.HdfsUtils;


public class HdfsApplication {
    public static void main(String[] args) throws Exception {
//        HdfsUtils.fileUpload("D:\\git\\liuyaqi-project\\data\\data.csv", "data/data.csv");

//        HbaseUtils hbaseUtils = new HbaseUtils();
//        hbaseUtils.init();
//        hbaseUtils.createTable("travel", new String[]{"number_people", "factor"});
//        hbaseUtils.close();

        HdfsUtils.downloadFileToHbase("data/data.csv");


    }
}
