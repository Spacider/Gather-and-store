package com.briup.util.Impl;


import com.briup.Client.EnvClient;
import com.briup.Client.Gather;
import com.briup.Server.DBStore;
import com.briup.Server.EnvServer;
import com.briup.util.BackUp;
import com.briup.util.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * 配置模块实现
 */

public class ConfigurationImpl implements Configuration {


    public static Element getDom4j (){
        SAXReader saxReader = new SAXReader();
        Document document = null;
        FileInputStream fis = null;
        Element EMS = null;

        try {
            fis = new FileInputStream("/Users/wjh/Desktop/FirstProject/src/main/resources/EMS.xml");
            document = saxReader.read(fis);
            EMS = document.getRootElement();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }finally {
            IOUtil.close(fis);
        }
        return EMS;
    }

    @Override
    public Gather getGather() {
        Element EMS = getDom4j();
        Gather gatherObj = null;
        // 获取二级标签结点
        Element gather = EMS.element("gather");
        String gatherClass = gather.attribute("class").getValue();
        String logFile = gather.element("logFile").getText();
        String positionFile = gather.element("positionFile").getText();

        try {
            gatherObj= (Gather) Class.forName(gatherClass).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        properties.setProperty("logFile",logFile);
        properties.setProperty("positionFile",positionFile);
        gatherObj.init(properties);

        return  gatherObj;
    }

    @Override
    public EnvClient getClient() {
        Element EMS = getDom4j();
        EnvClient client = null;
        // 获取二级标签结点
        Element envclient =  EMS.element("EnvClient");
        String envclientClass = envclient.attribute("class").getValue();
        String host = envclient.element("host").getText();
        String port = envclient.element("port").getText();
        String path = EMS.element("BackUp").element("BackUppath").getText();

        try {
            client= (EnvClient) Class.forName(envclientClass).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        properties.setProperty("host",host);
        properties.setProperty("port",port);
        properties.setProperty("path",path);
        client.init(properties);

        return client;
    }

    @Override
    public EnvServer getServer() {
        Element EMS = getDom4j();
        EnvServer envServer = null;

        Element EnvServer = EMS.element("EnvServer");

        String EnvServerClass = EnvServer.attribute("class").getValue();

        try {
            envServer = (EnvServer) Class.forName(EnvServerClass).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String port = EnvServer.element("port").getText();

//        System.out.println(port);

        Properties properties = new Properties();
        properties.setProperty("port",port);

        envServer.init(properties);

        return envServer;
    }

    @Override
    public DBStore getDBStore() {
        Element EMS = getDom4j();
        DBStore dbStore = null;

        Element DBStore = EMS.element("DBStore");

        String dbstoreClass = DBStore.attribute("class").getValue();

        try {
            dbStore = (DBStore) Class.forName(dbstoreClass).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbStore;
    }

//    @Override
//    public Log getLog() {
//        return new LogImpl();
//    }

    @Override
    public BackUp getBackUp() {
        Element EMS = getDom4j();
        BackUp backUp = null;

        Element BackUp = EMS.element("BackUp");
        String backupClass = BackUp.attribute("class").getValue();

        String BackUppath = BackUp.element("BackUppath").getText();

        try {
            backUp = (BackUp) Class.forName(backupClass).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        properties.setProperty("BackUppath",BackUppath);

        backUp.init(properties);

        return backUp;
    }


    public static void main(String[] args) {
        ConfigurationImpl configuration = new ConfigurationImpl();
        System.out.println(configuration.getServer());
    }
}
