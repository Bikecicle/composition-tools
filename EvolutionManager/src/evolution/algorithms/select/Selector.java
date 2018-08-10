package evolution.algorithms.select;

import evolution.core.Population;
import evolution.diagnostics.Log;

public interface Selector {

	public abstract Population nextGeneration(Population current, int nextSize, Log log);
	
}
