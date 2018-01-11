package grain;

import java.util.ArrayList;
import java.util.List;

public class OverlaySample implements Modifier {

	private float fMod;
	private int band;
	private int fID;

	public OverlaySample(float fMod, int band, int fID) {
		super();
		this.fMod = fMod;
		this.band = band;
		this.fID = fID;
	}

	@Override
	public int applyTo(Layer layer) {
		List<Grain> matrix = new ArrayList<Grain>();
		int count = 0;
		for (Grain g : layer.sequence) {
			if (g.gType == Instrument.osc) {
				OscGrain og = (OscGrain) g;
				SampleGrain sg = new SampleGrain(og.strt, og.dur, og.amp, og.att, og.dec, fMod, og.strt, og.freq, band,
						fID, og.fMin, og.fMax, layer.duration);
				matrix.add(sg);
				count++;
			} else {
				matrix.add(g);
			}
		}
		layer.clear();
		layer.addGrains(matrix);
		return count;
	}

}
