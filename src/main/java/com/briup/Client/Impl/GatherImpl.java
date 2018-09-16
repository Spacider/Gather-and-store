package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.EnvClient;
import com.briup.Client.Gather;
import com.briup.util.Impl.IOUtil;
import com.sun.tools.doclint.Env;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 采集模块实现
 */
public final class GatherImpl implements Gather {

    private static List<Environment> environmentList = new ArrayList <>() ;

    public void init(Properties properties) {

    }

    public Collection<Environment> gather(){
        FileReader fr = null;
        BufferedReader br = null;
        Environment envir = new Environment();

        try {
            File file = new File("/Users/wjh/Desktop/FirstProject/src/radwtmp");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String str = null;
            while ( (str = br.readLine() )!= null){
//                System.out.println(str);
                String[] stringList = str.split("\\|");
//                System.out.println(Arrays.toString(stringList));
//                [100|101|2|16|1|3|5d606f7802|1|2018-09-15 11:17:13.526]
//                System.out.println(Arrays.toString(stringList));
                getenv(stringList);
            }

            file.delete();

            for (Environment environment : environmentList){
                    System.out.println(environment);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(fr,br);
        }

        List<Environment> environmentList1 = new LinkedList <>(environmentList);
        environmentList.clear();
        return environmentList1;
    }

    private static void getenv(String[] stringList){
        //   System.out.println(Arrays.toString(stringList));
        //   [100|101|2|16|1|3|5d606f7802|1|2018-09-15 11:17:13.526]
        Environment envir = new Environment();
        String sensorAddress = stringList[3];
        int FinalDate = 0;
        FinalDate = Integer.parseInt(stringList[6].substring(0, 4), 16);

        if (sensorAddress.equals("16")) {
            /**
             * 温度􏲅􏲆:value(int) float Temperature = ((float)value*0.00268127)- 46.85;
             * 􏲌􏲆湿度:value(int) float Humidity = ((float)value*0.00190735)-6;
             */
            // 生成温度对象，并添加到 list 中
            float Temperature = (float) ((FinalDate * 0.00268127) - 46.85);
            environmentList.add(SetNameAndData(stringList,"温度",Temperature));

            // 生成湿度对象，并添加到 list 中
            FinalDate = Integer.parseInt(stringList[6].substring(4, 8), 16);
            float Humidity = (float) ((FinalDate * 0.00190735) - 6);
            environmentList.add( SetNameAndData(stringList,"湿度",Humidity));

        }else if (sensorAddress.equals("256")){
            environmentList.add( SetNameAndData(stringList,"光照强度",FinalDate));
        }else if (sensorAddress.equals("1280")){
            environmentList.add( SetNameAndData(stringList,"二氧化碳",FinalDate));
        }
    }

    private final static Environment SetNameAndData(String[] stringList, String name ,float Data){
        String sensorAddress = stringList[3];
        Environment envir = new Environment();
        try {
            envir.setSrcID(stringList[0]);
            envir.setDstID(stringList[1]);
            envir.setDevID(stringList[2]);
            envir.setSensorAddress(sensorAddress);
            envir.setCount(Integer.parseInt(stringList[4]));
            envir.setCmd(Integer.parseInt(stringList[5]));
            envir.setStatus(Integer.parseInt(stringList[7]));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp ts = new Timestamp(format.parse(stringList[8]).getTime());
            envir.setGather_date(ts);
            envir.setName(name);
            envir.setData(Data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return envir;
    }

}
