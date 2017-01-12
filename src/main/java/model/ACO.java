package model;

import util.ConstUtil;

import static util.DataUtil.*;

import util.DataUtil;
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
    private Integer capacity;   //车辆的载重约束
    private Integer ITER_NUM;   //迭代数
    private double[][] pheromone;   //信息素矩阵
    private double bestLen; //最佳长度
    private List<List<Integer>> bestTour;    //最佳路径
    //private int[] visitedCity;    //取值0或1，1表示已经访问过，0表示未访问过

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
                this.capacity = DataUtil.capacity;
                //初始化信息素矩阵
                pheromone = new double[clientNum][clientNum];
                for (int i = 0; i < clientNum; i++) {
                    for (int j = 0; j < clientNum; j++) {
                        pheromone[i][j] = 0.1;
                    }
                }
                bestLen = Double.MAX_VALUE;
                bestTour = new ArrayList<List<Integer>>();
                //初始化蚂蚁
                for (int i = 0; i < antNum; i++) {
                    ants[i] = new Ant(capacity);
                    ants[i].init();
                }

            } catch (IOException e) {
                System.err.print("filePath invalid!");
                e.printStackTrace();
            }
            System.out.println("capacity-->"+capacity);
            System.out.println("clientNum-->"+clientNum);

        } else {
            System.err.print("filePath empty!");
        }
    }

    /**
     * ACO的运行过程
     */
    public void run() {
        //进行ITER_NUM次迭代
        for (int i = 0; i < 10; i++) {
            //对于每一只蚂蚁
            for (int j = 0; j < 3; j++) {
                while (!ants[j].visitFinish()) {
                    ants[j].selectNextClient(pheromone);
                }
               /*for (int i=0;i<32;i++){
                    ants[j].selectNextClient(pheromone);
                }*/
                System.out.println("ants[j].getLength()---"+ants[j].getLength());
                if (ants[j].getLength() < bestLen) {
                    bestLen = ants[j].getLength();
                    bestTour = ants[j].getTour();
                }
                //更新蚂蚁自身的信息素矩阵
                for (int k1 = 0, len1 = ants[j].getTour().size(); k1 < len1; k1++) {
                    for (int k2 = 0, len2 = ants[j].getTour().get(k1).size(); k2 + 1 < len2; k2++) {
                        ants[j].getDelta()[ants[j].getTour().get(k1).get(k2).intValue()][ants[j].getTour().get(k1).get(k2 + 1).intValue()] = (1. / ants[j].getLength());
                        ants[j].getDelta()[ants[j].getTour().get(k1).get(k2 + 1).intValue()][ants[j].getTour().get(k1).get(k2).intValue()] = (1. / ants[j].getLength());
                    }
                }
            }
            /*for (int y = 0;y<antNum;y++){
                for (int z1 = 0;z1<ants[y].getDelta().length;z1++){
                    for (int z2=0;z2<ants[y].getDelta()[z1].length;z2++){
                        System.out.print(ants[y].getDelta()[z1][z2]+"  ");
                    }
                    System.out.print("\n");
                }
                System.out.println("============================");
            }*/
            updatePheromone();
            //初始化蚂蚁
            //visitedCity = new int[DataUtil.clientNum];
            for (int k3 = 0; k3 < antNum; k3++) {
                ants[k3] = new Ant(capacity);
                ants[k3].init();
            }
            //打印最佳结果
            printOptimal();
        }
    }

    /**
     * 打印最佳结果
     */
    private void printOptimal() {
        System.out.println("The optimal length is: " + bestLen);
        System.out.println("The optimal tour is: ");
        for (int i = 0; i < bestTour.size(); i++) {
            for (int j=0;j<bestTour.get(i).size();j++){
                System.out.print(bestTour.get(i).get(j)+"-");
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
