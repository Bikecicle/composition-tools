package evolution.diagnostics;

import java.util.ArrayList;
import evolution.core.Genome;
import evolution.core.Population;

public class Log extends ArrayList<Record> {

	private static final long serialVersionUID = 5378570329356066060L;
	
	public double maxScore() {
		double max = Double.MIN_VALUE;
		for (Record record : this) {
			if (record.score > max)
				max = record.score;
		}
		return max;
	}

	public double minScore() {
		double min = Double.MAX_VALUE;
		for (Record record : this) {
			if (record.score < min)
				min = record.score;
		}
		return min;
	}
	
	public int maxGen() {
		int max = Integer.MIN_VALUE;
		for (Record record : this) {
			if (record.gen > max)
				max = record.gen;
		}
		return max;
	}
	
	public void recordPopulation(Population pop) {
		for (Genome g : pop) {
			add(new Record(pop.gen, g));
		}
	}
}
