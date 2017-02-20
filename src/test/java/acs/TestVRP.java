package acs;

import org.junit.Test;
import vrp.Parameter;
import vrp.VRP;

import java.io.IOException;

/**
 * Created by ab792 on 2017/2/20.
 */
public class TestVRP {
    @Test
    public void test() throws IOException {
        VRP.importDataFromSolomon(Parameter.filePathSolomon);
    }
}
