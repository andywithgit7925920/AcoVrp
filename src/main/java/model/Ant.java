package model;

import util.ArrayUtil;
import util.DataUtil;

import static util.ConstUtil.*;

import static util.DataUtil.*;

import java.util.*;

/**
 * Created by ab792 on 2016/12/30.
 */
public class Ant {
    private List<List<Integer>> tour; //访问的城市列表
    private int[] allowedClient;  //允许访问的城市
    private int[] visitedClient;    //取值0或1，1表示已经访问过，0表示未访问过
    private Integer capacity;   //蚂蚁的载重量
    private Double totalLen;
    private double[][] delta;   //信息素变化矩阵
    private Integer firstClient;    //起始客户
    private Integer currentClient;  //当前客户
    private Integer currentCicycle;    //当前属于第几个循环
    private Integer currentCapacity;    //当前载重量

    public Ant(Integer capacity) {
        this.capacity = capacity;
        tour = new ArrayList<List<Integer>>();
        List<Integer> cicycle = new ArrayList<Integer>();
        tour.add(cicycle);
        allowedClient = new int[clientNum];
        delta = new double[clientNum][clientNum];
        initAllowClient2One(allowedClient);
        currentCicycle = 0;
        currentCapacity = capacity;
    }

    /**
     * 蚂蚁初始化
     *
     * @param
     */
    public void init() {
        //将蚂蚁初始化在出发站
        firstClient = 0;
        tour.get(0).add(0);
        currentClient = firstClient;
        visitedClient = new int[DataUtil.clientNum];
        visitedClient[0] = 1;
    }

    /**
     * 选择下一个城市
     *
     * @param pheromone
     */
    public void selectNextClient(double[][] pheromone) {
        //如果当前处在起始点，则下一步不能选择起始点
        if (currentClient == 0) {
            allowedClient[0] = 0;
        }
        double[] p = new double[clientNum];
        double sum = 0.0;
        //计算分母部分
        //System.out.println("count sum");
        //System.out.println("allowClient------");
        //ArrayUtil.printArr(allowedClient);
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                sum += Math.pow(pheromone[currentClient][i], ALPHA) * Math.pow(1.0 / distance[currentClient][i], BETA);
            }
        }
        //System.out.println("sum total --"+sum);
        //计算概率矩阵
        for (int i = 0; i < clientNum; i++) {
            if (allowedClient[i] == 1) {
                p[i] = Math.pow(pheromone[currentClient][i], ALPHA) * Math.pow(1.0 / distance[currentClient][i], BETA) / sum;
            } else {
                p[i] = 0.0;
            }
        }
        //System.out.println("p-------");
        //ArrayUtil.printArr(p);
        //轮盘赌选择下一个城市

        //System.out.println("selectP------");
        //Random random = new Random(System.currentTimeMillis());
        //double selectP = random.nextDouble();
        double selectP = Math.random();
        //System.out.println(selectP);
        int selectClient = 0;
        double sum1 = 0.f;
        for (int i = 0; i < clientNum; i++) {
            sum1 += p[i];
            if (sum1 >= selectP) {
                selectClient = i;
                break;
            }
        }
        //System.out.println("selectClient--");
        //System.out.println(selectClient);
        //从允许选择的城市中去除selectClient
        visitedClient[selectClient] = 1;
        allowedClient[selectClient] = 0;
        //将当前城市加入tour中
        if (currentCicycle >= tour.size()) {
            List<Integer> cicycle = new ArrayList<Integer>();
            cicycle.add(0);
            cicycle.add(selectClient);
            tour.add(cicycle);
        } else {
            tour.get(currentCicycle).add(selectClient);
        }
        currentCapacity -= clientDemandArr[selectClient];
        //根据载重量约束，计算下一步允许访问的城市
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1 && currentCapacity < clientDemandArr[i]) {
                allowedClient[i] = 0;
            }
        }
        //如果当前已经走完一个循环
        if (selectClient == 0) {
            ++currentCicycle;
            /*List<Integer> cicycle = new ArrayList<Integer>();
            cicycle.add(0);
            tour.add(cicycle);*/
            initAllowClient2Zero(allowedClient);
            //重新计算允许访问的城市
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient[i] = 1;
                }
            }
        }
        //将当前城市改为选择的城市
        currentClient = selectClient;
        if (currentClient != 0) {
            allowedClient[0] = 1;
        }
        //如果allowedClient只包含0点，则进入下一循环
        if (OnlyContainsDeposit(allowedClient)) {
            //System.out.println("allowedClient[i]==1");
            //将起始站加入tour中，该循环结束
            tour.get(currentCicycle).add(0);
            ++currentCicycle;
            /*List<Integer> cicycle = new ArrayList<Integer>();
            cicycle.add(0);
            tour.add(cicycle);*/
            //重新计算允许访问的城市
            initAllowClient2Zero(allowedClient);
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient[i] = 1;
                }
            }
            //重新初始化capacity
            currentCapacity = this.capacity;
        }
        /*if (!visitFinish()){
            tour.get(currentCicycle).add(0);
        }*/
        //System.out.println("cicycle----"+currentCicycle);
        //System.out.println("selectClient----"+selectClient);
        //System.out.println("selectClientDeman---"+clientDemandArr[selectClient]);
        //System.out.println("currentCapacity--"+currentCapacity);

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
        double len = 0;
        if (tour != null && tour.size() > 0) {
            for (int i = 0; i < tour.size(); i++) {
                for (int j = 0; j + 1 < tour.get(i).size(); j++) {
                    len += distance[tour.get(i).get(j).intValue()][tour.get(i).get(j + 1).intValue()];
                }
            }
            return len;
        }
        return 0.0;
    }

    /**
     * get total length
     *
     * @return
     */
    public double getLength() {
        totalLen = calculateTourLength();
        return totalLen;
    }


    public List<List<Integer>> getTour() {
        return tour;
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

    public Integer getFirstClient() {
        return firstClient;
    }

    public Integer getCurrentClient() {
        return currentClient;
    }
}
