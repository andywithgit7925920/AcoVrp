package launch;

import acs.ACO;

/**
 * Created by ab792 on 2017/3/6.
 */
public class Launch {
    public static void main(String[] args) throws Exception {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\log4j.properties");
        String fileName = "benchmark\\solomon\\C102.vrp";
        ACO aco = new ACO();
        aco.init(fileName);
        aco.run();
    }
}
