package evolution.diagnostics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import evolution.core.Genome;
import evolution.core.Population;

public class Log implements Serializable {

	private static final long serialVersionUID = 5378570329356066060L;

	private List<PopulationRecord> pops;

	public Log() {
		pops = new ArrayList<>();
	}

	public void addRelationship(Genome parent1, Genome parent2, Genome child) {
		
		// TODO tree.add(new Relationship(parent1, parent2, child));
	}

	public double maxScore() {
		double max = Double.NEGATIVE_INFINITY;
		for (PopulationRecord pop : getPops()) {
			double score = pop.get(0).score;
			if (score > max)
				max = score;
		}
		return max;
	}

	public double minScore() {
		double min = Double.POSITIVE_INFINITY;
		for (PopulationRecord pop : getPops()) {
			double score = pop.get(pop.size() - 1).score;
			if (score < min)
				min = score;
		}
		return min;
	}
	
	public void addPop(Population pop) {
		pops.add(new PopulationRecord(pop));
	}

	public List<PopulationRecord> getPops() {
		return pops;
	}
}
