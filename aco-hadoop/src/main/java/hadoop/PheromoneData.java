package hadoop;

import java.util.Arrays;

/**
 * for each iteration,store the pheromone data
 * @author hadoop
 *
 */
public class PheromoneData {
	@Override
	public String toString() {
		return "PheromoneData [pheromone=" + Arrays.toString(pheromone) + "]";
	}

	private double[][] pheromone;   //信息素矩阵

	public double[][] getPheromone() {
		return pheromone;
	}

	public void setPheromone(double[][] pheromone) {
		this.pheromone = pheromone;
	}
	
}
