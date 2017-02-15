package acs;

import localsearch.BaseStretegy;
import localsearch._2OptStretegy;
import updatestrategy.BaseUpdateStrategy;
import updatestrategy.UpdateStrategy4Case1;
import updatestrategy.UpdateStrategy4Case2;
import util.ConstUtil;
import static util.LogUtil.logger;
import static util.DataUtil.*;

import util.StringUtil;
import vrp.Solution;

import java.io.IOException;

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
    private Ant bestAnt;    //最佳路径的蚂蚁
    private BaseUpdateStrategy baseUpdateStrategy;  //信息素更新策略
    private BaseStretegy stretegy;  //局部搜索策略

    public ACO() {
        this.antNum = ConstUtil.ANT_NUM;
        ITER_NUM = ConstUtil.ITER_NUM;
        ants = new Ant[antNum];
        baseUpdateStrategy = new UpdateStrategy4Case1();
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
                        pheromone[i][j] = ConstUtil.PHEROMONE_INIT;
                    }
                }
                bestLen = Double.MAX_VALUE;
                //初始化蚂蚁
                initAntCommunity();
            } catch (IOException e) {
                System.err.print("filePath invalid!");
                e.printStackTrace();
            }

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
            //System.out.println("ITER_NUM:" + i);
            //bestSolution = null;
            //bestAnt = null;
            //对于每一只蚂蚁
            for (int j = 0; j < antNum; j++) {
                //System.out.println("第" + j + "只蚂蚁开始");
                //logger.info("第" + j + "只蚂蚁开始");
                while (!ants[j].visitFinish()) {
                    ants[j].selectNextClient(pheromone);
                }
                //System.out.println("第" + j + "只蚂蚁总路径长度" + ants[j].getLength());
                //System.out.println("第" + j + "只蚂蚁路径" + ants[j].getSolution());
                //改变信息素更新策略
                if (bestSolution == null && bestAnt == null) {
                    //logger.info("=============case1=============");
                    bestAnt = ants[j];
                    bestLen = bestAnt.getLength();
                    bestSolution = bestAnt.getSolution();
                }
                //1.若𝑅的用车数大于𝑅∗的 用车数, 则将𝑅中所有边上的信息素进行大量蒸发
                else if (ants[j].getSolution().getTruckNum() > bestSolution.getTruckNum()) {
                    //logger.info("=============case2=============");
                    setBaseUpdateStrategy(new UpdateStrategy4Case1());
                    baseUpdateStrategy.update(pheromone, ants[j].getSolution());
                }
                //2.若𝑅的用车数等 于𝑅∗的用车数, 但𝑅的距离/时间费用大于等于𝑅∗相 应的费用, 则将𝑅中所有边上的信息素进行少量蒸发
                else if (ants[j].getSolution().getTruckNum() == bestSolution.getTruckNum() && ants[j].getLength() >= bestLen) {
                    //logger.info("=============case3=============");
                    setBaseUpdateStrategy(new UpdateStrategy4Case2());
                    baseUpdateStrategy.update(pheromone, ants[j].getSolution());
                }
                //3.若𝑅的用车 数等于𝑅∗的用车数, 且𝑅的距离/时间费用小于𝑅∗相 应的费用, 或𝑅的用车数小于𝑅∗的用车数时
                else if ((ants[j].getSolution().getTruckNum () == bestSolution.getTruckNum() && ants[j].getLength() < bestLen) || (ants[j].getSolution().getTruckNum() < bestSolution.getTruckNum())) {
                    //logger.info("=============case4=============");
                    bestAnt = ants[j];
                    bestLen = bestAnt.getLength();
                    bestSolution = bestAnt.getSolution();
                }
                /**********优化解 begin**********/
                stretegy = new _2OptStretegy();
                stretegy.updateSolution(bestSolution);
                bestLen = bestSolution.getCost();
                ants[j].setSolution(bestSolution);
                //System.out.println("第" + j + "只蚂蚁优化后总路径长度" + ants[j].getLength());
                //System.out.println("第" + j + "只蚂蚁优化后路径" + ants[j].getSolution());
                /**********优化解 end**********/
                //更新蚂蚁自身的信息素
                for (int k1 = 0; k1 < ants[j].getSolution().size(); k1++) {
                    ants[j].getDelta()[0][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(0).intValue()] = (1. / ants[j].getLength());
                    for (int k2 = 0, len2 = ants[j].getSolution().getTruckSols().get(k1).size(); k2 + 1 < len2; k2++) {
                        ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()] = (1. / ants[j].getLength());
                        ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()][ants[j].getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()] = (1. / ants[j].getLength());
                    }
                    ants[j].getDelta()[ants[j].getSolution().getTruckSols().get(k1).size() - 1][0] = (1. / ants[j].getLength());
                }
                //更新信息素
                baseUpdateStrategy.update(pheromone, ants[j]);
            }

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
    /*private void updatePheromoneStrategy3() {
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
    }*/

    public void setBaseUpdateStrategy(BaseUpdateStrategy baseUpdateStrategy) {
        this.baseUpdateStrategy = baseUpdateStrategy;
    }
}
