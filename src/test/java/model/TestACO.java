package model;

import org.junit.Test;
import util.ConstUtil;
import util.DataUtil;

import java.io.IOException;

/**
 * Created by ab792 on 2017/1/5.
 */
public class TestACO {
    @Test
    public void test() throws IOException {
        ACO aco = new ACO();
        aco.init("benchmark/A-VRP/A-n32-k5.vrp");
        aco.run();

    }
}
