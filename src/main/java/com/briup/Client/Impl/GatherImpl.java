package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.Gather;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.Impl.IOUtil;
import com.briup.util.Impl.LogImpl;
import com.briup.util.Log;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 采集模块实现
 * 从 radwtmp 文件中取出数据并封装对象，装入 list 集合并返回给数据库入库模块使用
 */
public final class GatherImpl implements Gather , ConfigurationAware {

    private Configuration configuration ;
    private static List<Environment> environmentList = new ArrayList <>() ;

    private LogImpl logger = null;

    private volatile static long position = 0;

    private String logFile;
    private String positionFileAttr;

    public void init(Properties properties) {
        logFile = properties.getProperty("logFile");
        positionFileAttr = properties.getProperty("positionFile");
    }

    @Override
    public void SetConfiguration(Configuration conf) {
        this.configuration = conf;
        logger = (LogImpl) configuration.getLog();
    }

    public static void main(String[] args) {
        new GatherImpl().gather();
    }

    /**
     * 采集主方法
     * @return
     */
    public Collection<Environment> gather(){
        RandomAccessFile raf = null;
        BufferedReader br = null;
        Environment envir = new Environment();
        PrintWriter pw = null;

        try {

            // 读取 properties 文件并从中读取出上一次文件读取的位置F:ilePostion
            Properties properties = new Properties();
            File positionFile = new File(positionFileAttr);
            if (!positionFile.exists()){
                positionFile.createNewFile();
            }
            properties.load(new FileReader(positionFile));
            String FilePostion = properties.getProperty("FilePostion");


            File file = new File(logFile);
//            long FilePotionLong = Long.parseLong(FilePostion);

            // 如果文件不存在，就新建一个新的文件
            if (!file.exists()){
                // 则创建一个新的文件
                file.createNewFile();
            }else {
                // 跳过之前已经读取过的字节数  seek()
                raf = new RandomAccessFile(file,"r");
                raf.seek(Integer.parseInt(FilePostion));

                String str = null;
                while ((str = raf.readLine()) != null) {
                    // 运用 | 来拆分字符串
                    String[] stringList = str.split("\\|");
                    getenv(stringList);
                }

                position = getPostion(raf);

//                System.out.println("position : " + position);

                logger.info("记录文件 position:" +position);
                // 将最后的位数写入文件
                properties.setProperty("FilePostion" , position+"");
                pw = new PrintWriter(positionFile);
                properties.store(pw,null);

//                for (Environment environment : environmentList) {
//                    System.out.println(environment);
//                }
            }
        } catch (Exception e) {
            logger.error("从文件中采集数据失败" +e);
        } finally {
            IOUtil.close(raf);
        }

        List<Environment> environmentList1 = new LinkedList <>(environmentList);
        // 对 list 集合进行清理
        environmentList.clear();
        return environmentList1;
    }

    /**
     * 得到当前读到的文件的末尾的字节数
     * @param raf
     * @return
     */
    public static Long getPostion(RandomAccessFile raf){
        long length = 0;
        try {
            length =  raf.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 根据所传入的拆分后的字符串来生成新的对象并返回
     * @param stringList 根据 radWtmp 拆分的字符串
     */
    private void getenv(String[] stringList){

//        System.out.println(Arrays.toString(stringList));
        //   [100|101|2|16|1|3|5d606f7802|1|2018-09-15 11:17:13.526]
        String sensorAddress = stringList[3];
        int FinalDate = 0;

        FinalDate = Integer.parseInt(stringList[6].substring(0, 4), 16);

        if (sensorAddress.equals("16")) {
            if (stringList[6].length() != 10){
//                System.out.println("得到的数据为脏数据, 温度湿度错误数据:" + stringList[6]);
                logger.warn("得到的数据为脏数据, 温度湿度错误数据:" + stringList[6]);
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
//                System.out.println("得到的数据为脏数据, 光照错误数据:" + stringList[6]);
                logger.warn("得到的数据为脏数据, 光照错误数据:" + stringList[6]);
            }else{
                environmentList.add(SetNameAndData(stringList, "光照强度", FinalDate));
            }
        }else if (sensorAddress.equals("1280")){
            if (stringList[6].length() != 6) {
                logger.warn("得到的数据为脏数据, 二氧化碳错误数据:" + stringList[6]);
//                System.out.println("得到的数据为脏数据, 二氧化碳错误数据:" + stringList[6]);
            }else{
                environmentList.add(SetNameAndData(stringList, "二氧化碳", FinalDate));
            }
        }else{
            logger.error("得到的数据错误");
//            System.out.println("得到的数据错误");
        }
    }

    /**
     * 为不同的 Enviroment 对象封装名字和数据
     * @param stringList
     * @param name
     * @param Data
     * @return
     */
    private Environment SetNameAndData(String[] stringList, String name ,float Data){
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
            logger.error("设置名字和数据失败");
        }

        return envir;
    }


}
