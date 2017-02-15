package acs;

import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created by ab792 on 2017/1/5.
 */
public class TestACO {
    @Test
    public void test() throws IOException {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\src\\log4j.properties");
        ACO aco = new ACO();
        aco.init("benchmark\\A-VRP\\A-n32-k5.vrp");
        aco.run();

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
