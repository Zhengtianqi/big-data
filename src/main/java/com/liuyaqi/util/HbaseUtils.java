package com.liuyaqi.util;

import com.liuyaqi.common.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hbase的操作
 */
public class HbaseUtils {
    /**
     * 管理Hbase的配置信息
     */
    public static Configuration configuration;
    /**
     * 管理Hbase连接
     */
    public static Connection connection;
    /**
     * 管理Hbase数据库的信息
     */
    public static Admin admin;

    private static final Logger logger = Logger.getLogger("HbaseApplication");

    /**
     * hbase建立连接
     */
    public void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", Constants.hbaseUrl);
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 操作hbase数据库之后，关闭连接
     */
    public void close() {
        try {
            if (admin != null) {
                // 退出用户
                admin.close();
            }
            if (null != connection) {
                // 关闭连接
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     *
     * @param myTableName 表名
     * @param colFamily   列族名
     * @throws IOException
     */
    public void createTable(String myTableName, String[] colFamily) throws IOException {
        TableName tableName = TableName.valueOf(myTableName);
        if (admin.tableExists(tableName)) {
            logger.severe("Table exists");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String str : colFamily) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
    }

    /**
     * 添加单元格数据
     *
     * @param tableName 表名
     * @param rowKey    行键
     * @param colFamily 列族
     * @param col       列限定符
     * @param val       数据
     * @return
     * @thorws Exception
     */
    public Put oneData(String tableName, String rowKey, String colFamily, String col, String val) throws IOException {
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        return put;
    }

    public void insertBatch(String tableName, List<Put> allData) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        table.put(allData);
        table.close();
        table.close();
    }

    /**
     * 浏览数据
     *
     * @param tableName 表名
     * @param rowKey    行
     * @param colFamily 列族
     * @param col       列限定符
     * @throw IOException IO异常
     */
    public void getData(String tableName, String rowKey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(), col.getBytes());
        Result result = table.get(get);
        System.out.println(new String(result.getValue(colFamily.getBytes(), col.getBytes())));
        table.close();
    }

    /**
     * 获取原始数据
     *
     * @param tableName 表名称
     * @return 返回数据
     */
    public ResultScanner getNoDealData(String tableName) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            return  table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除表
     */
    public void deleteTable(String tableName) {

        try {
            TableName tablename = TableName.valueOf(tableName);
            admin = connection.getAdmin();
            admin.disableTable(tablename);
            admin.deleteTable(tablename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
