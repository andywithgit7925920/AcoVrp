package launch;

import acs.ACO;
import enums.DataPathEnum;
import enums.PublicPathEnum;
import localsearch.DefaultStretegy;
import vrp.Solution;

import java.io.PrintStream;

/**
 * Created by ab792 on 2017/3/6.
 */
public class LaunchDriver {
    public static void main(String[] args) throws Exception {
        /*long start = System.currentTimeMillis();
		org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
        //PrintStream print=new PrintStream("logs.mylog.txt");  //写好输出位置文件；  
        //System.setOut(print);  
        System.out.println("LaunchDriver.main ===========begin");
		ACO aco = new ACO();
		aco.init(DataPathEnum.DATA_INPUT.toString());
		aco.run();
		System.out.println("LaunchDriver.main ===========end");
		long end = System.currentTimeMillis();
		System.out.println("during time-->"+(end-start));*/
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
        Solution solution = aco.run();
        long time2 = System.currentTimeMillis();
        System.out.println("truckNum :" + solution.getTruckNum());
        System.out.println("time :" + ((time2 - time1) / 1000));
        System.out.println("cost :" + solution.calCost());
    }
}
