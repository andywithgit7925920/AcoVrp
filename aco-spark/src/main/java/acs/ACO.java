package acs;

import localsearch.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import parameter.Parameter;
import scala.Serializable;

import scala.Tuple2;
import scala.xml.PrettyPrinter;
import updatestrategy.BaseUpdateStrategy;
import updatestrategy.UpdateStrategy4Case1;
import updatestrategy.UpdateStrategy4Case2;

import util.VrpTransportTemp;
import util.DataUtil;
import util.StringUtil;
import vrp.Solution;
import vrp.VRP;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ab792 on 2016/12/30.
 */
public class ACO implements Serializable {
    private static final long serialVersionUID = -2542322072011298603L;
    private Ant[] ants; //蚂蚁
    private Integer antNum; //蚂蚁数量
    private Integer ITER_NUM;   //迭代数
    private double[][]  pheromone;   //信息素矩阵
    private volatile Broadcast<double[][]> broadcastPheromone = null;   //广播变量
    private double bestLen; //最佳长度
    private Solution bestSolution;  //最佳解
    private Ant bestAnt;    //最佳路径的蚂蚁
    private BaseUpdateStrategy baseUpdateStrategy;  //信息素更新策略
    private BaseStretegy stretegy;  //局部搜索策略
    private Solution pre3Solution = null;
    private Solution preNSolution = null;
    int FINISHCounter;
    private JavaSparkContext ctx;
    private VrpTransportTemp vrpTransportTemp;
    private Parameter parameter = new Parameter();


    public ACO() {
        this.antNum = parameter.ANT_NUM;
        ITER_NUM = parameter.ITER_NUM;
        ants = new Ant[antNum];
        baseUpdateStrategy = new UpdateStrategy4Case1();
        FINISHCounter = 0;
    }

    public ACO(JavaSparkContext ctx) {
        this.ctx = ctx;
        this.antNum = parameter.ANT_NUM;
        ITER_NUM = parameter.ITER_NUM;
        ants = new Ant[antNum];
        baseUpdateStrategy = new UpdateStrategy4Case1();
        FINISHCounter = 0;
    }

