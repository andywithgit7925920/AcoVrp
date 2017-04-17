package hadoop;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AntTempEntity implements WritableComparable<AntTempEntity> {
	private Text ant;
	private DoubleWritable cost;

	public void readFields(DataInput input) throws IOException {
		//System.out.println("AntTempEntity---readFields");
		///System.out.println("input-->"+input);
		//System.out.println("ant-->"+ant);
		//System.out.println("cost-->"+cost);
		ant.readFields(input);
		cost.readFields(input);
	}

	public void write(DataOutput output) throws IOException {
		System.out.println("AntTempEntity---write");
		ant.write(output);
		cost.write(output);
	}

	public int compareTo(AntTempEntity other) {
		return this.cost.compareTo(other.cost);
	}
    public  void set(Text ant, DoubleWritable cost) {  
        // TODO Auto-generated method stub  
        this.ant=ant;  
        this.cost=cost;  
    }  
	public AntTempEntity() {
		set(new Text(),new DoubleWritable()); 
	}

	public AntTempEntity(Text ant, DoubleWritable cost) {
		super();
		this.ant = ant;
		this.cost = cost;
	}

	public Text getAnt() {
		return ant;
	}

	public void setAnt(Text ant) {
		this.ant = ant;
	}

	public DoubleWritable getCost() {
		return cost;
	}

	public void setCost(DoubleWritable cost) {
		this.cost = cost;
	}

}
