package launch;

import acs.ACO;
import localsearch.DefaultStretegy;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import parameter.Parameter;
import vrp.Solution;
import vrp.VRP;

import java.io.PrintStream;

import static parameter.Parameter.*;

/**
 * Created by ab792 on 2017/2/28.
 */
public class Launch {
    public static void main(String[] args) throws Exception {

        //SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        //JavaSparkContext ctx = new JavaSparkContext(conf);
        //String fileName = "benchmark\\solomon\\C1\\C102.vrp";
        PrintStream out = System.out;// 保存原输出流
        //PrintStream ps=new PrintStream("E:/log.txt");// 创建文件输出流1
        /*String[] vals = {"R101","R102","R103","R104","R105","R106","R107","R108","R109","R110","R111","R101","R112",
                "RC101","RC102","RC103","RC104","RC105","RC106","RC107","RC108",
                "RC201","RC202","RC203","RC204","RC205","RC206","RC207","RC208"};*/

        /*for (String val : vals){*/
            PrintStream ps2 = new PrintStream("./result2/log.txt"+args[0]+"-"+System.currentTimeMillis());// 创建文件输出流2
            System.setOut(ps2);// 设置使用新的输出流
            getResult(args[0]);
        /*}*/

    }

    public static void getResult(String val) throws Exception {
        Solution[] solutions = new Solution[5];
        long time1 = System.currentTimeMillis();
        String fileName = "./solomon/" + val + ".vrp";
        /*for (int i = 0; i < solutions.length; i++) {*/
            //System.out.println(ctx.getSparkHome());
            ACO aco = new ACO();
            aco.init(fileName); //初始化蚁群
            Solution solution = aco.run(new DefaultStretegy());
        /*}*/
        long time2 = System.currentTimeMillis();
        System.out.println("truckNum :"+solution.getTruckNum());
        System.out.println("time :"+((time2-time1)/1000));
        System.out.println("cost :"+solution.calCost());
        /*int sumTruckNum = 0;
        double sumCost = 0.0;
        for (int i = 0; i < solutions.length; i++) {
            sumTruckNum+=solutions[i].getTruckNum();
            sumCost+=solutions[i].calCost();
        }
        System.out.println("avg truckNum:"+sumTruckNum/solutions.length);
        System.out.println("avg cost:"+sumCost/solutions.length);
        System.out.println("avg time:"+(time2-time1)/solutions.length/1000);*/
    }

}
