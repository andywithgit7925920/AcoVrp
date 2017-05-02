package hadoop;

import acs.Ant;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import updatestrategy.BaseUpdateStrategy;
import util.DataUtil;
import util.GsonUtil;
import util.HDFSUtil;

import java.io.IOException;

public class ReducerStep2 extends
		Reducer<IntWritable, AntTempEntity, NullWritable, Text> {
	
	private BaseUpdateStrategy baseUpdateStrategy;  //信息素更新策略
	protected void reduce(IntWritable key, Iterable<AntTempEntity> values,
			Context context) throws IOException, InterruptedException {
		//System.out.println("==================ReducerStep2.reduce-begin===================");
		// System.out.println("key--->"+key);
		// System.out.println("values--->"+values);
		// find the best solution

		//DEBUG
		StringBuilder sb = new StringBuilder();
		for (AntTempEntity val : values) {
			sb.append(val.getAnt().toString()).append("||");
		}
		HDFSUtil.CreateFile("temp2/"+System.currentTimeMillis()+"/data.vrp",sb.toString());


		Ant bestAnt = null;
		Double bestLength = Double.MAX_VALUE;
		for (AntTempEntity val : values) {
			Ant ant = GsonUtil.gson
					.fromJson(val.getAnt().toString(), Ant.class);
			Double len = val.getCost().get();
			//System.out.println("len--->" + len);
			if (DataUtil.le(len, bestLength)) {
				bestLength = len;
				bestAnt = ant;
			}
		}
		bestAnt.updatePheromone();
		context.write(null, new Text(GsonUtil.gson.toJson(bestAnt)));
		//context.write(null, new Text(GsonUtil.gson.toJson(bestAnt)));
		//System.out.println("bestAnt--->"+bestAnt);
		//System.out.println("bestLength--->"+bestLength);
        //System.out.println("==================ReducerStep2.reduce-end===================");
	}

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		//System.out.println("==================ReducerStep2.setup===================");
	}

}
