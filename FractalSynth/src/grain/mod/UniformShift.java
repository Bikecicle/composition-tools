package grain.mod;

import grain.Grain;
import grain.Layer;

public class UniformShift implements Modifier {
	
	private double rMax;
	
	public UniformShift(double rMax) {
		this.rMax = rMax;
	}

	@Override
	public int applyTo(Layer layer) {
		for (Grain grain : layer.sequence) {
			double d = Math.random() * 2 * rMax - rMax;
			grain.strt += d;
		}
		return layer.sequence.size();
	}

}
