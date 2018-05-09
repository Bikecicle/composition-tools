package grain;

import table.FractalTable;

public class IntegralWarp implements Modifier {
	
	private FractalTable table;
	
	public IntegralWarp(FractalTable table) {
		this.table = table;
	}

	@Override
	public int applyTo(Layer layer) {
		double dur = layer.duration;
		int count = 0;
		for (Grain g : layer.sequence) {
			if (g.gType == Instrument.sample) {
				SampleGrain sg = (SampleGrain) g;
				// Map iteration to time within duration
				sg.sStrt = (float) (dur * table.get(sg.xNorm, sg.yNorm) / table.kMax);
				count++;
			}
		}
		return count;
	}

}
