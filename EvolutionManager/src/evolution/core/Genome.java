package evolution.core;

import java.io.Serializable;

public interface Genome extends Serializable {

	public Genome breed(Genome other);
	
	public void randomize();

	public double getScore();

	public int getId();

}
