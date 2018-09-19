package com.briup.util.Impl;


import com.briup.Client.EnvClient;
import com.briup.Client.Gather;
import com.briup.Server.DBStore;
import com.briup.Server.EnvServer;
import com.briup.util.BackUp;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.WossModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 配置模块实现
 */

public class ConfigurationImpl implements Configuration {

    private Map<String,WossModel> ObjectMap = new HashMap <>();

    public ConfigurationImpl() {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        FileInputStream fis = null;
        Element EMS = null;

        try {
            fis = new FileInputStream("/Users/wjh/Desktop/FirstProject/src/main/resources/EMS.xml");
            document = saxReader.read(fis);
            EMS = document.getRootElement();

            List<Element> EMSlist = EMS.elements();
            for (Element element : EMSlist){
                //gather -- EnvClient -- EnvServer -- BackUp -- DBStore --
                String elementName = element.getName();
                String elementClass = element.attribute("class").getText();
                WossModel obj = (WossModel) Class.forName(elementClass).newInstance();
                if (obj instanceof ConfigurationAware){
                    ((ConfigurationAware) obj).SetConfiguration(this);
                }
                // 遍历子节点，为应该赋值的变量赋值
                List<Element> ChildEMSList = element.elements();
//                System.out.println(ChildEMSList.size());
//                System.out.println(obj);
                Properties properties =new Properties();
                for (Element element1 : ChildEMSList){
//                    System.out.println(element.getName() +"--"+element1.getName()+"--"+element1.getText());
                    properties.setProperty(element1.getName(),element1.getText());
                }
                obj.init(properties);
                ObjectMap.put(elementName,obj);
            }
        } catch (FileNotFoundException | DocumentException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            IOUtil.close(fis);
        }

    }

    @Override
    public Gather getGather() {
        return (Gather) ObjectMap.get("gather");
    }

    @Override
    public EnvClient getClient() {
        return (EnvClient) ObjectMap.get("EnvClient");
    }

    @Override
    public EnvServer getServer() {
        return (EnvServer) ObjectMap.get("EnvServer");
    }

    @Override
    public DBStore getDBStore() {
        return (DBStore) ObjectMap.get("DBStore");
    }

//    @Override
//    public Log getLog() {
//        return new LogImpl();
//    }

    @Override
    public BackUp getBackUp() {
        return (BackUp) ObjectMap.get("BackUp");
    }



}
