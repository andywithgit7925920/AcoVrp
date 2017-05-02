package util;

import vrp.VRP;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ab792 on 2017/4/17.
 * 为了分布式，将所有VRP类静态变量存入vrpTransportTemp中
 */
public class VrpTransportTemp implements Serializable {

    private static final long serialVersionUID = -845957640989678771L;
    /***********vrp***********/
    public String fileName;  //文件名
    public Integer clientNum;    //顾客数量
    public Integer capacity;     //货车容量
    public double[][] distance;  //距离矩阵
    public double[] clientDemandArr;    //顾客需求
    /************vrp**********/
    /***********vrptw***********/
    public double[] serviceTime;   //服务时间
    public double[][] time;     //车辆起止时间
    public double[][] savedQnuantity;    //节约量

    /************vrptw**********/
    public VrpTransportTemp() {
        importDataFromVrp();
    }

    public void importDataFromVrp() {
        this.fileName = VRP.fileName;
        this.clientNum = VRP.clientNum;
        this.capacity = VRP.capacity;
        distance = Arrays.copyOf(VRP.distance, clientNum);
        clientDemandArr = Arrays.copyOf(VRP.clientDemandArr, clientNum);
        serviceTime = Arrays.copyOf(VRP.serviceTime, clientNum);
        time = Arrays.copyOf(VRP.time, clientNum);
        savedQnuantity = Arrays.copyOf(VRP.savedQnuantity, clientNum);
    }

}
