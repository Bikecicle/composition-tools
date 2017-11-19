package grain;

public class UniformShift implements Modulator {
	
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
