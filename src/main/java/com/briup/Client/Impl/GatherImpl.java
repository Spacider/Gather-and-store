package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.Gather;
import com.briup.util.Impl.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(GatherImpl.class);

    private static List<Environment> environmentList = new ArrayList <>() ;

    private volatile static long position = 0;

    static {
        RandomAccessFile raf = null;
        File file = new File("/Users/wjh/Desktop/FirstProject/src/radwtmp");
        try {
            raf = new RandomAccessFile(file,"r");
            position = getPostion(raf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void init(Properties properties) {
    }

    public static void main(String[] args) {
        new GatherImpl().gather();
    }

    public Collection<Environment> gather(){
        RandomAccessFile raf = null;
        BufferedReader br = null;
        Environment envir = new Environment();

        try {
            File file = new File("/Users/wjh/Desktop/FirstProject/src/radwtmp");
//            long FilePotionLong = Long.parseLong(FilePostion);

            // 如果文件不存在，就报错
            if (!file.exists()){
                LOGGER.error("can not find the file : radwtmp! ");
                // 则创建一个新的文件
                file.createNewFile();
            }else {
                raf = new RandomAccessFile(file,"r");

                raf.seek(position);

                String str = null;
                while ((str = raf.readLine()) != null) {
//                System.out.println(str);
                    String[] stringList = str.split("\\|");
//                System.out.println(Arrays.toString(stringList));
//                [100|101|2|16|1|3|5d606f7802|1|2018-09-15 11:17:13.526]
//                    System.out.println(Arrays.toString(stringList));

                    getenv(stringList);
                }

                position = getPostion(raf);

                System.out.println("position : " + position);

                for (Environment environment : environmentList) {
                    System.out.println(environment);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(raf,br);
        }

        List<Environment> environmentList1 = new LinkedList <>(environmentList);
        environmentList.clear();
        return environmentList1;
    }

    public static Long getPostion(RandomAccessFile raf){
        long length = 0;
        try {
            length =  raf.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    private static void getenv(String[] stringList){
           System.out.println(Arrays.toString(stringList));
        //   [100|101|2|16|1|3|5d606f7802|1|2018-09-15 11:17:13.526]
        String sensorAddress = stringList[3];
        int FinalDate = 0;

        FinalDate = Integer.parseInt(stringList[6].substring(0, 4), 16);

        if (sensorAddress.equals("16")) {
            if (stringList[6].length() != 10){
                LOGGER.error("得到的数据为脏数据, 温度湿度错误数据:" + stringList[6]);
            }else {
                /**
                 * 温度􏲅􏲆:value(int) float Temperature = ((float)value*0.00268127)- 46.85;
                 * 􏲌􏲆湿度:value(int) float Humidity = ((float)value*0.00190735)-6;
                 */
                // 生成温度对象，并添加到 list 中
                float Temperature = (float) ((FinalDate * 0.00268127) - 46.85);
                environmentList.add(SetNameAndData(stringList, "温度", Temperature));

                // 生成湿度对象，并添加到 list 中
                FinalDate = Integer.parseInt(stringList[6].substring(4, 8), 16);
                float Humidity = (float) ((FinalDate * 0.00190735) - 6);
                environmentList.add(SetNameAndData(stringList, "湿度", Humidity));
            }
        }else if (sensorAddress.equals("256")){
            if (stringList[6].length() != 6) {
                LOGGER.error("得到的数据为脏数据, 光照错误数据:" + stringList[6]);
            }else{
                environmentList.add(SetNameAndData(stringList, "光照强度", FinalDate));
            }
        }else if (sensorAddress.equals("1280")){
            if (stringList[6].length() != 6) {
                LOGGER.error("得到的数据为脏数据, 二氧化碳错误数据:" + stringList[6]);
            }else{
                environmentList.add(SetNameAndData(stringList, "二氧化碳", FinalDate));
            }
        }else{
            LOGGER.error("得到的数据错误");
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
