package evolution.algorithms.select;

import evolution.algorithms.normalize.Normalizer;
import evolution.core.Genome;
import evolution.core.Population;
import evolution.diagnostics.Log;
import evolution.diagnostics.Record;

public class SingleGenNorm implements Selector {

	Normalizer normalizer;
	Log log;

	public SingleGenNorm(Normalizer normalizer) {
		this.normalizer = normalizer;
		log = null;
	}

	@Override
	public Population nextGeneration(Population current) {
		double[] breedRates = normalizer.normalize(current); // Mapped by index
		Population next = new Population(current.gen + 1);
		for (int i = 0; i < current.size(); i++) {
			Genome parent1 = current.get(selectRandom(breedRates));
			Genome parent2 = current.get(selectRandom(breedRates));
			Genome child = parent1.breed(parent2);
			next.add(child);
			if (log != null) {
				log.add(new Record(next.gen, child, parent1, parent2));
			}
		}

		return next;
	}

	// Returns the index of a random genome based on given breedRates array
	private int selectRandom(double[] breedRates) {
		double acc = 0;
		double a = Math.random();
		// Accumulate breed rates until they pass the random value
		for (int i = 0; i < breedRates.length; i++) {
			acc += breedRates[i];
			if (acc > a) {
				return i;
			}
		}
		return -1; // This means rates don't add up to 1.0
	}

	@Override
	public void enableLogging(Log log) {
		this.log = log;
	}
}
