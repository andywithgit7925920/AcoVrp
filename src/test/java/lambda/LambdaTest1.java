package lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RunnableFuture;

/**
 * Created by ab792 on 2017/3/1.
 */
public class LambdaTest1 {
    @Test
    public void test(){
        testLambda("xxxx","yyy");
    }

    public void testLambda(String x1 ,String y1) {
        /*List<String> list = Arrays.asList("ab","abcdef","aaadfgfgf","dfd","dfdgd","d");
        Collections.sort(list,this::compare);
        System.out.println(list);*/
        /*Runnable run = () -> {
            System.out.println("hello1");
            System.out.println("hello2");
        };*/
        String z = "222";
        LambdaTest2 test2 = (x,y)->{
            System.out.println(this);
            x="333";
            System.out.println(x1);
            return x+y;
        };
        LambdaTest2 test3 = new LambdaTest2() {
            @Override
            public String work(String x, String y) {
                System.out.println(this);
                System.out.println("haha");
                x = "333";
                return x+y;
            }
        };

            testLambda2(test2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int compare(String x, String y) {
        return (x.length() < y.length()) ? -1 : ((x.length() == y.length()) ? 0 : 1);
    }

    public void testLambda2(LambdaTest2 lambdaTest2) {
        System.out.println(lambdaTest2.work("111","222"));
    }
}
