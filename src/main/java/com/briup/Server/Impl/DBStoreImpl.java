package com.briup.Server.Impl;

import com.briup.Bean.Environment;
import com.briup.Server.DBStore;
import com.briup.Server.helper.DBhelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;

/**
 * 入库模块实现
 */
public class DBStoreImpl implements DBStore {

    public final static Logger LOGGER = LoggerFactory.getLogger(DBStoreImpl.class);

    public void saveEnvToDB(Collection<Environment> col) {

        System.out.println(col.size());
        if (col != null) {
            for (Environment environment : col) {

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                Timestamp timestamp = environment.getGather_date();
                String tsStr = sdf.format(timestamp);
                // gather_date=2018-09-15 11:17:48.0

                String[] time = tsStr.split("\\-");
                String day = time[2];

                String sql = "insert into T_DETAIL_" + day
                        + " values("
                        + "'" + environment.getSrcID() + "',"
                        + "'" + environment.getDstID() + "',"
                        + "'" + environment.getDevID() + "',"
                        + "'" + environment.getSensorAddress() + "',"
                        + environment.getCount() + ","
                        + environment.getCmd() + ","
                        + "'" + environment.getData() + "')";

//            System.out.println(sql);

                DBhelper.InsertIntoDB(sql);
                LOGGER.info("insert into database successful");
            }
        }else {

        }
    }

    public static void CreateEvnDB(){
        for (int i = 1 ; i <= 31 ; i++) {
            String sql = "CREATE TABLE T_DETAIL_" + i
                    +"("
                    +"source_id  VARCHAR2(10),"
                    +"dit_id  VARCHAR2(10),"
                    +"dev_id  VARCHAR2(10),"
                    +"SensorAddress VARCHAR2(10),"
                    +"Counter  NUMBER(7),"
                    +"Cmd  NUMBER(7),"
                    +"Data  VARCHAR2(10)"
                    +")";
            DBhelper.InsertIntoDB(sql);
        }
    }

    public static void DropEvnDB(){
        for (int i = 1 ; i <= 31 ; i++) {
            String sql = "Drop TABLE T_DETAIL_" + i;
            DBhelper.InsertIntoDB(sql);
        }
    }

    public static void main(String[] args) {
        CreateEvnDB();
    }

    public void init(Properties properties) {

    }
}
