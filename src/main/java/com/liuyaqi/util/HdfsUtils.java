package com.liuyaqi.util;

import com.liuyaqi.common.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * hdfs工具类
 *
 * @author liuyaqi
 */
public class HdfsUtils {
    /**
     * 文件上传
     *
     * @param localFile 本地文件地址，如 D:\git\liuyaqi-project\data\data.csv
     * @param hdfsFile  hdfs地址，如data/data.csv
     * @throws IOException
     */
    public static void fileUpload(String localFile, String hdfsFile) throws IOException {
        // 创建HDFS连接对象client
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", Constants.hdfsUrl);
        FileSystem fileSystem = FileSystem.get(conf);
        // 创建HDFS的输入流
        InputStream input = new FileInputStream(localFile);
        // 创建HDFS的输出流
        OutputStream output = fileSystem.create(new Path(hdfsFile));
        // 写文件到HDFS
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        // 防止输出数据不完整
        output.flush();
        // 使用工具类IOUtils上传或下载
        input.close();
        output.close();
    }

    public static void downloadFileToHbase(String hdfsFile) throws URISyntaxException, IOException {
        // 创建HDFS连接对象client
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", Constants.hdfsUrl);
        FileSystem fileSystem = FileSystem.get(conf);

        // 第一个参数，y要下载的HDFS文件路径
        // 第二个参数，下载到本机的目录（不是虚拟机的主机）
        // Path(String str)，路径类
        Path remotePath = new Path(hdfsFile);
        FSDataInputStream fsDataInputStream = fileSystem.open(remotePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line = null;
        HbaseUtils hbaseUtils = new HbaseUtils();
        hbaseUtils.init();
        List<Put> allData = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] strArray = line.split(",");
            for (int i = 1; i < strArray.length; i++) {
                String dateTime = strArray[1].replaceAll("-07:00", "");
                allData.add(hbaseUtils.oneData("travel", dateTime, "number_people", "", strArray[0]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "date", dateTime));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "day_of_week", strArray[3]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "is_weekend", strArray[4]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "is_holiday", strArray[5]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "temperature", strArray[6]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "is_start_of_semester", strArray[7]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "is_during_semester", strArray[8]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "month", strArray[9]));
                allData.add(hbaseUtils.oneData("travel", dateTime, "factor", "hour", strArray[10]));
            }
        }
        hbaseUtils.insertBatch("travel", allData);
        hbaseUtils.close();
        bufferedReader.close();
        fsDataInputStream.close();
        fileSystem.close();
    }

}
