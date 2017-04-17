package launch;

import acs.ACO;
import localsearch.DefaultStretegy;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import parameter.Parameter;
import vrp.Solution;

import java.io.PrintStream;

import static parameter.Parameter.*;

/**
 * Created by ab792 on 2017/2/28.
 */
public class Launch {
    /*public static void main(String[] args) throws Exception {
        PrintStream out = System.out;// 保存原输出流
        PrintStream ps2 = new PrintStream("./result2/log.txt" + args[0] + "-" + System.currentTimeMillis());// 创建文件输出流2
        System.setOut(ps2);// 设置使用新的输出流
        getResult(args[0]);
    }

    public static void getResult(String val) throws Exception {
        Solution[] solutions = new Solution[5];
        long time1 = System.currentTimeMillis();
        String fileName = "./solomon/" + val + ".vrp";
        ACO aco = new ACO();
        aco.init(fileName); //初始化蚁群
        Solution solution = aco.run(new DefaultStretegy());
        *//*}*//*
        long time2 = System.currentTimeMillis();
        System.out.println("truckNum :" + solution.getTruckNum());
        System.out.println("time :" + ((time2 - time1) / 1000));
        System.out.println("cost :" + solution.calCost());
    }*/
    public static void main(String[] args) throws Exception {
        //PrintStream out = System.out;// 保存原输出流
        //PrintStream ps2 = new PrintStream("./result2/log.txt" + args[0] + "-" + System.currentTimeMillis());// 创建文件输出流2
        //System.setOut(ps2);// 设置使用新的输出流
        getResult("R101");
    }

    public static void getResult(String val) throws Exception {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\log4j.properties");
        long time1 = System.currentTimeMillis();
        String fileName = "benchmark\\solomon\\R1\\"+val+".vrp";
        ACO aco = new ACO();
        aco.init(fileName); //初始化蚁群
        Solution solution = aco.run(new DefaultStretegy());
        /*}*/
        long time2 = System.currentTimeMillis();
        System.out.println("truckNum :" + solution.getTruckNum());
        System.out.println("time :" + ((time2 - time1) / 1000));
        System.out.println("cost :" + solution.calCost());

        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\log4j.properties");


    }
}
