package com.briup.Server.Impl;

import com.briup.Bean.Environment;
import com.briup.Server.DBStore;
import com.briup.Server.helper.DBhelper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;

/**
 * 入库模块实现
 */
public class DBStoreImpl implements DBStore {


    /**
     * 拼接字符串 sql，并将其写入数据库
     * @param col
     */
    public void saveEnvToDB(Collection<Environment> col) {

        System.out.println(col.size());
        for (Environment environment : col) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            Timestamp timestamp = environment.getGather_date();
            String tsStr = sdf.format(timestamp);
            // gather_date=2018-09-15 11:17:48.0

            String[] time = tsStr.split("\\-");
            String day = time[2];

            // 拼接 sql 语句
            String sql = "insert into T_DETAIL_" + day
                    + " values(?,?,?,?,?,?,?,?,?,?)";
            System.out.println(sql);
            DBhelper.InsertIntoDB(sql, environment);
        }
    }

    /**
     * 创建数据表操作
     */
    public static void CreateEvnDB(){
        for (int i = 1 ; i <= 31 ; i++) {
            String sql = "CREATE TABLE T_DETAIL_" + i
                    +"("
                    +"Name VARCHAR2(30),"
                    +"SrcID  VARCHAR2(30),"
                    +"DstID  VARCHAR2(30),"
                    +"DevID  VARCHAR2(30),"
                    +"SensorAddress VARCHAR2(30),"
                    +"Count  NUMBER,"
                    +"Cmd  NUMBER,"
                    +"Data  VARCHAR2(30),"
                    +"Status NUMBER,"
                    +"gather_date TIMESTAMP"
                    +")";
            DBhelper.ExecuteSql(sql);
        }

    }

    /**
     * 删除表操作
     */
    public static void DropEvnDB(){
        for (int i = 1 ; i <= 31 ; i++) {
            String sql = "Drop TABLE T_DETAIL_" + i;
            DBhelper.ExecuteSql(sql);
        }
    }

    public void init(Properties properties) {

    }

    public static void main(String[] args) {
//        DropEvnDB();
//        CreateEvnDB();

    }
}
