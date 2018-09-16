package com.briup.Server.helper;

import com.briup.util.Impl.IOUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBhelper {

    private static BasicDataSource DATA_SOURCE;

    static {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("/Users/wjh/Desktop/FirstProject/src/main/resources/config.properties"));
            properties.load(fis);

            DATA_SOURCE = new BasicDataSource();
            DATA_SOURCE = BasicDataSourceFactory.createDataSource(properties);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(fis);
        }
    }

    public static Connection getConnction(){
        Connection connection = null;
        try {
            connection = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void InsertIntoDB(String sql){
        Connection connection = getConnction();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        for (int i = 0 ; i < 1000 ; i++) {
//            System.out.println(i + "--"+getConnction());
//        }
//    }

}
