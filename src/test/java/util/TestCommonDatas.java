package util;

import org.junit.Test;
import parameter.Parameter;
import vrp.VRP;

import java.io.IOException;

/**
 * Created by ab792 on 2016/12/30.
 * 需要从文件读入的信息
 */
public class TestCommonDatas {

    @Test
    public void test() throws IOException {
        VRP.importDataFromAVRP(Parameter.FILE_PATH);

    }

}
