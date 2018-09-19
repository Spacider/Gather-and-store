package com.briup.Server.Impl;

import com.briup.Bean.Environment;
import com.briup.Server.DBStore;
import com.briup.Server.helper.DBhelper;
import com.briup.util.Impl.BackUpImpl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;

/**
 * 入库模块实现
 */
public class DBStoreImpl implements DBStore {

    private int count =1;

    @Override
    public void init(Properties properties) {

    }
    /**
     * 拼接字符串 sql，并将其写入数据库
     * @param col
     */
    public void saveEnvToDB(Collection<Environment> col) {
        Connection connection = DBhelper.getConnction();
        PreparedStatement ps = null;


        System.out.println(col.size());
        try {
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
//                System.out.println(sql);
//            DBhelper.InsertIntoDB(sql, environment);

                ps = connection.prepareStatement(sql);

                ps.setString(1,environment.getName());
                ps.setString(2,environment.getSrcID());
                ps.setString(3,environment.getDstID());
                ps.setString(4,environment.getDevID());
                ps.setString(5,environment.getSensorAddress());
                ps.setInt(6,environment.getCount());
                ps.setInt(7,environment.getCmd());
                ps.setString(8,environment.getData()+"");
                ps.setInt(9,environment.getStatus());
                ps.setTimestamp(10,environment.getGather_date());
                ps.addBatch();

                System.out.println("count:" + count);
                if ( count % 100 == 0 ) {
                    ps.executeBatch();
                    System.out.println("插入数据库成功：" + count + "数据");
//                ps.execute();
                }
                count ++;
            }
            ps.executeBatch();
        } catch (Exception e){
//            try {
//                BackUpImpl backUp = new BackUpImpl();
//                String path = "/Users/wjh/Desktop/FirstProject/src/BackUptmp";
//                File file = new File(path);
//                if (!file.exists()){
//                    file.createNewFile();
//                }
//                backUp.storeEnvs(col,path);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if (ps != null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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



    public static void main(String[] args) {
//        DropEvnDB();
//        CreateEvnDB();

    }
}
