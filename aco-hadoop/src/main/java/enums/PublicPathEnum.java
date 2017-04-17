package enums;

/**
 * Created by ab792 on 2017/4/16.
 */
public enum PublicPathEnum  implements BaseEnum{
    DATA_INPUT{
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "benchmark/solomon/";
        }

        public int getCode() {
            // TODO Auto-generated method stub
            return 0;
        }
    },PheromoneData{
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "pheromone/pheromone.vrp";
        }
        public int getCode() {
            // TODO Auto-generated method stub
            return 2;
        }
    },
    DATA_OUTPUT{

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "output";
        }
        public int getCode() {
            // TODO Auto-generated method stub
            return 3;
        }

    },
    DATA_OUTPUT_RESULT{

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "output/part-r-00000";
        }
        public int getCode() {
            // TODO Auto-generated method stub
            return 4;
        }

    },
    ANT_COLONY_PATH{
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "ants/ants.vrp";
        }
        public int getCode() {
            // TODO Auto-generated method stub
            return 5;
        }
    };
}
