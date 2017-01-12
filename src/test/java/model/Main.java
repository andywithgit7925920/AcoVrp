package model;

/**
 * Created by ab792 on 2017/1/10.
 */
public class Main {
    Insert insert;
    public Main(){
        insert = new GreddyInsert();
    }

    public void setInsert(Insert insert) {
        this.insert = insert;
    }

    public static void main(String[] args){
        Main main = new Main();
        main.myInsert();
        main.setInsert(new DynamicInsert());
        main.myInsert();
    }
    public void myInsert(){
        insert.insert(null);
    }
}
