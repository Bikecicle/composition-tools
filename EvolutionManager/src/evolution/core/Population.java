package evolution.core;

import java.util.ArrayList;
import java.util.Comparator;

public class Population extends ArrayList<Genome> {

	private static final long serialVersionUID = 2173547797951796680L;

	public int gen;

	public Population() {
		super();
		this.gen = 0;
	}

	public Population(int gen) {
		super();
		this.gen = gen;
	}

	public Genome getFittest() {
		return this.get(0);
	}

	@Override
	public boolean add(Genome arg0) {
		boolean success = super.add(arg0);
		sort();
		return success;
	}

	// Sorted from high score to low
	public void sort() {
		super.sort(new Comparator<Genome>() {

			@Override
			public int compare(Genome o1, Genome o2) {
				return (int) (o2.getScore() - o1.getScore());
			}
		});
	}

	@Override
	public String toString() {
		String str = "";
		for (Genome genome : this) {
			str += genome.toString();
		}
		return str;
	}
}
