package com.briup.util.Impl;

import com.briup.Bean.Environment;
import com.briup.util.BackUp;

import java.io.*;
import java.util.Collection;
import java.util.Properties;

public class BackUpImpl implements BackUp {


    @Override
    public void storeEnvs(Collection<Environment> coll, String path) {
        File file = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file,true);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(coll);
            oos.flush();
            System.out.println("已经存入备份文件中 ,path:" +path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(fos,oos);
        }
    }

    @Override
    public Collection <Environment> loadEnvs() {
        String path = "/Users/wjh/Desktop/FirstProject/src/BackUptmp";
        FileInputStream fis =  null;
        ObjectInputStream ois = null;
        Object backupObject = null;

        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);

            backupObject = ois.readObject();

            System.out.println("已经从 path:" + path + "取出备份文件");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Collection <Environment>) backupObject;
    }

    @Override
    public void init(Properties properties) {

    }
}
