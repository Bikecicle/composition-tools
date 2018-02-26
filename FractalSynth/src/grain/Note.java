package grain;

public class Note implements Generator {
	
	double start;
	double duration;
	int frequency;
	
	public Note(double start, double duration, int frequency) {
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
		int fMin = freq;
		int fMax = freq;
		double totalDur = strt + dur;
		OscGrain note = new OscGrain(strt, dur, amp, freq, att, dec, fMin, fMax, totalDur);
		layer.sequence.add(note);
		layer.addFTable(new SineFTable());
		return 1;
	}
}
