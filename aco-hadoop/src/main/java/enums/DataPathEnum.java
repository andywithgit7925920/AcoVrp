package enums;
/**
 * @author hadoop
 *
 */
public enum DataPathEnum implements BaseEnum{
	
	DATA_INPUT{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "hdfs://localhost:9000/user/hadoop/acoinput/C102.vrp";
		}

		public int getCode() {
			// TODO Auto-generated method stub
			return 0;
		}
	},
	CACHE_PATH{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "hdfs://localhost:9000/user/hadoop/cache/cache.vrp";
		}
		public int getCode() {
			// TODO Auto-generated method stub
			return 1;
		}
	},
	PheromoneData{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "hdfs://localhost:9000/user/hadoop/pheromone/pheromone.vrp";
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
			return "hdfs://localhost:9000/user/hadoop/output";
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
			return "hdfs://localhost:9000/user/hadoop/output/part-r-00000";
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
			return "hdfs://localhost:9000/user/hadoop/ants/ants.vrp";
		}
		public int getCode() {
			// TODO Auto-generated method stub
			return 5;
		}
	};


	
}
