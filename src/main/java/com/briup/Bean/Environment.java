package com.briup.Bean;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 每一行日志封装的对象
 * sensorAddress 为 16 封装成2个对象：(温度，湿度)
 * sensorAddress 为 256 封装的对象是光照强度
 * sensorAddress 为 1280 封装的对象是二氧化碳对象
 * srcId|destId|devId|sersorAddress|count|cmd|Data|status|gather_date
 * 发送端的Id|树莓派系统的Id|实验箱区域模块的Id|模块上传感器的地址|传感器的个数|发送指令的标号|服务器端返回的温度和湿度数据|状态码|生成这行指令的时间
 *
 */
public class Environment implements Serializable {


    /**
     * 标记封装的是温度、湿度或二氧化碳或光照强度
     */
    private String name;

    /**
     * 发送端的 ID
     */
    private String SrcID;

    /**
     * 树莓派系统的Id
     */
    private String DstID;

    /**
     * 实验箱区域模块的Id
     */
    private String DevID;

    /**
     * 模块上传感器的地址
     */
    private String SensorAddress;

    /**
     * 调动传感器的个数
     */
    private int Count;

    /**
     * 发送指令的标号
     * 3 表示接受数据
     * 16 表示发送数据
     */
    private int cmd;

    /**
     * 采集到的数据:
     * 服务器端返回的温度和湿度数据
     */
    private float Data;

    /**
     * 状态码
     */
    private int Status;

    /**
     * 日志生成时间
     * 生成这行指令的时间
     */
    private Timestamp gather_date;

    @Override
    public String toString() {
        return "Environment{" +
                "  name='" + name + '\'' +
                ", SrcID='" + SrcID + '\'' +
                ", DstID='" + DstID + '\'' +
                ", DevID='" + DevID + '\'' +
                ", SensorAddress='" + SensorAddress + '\'' +
                ", Count=" + Count +
                ", cmd=" + cmd +
                ", Data=" + Data +
                ", Status=" + Status +
                ", gather_date=" + gather_date +
                '}';
    }

    public Environment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcID() {
        return SrcID;
    }

    public void setSrcID(String srcID) {
        SrcID = srcID;
    }

    public String getDstID() {
        return DstID;
    }

    public void setDstID(String dstID) {
        DstID = dstID;
    }

    public String getDevID() {
        return DevID;
    }

    public void setDevID(String devID) {
        DevID = devID;
    }

    public String getSensorAddress() {
        return SensorAddress;
    }

    public void setSensorAddress(String sensorAddress) {
        SensorAddress = sensorAddress;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public float getData() {
        return Data;
    }

    public void setData(float data) {
        Data = data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Timestamp getGather_date() {
        return gather_date;
    }

    public void setGather_date(Timestamp gather_date) {
        this.gather_date = gather_date;
    }
}
