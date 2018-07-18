package evolution.diagnostics;

import java.util.ArrayList;
import java.util.Comparator;

import evolution.core.Genome;
import evolution.core.Population;

public class PopulationRecord extends ArrayList<Record> {

	private static final long serialVersionUID = -6904396923825570856L;

	public int gen;

	public PopulationRecord(Population pop) {
		super();
		for (Genome g : pop)
			this.add(new Record(g.getId(), g.getScore(), g.toString()));
		this.gen = pop.gen;
	}

	public Record getFittest() {
		return this.get(0);
	}

	@Override
	public boolean add(Record arg0) {
		boolean success = super.add(arg0);
		sort();
		return success;
	}

	// Sorted from high score to low
	public void sort() {
		super.sort(new Comparator<Record>() {

			@Override
			public int compare(Record o1, Record o2) {
				return (int) Math.signum(o2.score - o1.score);
			}
		});
	}
}
