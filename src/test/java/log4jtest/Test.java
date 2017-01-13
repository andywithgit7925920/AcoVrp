package log4jtest;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import static util.LogUtil.logger;

/**
 * Created by ab792 on 2017/1/13.
 */
public class Test {


    public static void main(String[] args) {
        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\ab792\\IdeaProjects\\AcoVrp\\src\\log4j.properties");
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
}
