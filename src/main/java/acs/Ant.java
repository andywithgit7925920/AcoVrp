package acs;

import util.ArrayUtil;
import vrp.Solution;

import static util.ConstUtil.*;
import static util.LogUtil.logger;
import static util.DataUtil.*;

import java.util.*;

/**
 * Created by ab792 on 2016/12/30.
 */
public class Ant {
    private Solution solution;
    private int[] allowedClient;  //允许访问的城市
    private int[] visitedClient;    //取值0或1，1表示已经访问过，0表示未访问过
    private double[][] delta;   //信息素变化矩阵
    public Ant() {
        allowedClient = new int[clientNum];
        delta = new double[clientNum][clientNum];
        solution = new Solution();
    }

    /**
     * 蚂蚁初始化
     *
     * @param
     */
    public void init() {
        //logger.info("ant .. init...begin");
        //将蚂蚁初始化在出发站
        visitedClient = new int[clientNum];
        //默认开始从起始点出发
        visitedClient[0] = 1;
        initAllowClient2One(allowedClient);
        //logger.info("ant .. init...end");
    }

    /**
     * 选择下一个城市
     *
     * @param pheromone
     */
    public void selectNextClient(double[][] pheromone) {
        /*logger.info("ant .. selectNextClient...begin");
        logger.info("allowedClient---" );
        ArrayUtil.printArr(allowedClient);
        logger.info("visitedClient---" );
        ArrayUtil.printArr(visitedClient);*/
        //如果当前处在起始点，则下一步不能选择起始点
        double[] p = new double[clientNum];
        double sum = 0.0;
        int currentCus = solution.getCurrentTruck().getCurrentCus();
        //logger.info("currentCus-->"+currentCus);
        //计算分母部分
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                sum += Math.pow(pheromone[currentCus][i], ALPHA) * Math.pow(1.0 / distance[currentCus][i], BETA);
                //System.out.println("sum--->"+sum);
                //System.out.println("Math.pow(pheromone[currentCus][i], ALPHA)--->"+Math.pow(pheromone[currentCus][i], ALPHA));
                /*System.out.println("currentCus-->"+currentCus);
                System.out.println("i--->"+i);
                System.out.println("Math.pow(1.0 / distance[currentCus][i], BETA)--->"+Math.pow(1.0 / distance[currentCus][i], BETA));
                System.out.println("distance[currentCus][i]--->"+distance[currentCus][i]);*/
            }
        }
        //logger.info("total sum---"+sum);
        /*if (sum ==0){
            for (int i = 0; i < allowedClient.length; i++) {
                if (allowedClient[i] == 1) {
                    *//*System.out.println("i--->"+i);
                    System.out.println("pheromone[currentCus][i]--->"+pheromone[currentCus][i]);
                    System.out.println("1.0 / distance[currentCus][i]--->"+1.0 / distance[currentCus][i]);
                    sum += Math.pow(pheromone[currentCus][i], ALPHA) * Math.pow(1.0 / distance[currentCus][i], BETA);*//*
                }
            }
            System.exit(-1);
        }*/
        //计算概率矩阵
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                p[i] = Math.pow(pheromone[currentCus][i], ALPHA) * Math.pow(1.0 / distance[currentCus][i], BETA) / sum;
            } else {
                p[i] = 0.0;
            }
        }
        //轮盘赌选择下一个城市
        double selectP = Math.random();
        //logger.info("selectP-->"+selectP);
        int selectClient = 0;
        double sum1 = 0.f;
        for (int i = 0; i < clientNum; i++) {
            sum1 += p[i];
            if (sum1 >= selectP) {
                selectClient = i;
                break;
            }
        }
        //从允许选择的城市中去除selectClient
        visitedClient[selectClient] = 1;
        allowedClient[selectClient] = 0;

        //logger.info("selectCliend---"+selectClient);
        //将当前城市加入solution中
        solution.addCus(selectClient);
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1 && !solution.getCurrentTruck().checkNowCus(i)) {
                allowedClient[i] = 0;
            }
        }
        //如果当前已经走完一个循环,如果allowedClient只包含0点，则进入下一循环
        if ((OnlyContainsDeposit(allowedClient)&&!visitFinish())) {
            //System.out.println("走完一个循环");
            solution.increaseLoop();
            initAllowClient2Zero(allowedClient);
            //重新计算允许访问的客户
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient[i] = 1;
                }
            }
        }
        //如果当前路径不在出发点，那么可以回到出发点
        /*if (!solution.getCurrentTruck().isEmpty()) {
            allowedClient[0] = 1;
        }*/
        //logger.info("ant .. selectNextClient...end");
    }


    /**
     * 将允许访问的城市置为0
     *
     * @param allowedClient
     */
    private static void initAllowClient2Zero(int[] allowedClient) {
        if (allowedClient != null && allowedClient.length > 0) {
            for (int i = 0, len = allowedClient.length; i < len; i++) {
                allowedClient[i] = 0;
            }
        }
    }

    /**
     * 将允许访问的城市置为1
     *
     * @param allowedClient
     */
    private void initAllowClient2One(int[] allowedClient) {
        if (allowedClient != null && allowedClient.length > 0) {
            for (int i = 1, len = allowedClient.length; i < len; i++) {
                allowedClient[i] = 1;
            }
        }
    }

    /**
     * 允许访问的城市是否只包含0点
     *
     * @param allowedClient
     * @return
     */
    private static boolean OnlyContainsDeposit(int[] allowedClient) {
        if (allowedClient == null || allowedClient.length == 0)
            return false;
        for (int i = 1; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 蚂蚁是否结束访问
     *
     * @return
     */
    public boolean visitFinish() {
        for (int i = 1; i < visitedClient.length; i++) {
            if (visitedClient[i] == 0)
                return false;
        }
        return true;
    }

    /**
     * 计算路径总长度
     *
     * @return
     */
    private double calculateTourLength() {
        return solution.calCost();
    }

    /**
     * get total length
     *
     * @return
     */
    public double getLength() {
        return calculateTourLength();
    }



    public int[] getVisitedClient() {
        return visitedClient;
    }

    public Integer getCapacity() {
        return capacity;
    }


    public double[][] getDelta() {
        return delta;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Ant{" +
                "solution=" + solution +
                ", allowedClient=" + Arrays.toString(allowedClient) +
                ", visitedClient=" + Arrays.toString(visitedClient) +
                ", delta=" + Arrays.toString(delta) +
                '}';
    }
}
