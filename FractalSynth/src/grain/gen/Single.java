package grain.gen;

import grain.Layer;
import grain.OscGrain;
import sco.SineFTable;

public class Single implements Generator {
	
	double start;
	double duration;
	int frequency;
	
	public Single(double start, double duration, int frequency) {
		this.start = start;
		this.duration = duration;
		this.frequency = frequency;
	}

	@Override
	public int gen(Layer layer) {
		float strt = (float) start;
		float dur = (float) duration;
		float amp = 0.5f;
		int freq = frequency;
		float att = 0.1f;
		float dec = 0.1f;
		double xNorm = 0;
		double yNorm = 0;
		OscGrain note = new OscGrain(strt, dur, amp, freq, att, dec, xNorm, yNorm);
		layer.sequence.add(note);
		layer.addFTable(new SineFTable());
		return 1;
	}
}
