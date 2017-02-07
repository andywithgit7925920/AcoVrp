package model;

import util.ConstUtil;

import static util.DataUtil.*;

import util.DataUtil;
import util.MatrixUtil;
import util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ab792 on 2016/12/30.
 */
public class ACO {
    private Ant[] ants; //蚂蚁
    private Integer antNum; //蚂蚁数量
    private Integer ITER_NUM;   //迭代数
    private double[][] pheromone;   //信息素矩阵
    private double bestLen; //最佳长度
    private Solution bestSolution;  //最佳解

    public ACO() {
        this.antNum = ConstUtil.ANT_NUM;
        ITER_NUM = ConstUtil.ITER_NUM;
        ants = new Ant[antNum];

    }

    public void init(String filePath) {
        if (StringUtil.isNotEmpty(filePath)) {
            try {
                //导入数据
                importDataFromAVRP(filePath);
                //this.capacity = DataUtil.capacity;
                //初始化信息素矩阵
                pheromone = new double[clientNum][clientNum];
                for (int i = 0; i < clientNum; i++) {
                    for (int j = 0; j < clientNum; j++) {
                        pheromone[i][j] = 0.1;
                    }
                }
                bestLen = Double.MAX_VALUE;
                //bestTour = new ArrayList<List<Integer>>();
                bestSolution = new Solution();
                //初始化蚂蚁
                initAntCommunity();
            } catch (IOException e) {
                System.err.print("filePath invalid!");
                e.printStackTrace();
            }
            System.out.println("capacity-->" + capacity);
            System.out.println("clientNum-->" + clientNum);

        } else {
            System.err.print("filePath empty!");
        }
    }

    /**
     * 初始化蚂蚁
     */
    private void initAntCommunity() {
        for (int i = 0; i < antNum; i++) {
            ants[i] = new Ant();
            ants[i].init();
        }
    }

    /**
     * ACO的运行过程
     */
    public void run() {
        //进行ITER_NUM次迭代
        for (int i = 0; i < ITER_NUM; i++) {
            System.out.println("ITER_NUM:" + i);
            //对于每一只蚂蚁
            for (int j = 0; j < antNum; j++) {
                //System.out.println("第" + j + "只蚂蚁开始");
                while (!ants[j].visitFinish()) {
                    ants[j].selectNextClient(pheromone);
                }
                //System.out.println("第" + j + "只蚂蚁总路径长度" + ants[j].getLength());
                //System.out.println("第" + j + "只蚂蚁路径"+ants[j].getSolution());
                if (ants[j].getLength() < bestLen) {
                    bestLen = ants[j].getLength();
                    bestSolution = ants[j].getSolution();
                }
                for (int k1 = 0; k1 < ants[j].getSolution().size(); k1++) {
                    ants[j].getDelta()[0][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(0).intValue()] = (1. / ants[j].getLength());
                    for (int k2 = 0, len2 = ants[j].getSolution().getTruckSols().get(k1).size(); k2 + 1 < len2; k2++) {
                        ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()] = (1. / ants[j].getLength());
                        ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()] = (1. / ants[j].getLength());
                    }
                    ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).size() - 1][0] = (1. / ants[j].getLength());
                }
            }
            //更新信息素
            updatePheromone();
            //初始化蚁群
            initAntCommunity();
        }
        //打印最佳结果
        printOptimal();
    }


    /**
     * 打印最佳结果
     */
    private void printOptimal() {
        System.out.println("The optimal length is: " + bestLen);
        System.out.println("The optimal tour is: ");
        System.out.println(bestSolution);
        System.out.println("The value of pheromone:");
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j < pheromone[i].length; j++) {
                System.out.print(pheromone[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * 更新信息素
     */
    private void updatePheromone() {
        //信息素挥发
        for (int i = 0; i < clientNum; i++) {
            for (int j = 0; j < clientNum; j++) {
                pheromone[i][j] = pheromone[i][j] * (1 - ConstUtil.RHO);
                pheromone[i][j] = (pheromone[i][j] < 1.0E-323) ? 1.0E-323 : pheromone[i][j];
            }
        }
        //信息素更新
        for (int i = 0; i < clientNum; i++) {
            for (int j = 0; j < clientNum; j++) {
                for (int k = 0; k < antNum; k++) {
                    pheromone[i][j] += ants[k].getDelta()[i][j];
                }
            }
        }
    }
}