    public void init(String filePath) {
        if (StringUtil.isNotEmpty(filePath)) {
            try {
                //导入数据
                //importDataFromAVRP(FILE_PATH);
                VRP.importDataFromSolomon(filePath);
                //将所有静态变量封装进Cache中
                VrpTransportTemp vrpTransportTemp = new VrpTransportTemp();
                this.vrpTransportTemp = vrpTransportTemp;
                System.out.println("fileName---" + vrpTransportTemp.fileName);
                //初始化信息素矩阵
                pheromone = new double[vrpTransportTemp.clientNum][vrpTransportTemp.clientNum];
                for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
                    for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                        pheromone[i][j] = parameter.PHEROMONE_INIT;
                    }
                }

                bestLen = Double.MAX_VALUE;
                //初始化蚂蚁
                initAntCommunity(vrpTransportTemp);
            } catch (IOException e) {
                System.err.print("FILE_PATH invalid!");
                e.printStackTrace();
            }

        } else {
            System.err.print("FILE_PATH empty!");
        }
    }

    /**
     * 初始化蚂蚁
     */
    private void initAntCommunity(VrpTransportTemp vrpTransportTemp) {
        for (int i = 0; i < antNum; i++) {
            ants[i] = new Ant(i, vrpTransportTemp);
            ants[i].init();
        }
    }

    /**
     * ACO的运行过程
     */
    public Solution run(BaseStretegy baseStretegy) throws Exception {
        SparkConf conf = new SparkConf().setAppName(parameter.appName).setMaster(parameter.master);
        //SparkConf conf = new SparkConf().setAppName(parameter.appName);
        JavaSparkContext ctx = new JavaSparkContext(conf);
        //初始化广播变量
        //System.out.println("broadcast begin..");
        broadcastPheromone = ctx.broadcast(pheromone);
        //System.out.println("broadcast end..");
        int RHOCounter = 0;
        FINISHCounter = 0;
        //进行ITER_NUM次迭代
        for (int i = 0; i < ITER_NUM; i++) {
            //System.out.println("ITER_NUM:" + i);
            //对于每一只蚂蚁
            JavaRDD<Ant> antsRdds = ctx.parallelize(Arrays.asList(ants),8);
            JavaRDD<Ant> improvedAntsRdds = antsRdds.map(x -> {
                        /*Ant tempAnt = x.traceRoad(pheromone);*/
                        Ant tempAnt = x.traceRoad(broadcastPheromone.value());
                        baseStretegy.improveSolution(tempAnt.getSolution());
                        return tempAnt;
                    }
            );
            JavaPairRDD<Double, Ant> pairs = improvedAntsRdds.mapToPair(x -> {
                double len = x.getLength();
                return new Tuple2<Double, Ant>(len, x);
            });
            JavaPairRDD<Double, Ant> sortedPairs = pairs.sortByKey();
            List<Tuple2<Double, Ant>> list = sortedPairs.collect();
            //list.forEach(x-> System.out.println(x._2().getClass() + ": " + x._1()));
            Ant result = list.get(0)._2();
            //System.out.println("result-->"+result);
            //LogUtil.logger.info(result);
            updatePheromoneBySolution(result);
            //更新蚂蚁自身的信息素
            result.updatePheromone();
            //更新信息素
            baseUpdateStrategy.updateByAntRule1(pheromone, bestAnt, vrpTransportTemp,parameter);
            //再次广播变量
            //System.out.println("broadcast begin..");
            broadcastPheromone = ctx.broadcast(pheromone);
            //System.out.println("broadcast end..");
            ++RHOCounter;
            ++FINISHCounter;
            //初始化蚁群
            initAntCommunity(vrpTransportTemp);
            //如果三代以内，最优解的变化值在3之内，则更新RHO
            if (RHOCounter > parameter.RHO_COUNTER) {
                RHOCounter = 0;
                if (DataUtil.le(pre3Solution.calCost() - bestSolution.calCost(), parameter.RHO_THRESHOLD)) {
                    updateRHO(parameter);
                }
                pre3Solution = bestSolution;
            }
            /*if (FINISHCounter >= Parameter.BREAK_COUNTER) {
                LogUtil.logger.info("FINISHCounter--->" + Parameter.BREAK_COUNTER);
                break;
            }*/
            printResult(i,result);
        }
        //打印最佳结果
        //printOptimal();
        return bestSolution;
    }

    /**
     * 打印每次迭代的结果
     * @param iterNum
     */
    private void printResult(int iterNum,Ant ant) {
        //System.out.println(iterNum);
        //System.out.println(ant.getLength());
        System.out.println(bestLen);
    }

    /**
     * 通过得出的解更新信息素
     *
     * @param ant
     */
    private void updatePheromoneBySolution(Ant ant) {
        if (bestSolution == null && bestAnt == null) {
            //logger.info("=========case1==========");
            bestAnt = ant;
            bestLen = bestAnt.getLength();
            bestSolution = bestAnt.getSolution();
            //更新最大最小信息素
            updateMaxMinPheromone(parameter);
            pre3Solution = bestSolution;
            preNSolution = bestSolution;
        }
        //1.若𝑅的用车数大于𝑅∗的 用车数, 则将𝑅中所有边上的信息素进行大量蒸发
        else if (ant.getSolution().getTruckNum() > bestSolution.getTruckNum()) {
            //logger.info("=========case2==========");
            setBaseUpdateStrategy(new UpdateStrategy4Case1());
            baseUpdateStrategy.updatePheBySolution(pheromone, ant.getSolution(),parameter);
        }
        //2.若𝑅的用车数等 于𝑅∗的用车数, 但𝑅的距离/时间费用大于等于𝑅∗相 应的费用, 则将𝑅中所有边上的信息素进行少量蒸发
        else if (ant.getSolution().getTruckNum() == bestSolution.getTruckNum() && DataUtil.ge(ant.getLength(), bestLen)) {
            //logger.info("=========case3==========");
            setBaseUpdateStrategy(new UpdateStrategy4Case2());
            baseUpdateStrategy.updatePheBySolution(pheromone, ant.getSolution(),parameter);
        } else {
            //logger.info("=========case4==========");
            bestAnt = ant;
            bestLen = bestAnt.getLength();
            bestSolution = bestAnt.getSolution();
            preNSolution = bestSolution;
            FINISHCounter = 0;
            //更新最大最小信息素
            updateMaxMinPheromone(parameter);
        }
    }

    private void updateRHO(Parameter parameter) {
        //System.out.println("ACO.updateRHO");
        parameter.RHO *= 1.05;
        parameter.RHO = DataUtil.ge(parameter.RHO, 1.0) ? 0.99 : parameter.RHO;
        //System.out.println("RHO--->" + Parameter.RHO);
    }

    /**
     * 更新最大最小信息素
     */
    private void updateMaxMinPheromone(Parameter parameter) {
        parameter.PHEROMONE_MAX = calPheromoneMax(bestLen, vrpTransportTemp.clientNum);
        parameter.PHEROMONE_MIN = calPheromoneMin(parameter.PHEROMONE_MAX);
    }

    /**
     * 计算最小信息素
     * 𝜏min = 𝜏max/20
     *
     * @param pheromoneMax
     * @return
     */
    private Double calPheromoneMin(Double pheromoneMax) {
        return pheromoneMax / parameter.pheSpan;
    }

    /**
     * 计算最大信息素
     * 𝜏max = 𝐶/(𝐿(𝑅∗)×𝑛×(1−𝜌))
     *
     * @param bestLen
     * @param clientNum
     * @return
     */
    private Double calPheromoneMax(double bestLen, Integer clientNum) {
        return parameter.C / bestLen * (clientNum - 1) * (1 - parameter.RHO);
    }


    /**
     * 打印最佳结果
     */
    private void printOptimal() {
        System.out.println("The optimal length is: " + bestLen);
        System.out.println("The optimal tour is: ");
        System.out.println(bestSolution);
        /*System.out.println("The value of pheromone:");
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j < pheromone[i].length; j++) {
                System.out.print(pheromone[i][j] + "\t");
            }
            System.out.print("\n");
        }*/
    }

    public void setBaseUpdateStrategy(BaseUpdateStrategy baseUpdateStrategy) {
        this.baseUpdateStrategy = baseUpdateStrategy;
    }

    public void setStretegy(BaseStretegy stretegy) {
        this.stretegy = stretegy;
    }
}
