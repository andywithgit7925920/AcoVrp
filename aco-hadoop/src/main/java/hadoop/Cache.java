package hadoop;

import vrp.VRP;

import java.util.Arrays;

public class Cache {
	public String fileName;
	public Integer clientNum;
	public Integer capacity;
	public double[][] distance;  //距离矩阵
    public double[] clientDemandArr;    //顾客需求
    /************vrp**********/
    /***********vrptw***********/
    public double[] serviceTime;   //服务时间
    public double[][] time;     //车辆起止时间
    public double[][] savedQnuantity;    //节约量
    public void refresh(){
    	this.fileName = VRP.fileName;
    	this.clientNum = VRP.clientNum;
    	this.capacity = VRP.capacity;
    	this.distance = VRP.distance;
    	this.clientDemandArr = VRP.clientDemandArr;
    	this.serviceTime = VRP.serviceTime;
    	this.time = VRP.time;
    	this.savedQnuantity  = VRP.savedQnuantity;
    }
	@Override
	public String toString() {
		return "VrpTransportTemp [fileName=" + fileName + ", clientNum=" + clientNum
				+ ", capacity=" + capacity + ", distance="
				+ Arrays.toString(distance) + ", clientDemandArr="
				+ Arrays.toString(clientDemandArr) + ", serviceTime="
				+ Arrays.toString(serviceTime) + ", time="
				+ Arrays.toString(time) + ", savedQnuantity="
				+ Arrays.toString(savedQnuantity) + "]";
	}
    
}
