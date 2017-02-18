package localsearch;

import javafx.scene.chart.PieChart;
import javafx.scene.control.SpinnerValueFactory;
import static vrp.VRP.*;

import util.DataUtil;
import vrp.Solution;
import vrp.Truck;

import javax.xml.bind.SchemaOutputResolver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ab792 on 2017/2/15.
 */
public class _2Opt$Stretegy implements BaseStretegy {
    public void updateSolution(Solution preSolution) throws Exception {
        LinkedList<Truck> truckSols = preSolution.getTruckSols();
        //对于每一组
        for (int i = 0, len1 = truckSols.size(); i < len1; i++) {
            //System.out.println("当前选择车辆--->" + i);
            Truck nowTruck = truckSols.get(i);
            //保存一组Truck互换的点
            Integer[] nodesMap = new Integer[2];
            //互换的Truck
            Truck[] truckMap = new Truck[2];
            double maxSpan = Double.MIN_VALUE;
            //与其余组中的进行比较
            for (int j = i + 1, len2 = truckSols.size(); j < len2; j++) {
                Truck compareTruck = truckSols.get(j);
                //System.out.println("比较车辆--->" + j);
                //当前组中的每一个点
                for (int k = 0; k < nowTruck.size(); k++) {
                    //System.out.println("当前车辆交换点k--->" + k);
                    //其余组中的每一个点
                    for (int m = 0; m < compareTruck.size(); m++) {
                        //System.out.println("比较车辆交换点m--->" + m);
                        double tempCost = calCost(nowTruck, compareTruck, k, m);
                        if (DataUtil.more(tempCost, 0.0)) {
                            //System.out.println("==DataUtil.more(tempCost, 0.0)==");
                            if (DataUtil.more(tempCost, maxSpan)) {
                                //System.out.println("==DataUtil.more(tempCost, MAX_SPAN)==");
                                //交换
                                _2Opt$Swap(nowTruck, compareTruck, k, m);
                                if (!nowTruck.isOverLoad() && !compareTruck.isOverLoad()) {
                                    /*System.out.println("============================");
                                    System.out.println("交换后的车辆未超载");
                                    System.out.println("==!nowTruck.isOverLoad() && !compareTruck.isOverLoad()==");
                                    System.out.println("当前车辆交换点k--->" + k);
                                    System.out.println("比较车辆交换点m--->" + m);
                                    System.out.println("当前车辆nowTruck--->" + nowTruck);
                                    System.out.println("比较车辆compareTruck--->" + compareTruck);
                                    System.out.println("tempCost--->" + tempCost);*/
                                    nodesMap[0] = k;
                                    nodesMap[1] = m;
                                    truckMap[0] = nowTruck;
                                    truckMap[1] = compareTruck;
                                    maxSpan = tempCost;
                                    //System.out.println("============================");
                                }
                                _2Opt$Swap(nowTruck, compareTruck, k, m);
                            }
                        }
                    }
                }
            }
            //当前组交换的最大值

            if (truckMap[0]!=null&&truckMap[1]!=null&&nodesMap[0]!=null&&nodesMap[1]!=null) {
                /*System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("maxSpan--->"+maxSpan);
                System.out.println("将选中的点进行最终交换");*/
                _2Opt$Swap(truckMap[0], truckMap[1], nodesMap[0], nodesMap[1]);
                /*System.out.println("truckMap[0]-->" + truckMap[0]);
                System.out.println("truckMap[1]-->" + truckMap[1]);
                System.out.println("nodesMap[0]-->" + nodesMap[0]);
                System.out.println("nodesMap[1]-->" + nodesMap[1]);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");*/
            }

        }

    }


    /**
     * 路径间交换
     *
     * @param truck1
     * @param truck2
     * @param indexI
     * @param indexJ
     * @return
     */
    public static void _2Opt$Swap(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception {
        if (truck1 == null || truck2 == null || truck1.getCustomers().size() <= indexI || truck2.getCustomers().size() <= indexJ)
            throw new Exception("input invalid!");
        LinkedList<Integer> customers1 = truck1.getCustomers();
        LinkedList<Integer> customers2 = truck2.getCustomers();
        LinkedList<Integer> temp = new LinkedList<Integer>();
        for (int i = indexI + 1, len = customers1.size(); i < len; i++) {
            temp.add(customers1.get(indexI + 1));
            customers1.remove(indexI + 1);
        }
        for (int j = indexJ + 1; j < customers2.size(); j++) {
            customers1.add(customers2.get(j));
        }
        for (int j = indexJ + 1, len = customers2.size(); j < len; j++) {
            customers2.remove(indexJ + 1);
        }
        if (!temp.isEmpty()) {
            for (Integer k : temp) {
                customers2.add(k);
            }
        }
    }

    /**
     * 计算交换的代价
     *
     * @param truck1
     * @param truck2
     * @param indexI
     * @param indexJ
     * @return
     */
    public double calCost(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception {
        if (truck1 == null || truck2 == null || truck1.getCustomers().size() <= indexI || truck2.getCustomers().size() <= indexJ)
            throw new Exception("input invalid!");
        LinkedList<Integer> customers1 = truck1.getCustomers();
        LinkedList<Integer> customers2 = truck2.getCustomers();
        int index11 = customers1.get(indexI), index12 = (indexI + 1) >= customers1.size() ? 0 : customers1.get(indexI + 1);
        int index21 = customers2.get(indexJ), index22 = (indexJ + 1) >= customers2.size() ? 0 : customers2.get(indexJ + 1);
        double cost1 = distance[index11][index12] + distance[index21][index22];
        double cost2 = distance[index11][index22] + distance[index21][index12];
        return cost1 - cost2;
    }

}
