package evolution.diagnostics;

public class Record {

	public int id;
	public double score;
	public String details;
	
	public boolean hasParents;
	public int parent1;
	public int parent2;
	
	public Record(int id, double score, String details) {
		hasParents = false;
	}
}
