package grain;

public class NoiseBand implements Generator {
	
	private int fMin;
	private int fMax;
	private double density;
	private double duration;

	public NoiseBand(int fMin, int fMax, double density, double duration) {
		super();
		this.fMin = fMin;
		this.fMax = fMax;
		this.density = density;
		this.duration = duration;
	}

	@Override
	public int gen(Layer layer) {
		int total = (int) (density * duration);
		int width = fMax - fMin;
		for (int i = 0; i < total; i++) {
			float strt = (float) (Math.random() * duration);
			float dur = Grain.DEFAULT_DUR;
			float amp = Grain.DEFAULT_AMP;
			int freq = (int) (Math.random() * width) + fMin;
			float att = Grain.DEFAULT_ATT;
			float dec = Grain.DEFAULT_DEC;
			OscGrain g = new OscGrain(strt, dur, amp, freq, att, dec, fMin, fMax, duration);
			layer.sequence.add(g);
		}
		layer.addFTable(new SineFTable());
		return total;
	}

}
