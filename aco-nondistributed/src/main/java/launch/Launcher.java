package launch;

import acs.ACO;
import enums.ParameterComEnum1;
import enums.ParameterComEnum2;
import org.junit.Test;
import vrp.Solution;

/**
 * Created by ab792 on 2017/3/6.
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        /*ParameterComEnum1[] parameterComEnums = ParameterComEnum1.values();
        for (ParameterComEnum1 parameterComEnum : parameterComEnums){
            System.out.println(parameterComEnum);
            getBestResult("R102",parameterComEnum);
        }*/
        ParameterComEnum2[] parameterComEnum2s = ParameterComEnum2.values();
        for (ParameterComEnum2 parameterComEnum2:parameterComEnum2s){
            System.out.println(parameterComEnum2);
            getBestResult("C102",parameterComEnum2);

        }
    }
    public static void getBestResult(String val, ParameterComEnum1 pce) throws Exception{
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\log4j.properties");
            Solution[] solutions = new Solution[5];
            for (int i=0;i<solutions.length;i++){
                //String fileName = "benchmark\\solomon\\C1\\"+val+".vrp";
                String fileName = "benchmark\\solomon\\C1\\"+val+".vrp";
                ACO aco = new ACO();
                aco.init(fileName,pce);
                Solution solution = aco.run();
                solutions[i] = solution;
            }
            //计算平均值
            int sumTruckNum = 0;
            double sumCost = 0.0;
            double sumItter = 0;
            for (int i=0;i<solutions.length;i++){
                sumTruckNum += solutions[i].getTruckNum();
                sumCost+=solutions[i].calCost();
                sumItter+=solutions[i].getIterNum();
            }
            System.out.println("avg truckNum :"+sumTruckNum/solutions.length);
            System.out.println("avg cost :"+sumCost/solutions.length);
        System.out.println("ave iter:"+sumItter/solutions.length);
    }
    public static void getBestResult(String val, ParameterComEnum2 pce) throws Exception{
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\log4j.properties");
        Solution[] solutions = new Solution[5];
        for (int i=0;i<solutions.length;i++){
            //String fileName = "benchmark\\solomon\\C1\\"+val+".vrp";
            String fileName = "benchmark\\solomon\\C1\\"+val+".vrp";
            ACO aco = new ACO();
            aco.init(fileName,pce);
            Solution solution = aco.run();
            solutions[i] = solution;
        }
        //计算平均值
        int sumTruckNum = 0;
        double sumCost = 0.0;
        double sumItter = 0;
        for (int i=0;i<solutions.length;i++){
            sumTruckNum += solutions[i].getTruckNum();
            sumCost+=solutions[i].calCost();
            sumItter+=solutions[i].getIterNum();
        }
        System.out.println("avg truckNum :"+sumTruckNum/solutions.length);
        System.out.println("avg cost :"+sumCost/solutions.length);
        System.out.println("ave iter:"+sumItter/solutions.length);
    }
    @Test
    public void fun1(){
        System.out.println((1E2)/10);
    }

}
