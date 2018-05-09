package grain;

import table.FractalTable;

public class ShredSample implements Generator {

	public static final float STRT = 0f;
	public static final int IWSIZE = 4410;
	public static final int IRANDW = 441;

	private int bandCount;
	private int fMin;
	private int fMax;
	private float sMin;
	private float sMax;
	private int sRes;
	private double duration;
	private FractalTable table;
	private String sample;

	public ShredSample(int bandCount, int fMin, int fMax, float sMin, float sMax, int sRes, double duration, FractalTable table,
			String sample) {
		this.bandCount = bandCount;
		this.fMin = fMin;
		this.fMax = fMax;
		this.sMin = sMin;
		this.sMax = sMax;
		this.sRes = sRes;
		this.duration = duration;
		this.table = table;
		this.sample = sample;
	}

	@Override
	public int gen(Layer layer) {
		int total = (int) (sRes * duration);
		float amp = 0.5f;
		int iband = (fMax - fMin) / bandCount;
		int ifn = layer.addFTable(new SoundfileFTable(sample));
		float slen = 1.0f / sRes;
		for (int i = 0; i < bandCount; i++) {
			int ifreq = fMin + (i * iband) + (iband / 2);
			float[] segments = new float[total];
			double yScaled = 1.0 * i / bandCount;
			for (int j = 0; j < total; j++) {
				double xScaled = 1.0 * j / total;
				segments[j] = 1.0f * table.get(xScaled, yScaled) / table.kMax * (sMax - sMin) + sMin;
			}
			int id = i + 1;
			layer.sequence.add(
					new WarpGrain(STRT, amp, ifreq, iband, ifn, sMin, sMax, segments, slen, id));
		}
		return total * bandCount;
	}
}
