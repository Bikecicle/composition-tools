package evolution.diagnostics;

import evolution.core.Genome;

public class Record {

	public int gen;
	public int id;
	public double score;
	public String details;
	
	public int[] parentIds;
	
	public Record(int gen, Genome genome, Genome...parents) {
		this.gen = gen;
		id = genome.getId();
		score = genome.getScore();
		details = genome.toString();
		parentIds = new int[parents.length];
		for (int i = 0; i < parents.length; i++) {
			parentIds[i] = parents[i].getId();
		}
	}
}
