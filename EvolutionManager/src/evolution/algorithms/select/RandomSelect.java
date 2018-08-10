package evolution.algorithms.select;

import evolution.core.Genome;
import evolution.core.Population;
import evolution.diagnostics.Log;
import evolution.diagnostics.Record;

public class RandomSelect implements Selector {

	@Override
	public Population nextGeneration(Population current, int nextSize, Log log) {
		Population next = new Population(current.gen + 1);
		for (int i = 0; i < nextSize; i++) {
			Genome parent1 = current.get((int) (Math.random() * current.size()));
			Genome parent2 = current.get((int) (Math.random() * current.size()));
			Genome child = parent1.breed(parent2);
			next.add(child);
			if (log != null)
				log.add(new Record(next.gen, child, parent1, parent2));
		}
		return next;
	}
}
