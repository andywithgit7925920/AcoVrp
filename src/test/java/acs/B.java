package acs;

/**
 * Created by ab792 on 2017/1/10.
 */
public class B {
    A a;
    C c;
    public B(){
        a = new A();
        c = new C();
    }
    public void test(){
        a.testA();
        c.testC();
    }
    public static void main(String[] args){
        B b = new B();
        b.test();
    }
}
