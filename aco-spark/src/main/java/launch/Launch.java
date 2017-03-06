package launch;
import acs.ACO;
import localsearch.DefaultStretegy;
import vrp.VRP;

import static parameter.Parameter.*;
/**
 * Created by ab792 on 2017/2/28.
 */
public class Launch {
    public static void main(String[] args) throws Exception {
        //SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        //JavaSparkContext ctx = new JavaSparkContext(conf);
        String fileName = "benchmark\\solomon\\C102.vrp";
        long time1 = System.currentTimeMillis();
        //System.out.println(ctx.getSparkHome());
        ACO aco = new ACO();
        aco.init(fileName); //初始化蚁群
        /*ctx.broadcast(VRP.clientNum);
        ctx.broadcast(VRP.distance);
        ctx.broadcast(VRP.clientDemandArr);*/
       // sc.broadcast()
        //创建信息的broadcast
        aco.run(new DefaultStretegy());
        long time2 = System.currentTimeMillis();
        System.out.println("spend time--->"+(time2-time1));
    }
    /*public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);
        //List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<String> lines = sc.textFile("benchmark\\sparktest\\data.txt");
        JavaPairRDD<String,Integer> pairs = lines.mapToPair(s->new Tuple2<String, Integer>(s,1));
        JavaPairRDD<String,Integer> counts = pairs.reduceByKey((a,b)->a+b);
        counts.sortByKey();
        counts.collect();
        *//*JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);*//*
        //System.out.println("totalLength--->"+totalLength);
        *//*JavaRDD<Integer> distData = sc.parallelize("");
        JavaRDD<String> lines = sc.textFile("data.txt");*//*

    }*/

}
