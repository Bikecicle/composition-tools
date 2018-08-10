package evolution.algorithms.select;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import evolution.core.Genome;
import evolution.core.Population;
import evolution.diagnostics.Log;
import evolution.diagnostics.Record;

public class ExemplarInject implements Selector{

	Selector subSelect;
	List<Genome> exemplars;
	
	public ExemplarInject(Selector subSelect, Genome... exemplars) {
		this.subSelect = subSelect;
		this.exemplars = new ArrayList<>();
		for (Genome exemplar : exemplars)
			this.exemplars.add(exemplar);
	}

	@Override
	public Population nextGeneration(Population current, int nextSize, Log log) {
		Population next = subSelect.nextGeneration(current, nextSize, log);
		if (!exemplars.isEmpty()) {
			Genome target = next.remove((int) (Math.random() * next.size()));
			Genome exemplar = exemplars.get((int) (Math.random() * exemplars.size()));
			Genome child = target.breed(exemplar);
			next.add(child);
			if (log!= null)
				log.add(new Record(next.gen, child, target, exemplar));
		}
		return next;
	}
}
