package com.briup.Server.helper;

import com.briup.Bean.Environment;
import com.briup.util.Impl.IOUtil;
import com.sun.tools.doclint.Env;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 数据库处理帮助类
 * 书写关于数据库的一些操作方法，比如 获取连接对象 、 封装插入方法
 */
public class DBhelper {

    // dhcp 缓冲池对象
    private static BasicDataSource DATA_SOURCE;

    static {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("/Users/wjh/Desktop/FirstProject/src/main/resources/config.properties"));
            properties.load(fis);

            // 通过读取 properties 文件来初始化 DATA_SOURCE
            DATA_SOURCE = new BasicDataSource();
            DATA_SOURCE = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(fis);
        }
    }

    /**
     * 获取 Connection 连接对象
     * @return
     */
    public static Connection getConnction(){
        Connection connection = null;
        try {
            connection = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 实现插入方法
     * @param sql 执行的 sql 语句
     */
    public static void InsertIntoDB(String sql, Environment environment){
        Connection connection = getConnction();
        PreparedStatement ps = null;
        try {
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

            ps.execute();
        } catch (SQLException e) {
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

    public static void ExecuteSql(String sql){
        Connection connection = getConnction();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
