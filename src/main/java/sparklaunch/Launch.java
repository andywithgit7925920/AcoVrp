package sparklaunch;
import acs.ACO;
import localsearch.DefaultStretegy;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;
import vrp.VRP;

import java.util.Arrays;
import java.util.List;
import static parameter.Parameter.*;
/**
 * Created by ab792 on 2017/2/28.
 */
public class Launch {
    public static void main(String[] args) throws Exception {
        //SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        //JavaSparkContext ctx = new JavaSparkContext(conf);
        String fileName = "benchmark\\solomon\\C102.vrp";
        //System.out.println(ctx.getSparkHome());
        ACO aco = new ACO();
        aco.init(fileName); //初始化蚁群
        /*ctx.broadcast(VRP.clientNum);
        ctx.broadcast(VRP.distance);
        ctx.broadcast(VRP.clientDemandArr);*/
       // sc.broadcast()
        //创建信息的broadcast

        aco.run(new DefaultStretegy());
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
