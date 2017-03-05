package acs;

import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import localsearch.DefaultStretegy;
import org.junit.Test;
import parameter.Parameter;

import java.util.Random;

/**
 * Created by ab792 on 2017/1/5.
 */
public class TestACO {
    @Test
    public void testBatch() throws Exception {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\src\\log4j.properties");

        String[] arr = {"C101","C102","C103","C104","C105","C106","C107","C108","C109"};
        for (String item : arr){
            String fileName = "benchmark\\solomon\\"+item+".vrp";
            ACO aco = new ACO();
            aco.init(fileName);
            aco.run(new DefaultStretegy());
        }
        /*String filePath = Parameter.FILE_PATH_SOLOMON;
        aco.init(filePath);
        aco.run();*/

    }
    @Test
    public void testSingle() throws Exception {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\src\\log4j.properties");

        //String[] arr = {"C102","C103","C104","C109"};
        String[] arr = {"C102"};
        for (String item : arr){
            String fileName = "benchmark\\solomon\\"+item+".vrp";
            ACO aco = new ACO();
            aco.init(fileName);
            aco.run(new DefaultStretegy());
        }
        /*String filePath = Parameter.FILE_PATH_SOLOMON;
        aco.init(filePath);
        aco.run();*/

    }
    @Test
    public void test1(){
        for (int i=0;i<100;i++){
            //System.out.println(Math.random());
            Random random = new Random(1);
            double selectP = random.nextDouble();
            System.out.println(selectP);
        }
    }
}
