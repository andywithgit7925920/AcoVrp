package model;

import util.ArrayUtil;
import util.DataUtil;
import util.MatrixUtil;

import static util.ConstUtil.*;

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
        //将蚂蚁初始化在出发站
        visitedClient = new int[clientNum];
        //默认开始从起始点出发
        visitedClient[0] = 1;
        initAllowClient2One(allowedClient);
    }

    /**
     * 选择下一个城市
     *
     * @param pheromone
     */
    public void selectNextClient(double[][] pheromone,int id) {
        //如果当前处在起始点，则下一步不能选择起始点
        if (solution.getCurrentTruck().isEmpty()) {
            allowedClient[0] = 0;
        }
        double[] p = new double[clientNum];
        double sum = 0.0;
        //计算分母部分
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                sum += Math.pow(pheromone[solution.getCurrentTruck().getCurrentCus()][i], ALPHA) * Math.pow(1.0 / distance[solution.getCurrentTruck().getCurrentCus()][i], BETA);
            }
        }
        //计算概率矩阵
        for (int i = 0; i < clientNum; i++) {
            if (allowedClient[i] == 1) {
                p[i] = Math.pow(pheromone[solution.getCurrentTruck().getCurrentCus()][i], ALPHA) * Math.pow(1.0 / distance[solution.getCurrentTruck().getCurrentCus()][i], BETA) / sum;
            } else {
                p[i] = 0.0;
            }
        }
        //轮盘赌选择下一个城市
        double selectP = Math.random();
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
        //将当前城市加入tour中
        solution.addCus(selectClient);
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1 && !solution.getCurrentTruck().checkNowCus(i)) {
                allowedClient[i] = 0;
            }
        }
        //如果当前已经走完一个循环
        if (selectClient == 0) {
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
        //将当前客户改为选择的客户
        if (solution.getCurrentTruck().isEmpty()) {
            allowedClient[0] = 1;
        }
        //如果allowedClient只包含0点，则进入下一循环
        if (OnlyContainsDeposit(allowedClient)) {
            //System.out.println("allowedClient只包含0点，则进入下一循环");
            //将起始站加入tour中，该循环结束
            solution.increaseLoop();
            //重新计算允许访问的城市
            initAllowClient2Zero(allowedClient);
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient[i] = 1;
                }
            }
        }
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
            for (int i = 0, len = allowedClient.length; i < len; i++) {
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
        if (allowedClient[0] == 0) {
            return false;
        } else {
            return true;
        }
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
}
