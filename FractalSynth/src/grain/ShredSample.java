package grain;

import table.Table;

public class ShredSample implements Generator {
	
	public static final float STRT = 0f;
	public static final int IWSIZE = 4410;
	public static final int IRANDW = 441;
	public static final int IOVERLAP = 10;

	private int bandCount;
	private int fMin;
	private int fMax;
	private int overlap;
	private int sRes;
	private double duration;
	private Table table;
	private String sample;
	
	public ShredSample(int bandCount, int fMin, int fMax, int overlap, int sRes, double duration, Table table,
			String sample) {
		this.bandCount = bandCount;
		this.fMin = fMin;
		this.fMax = fMax;
		this.overlap = overlap;
		this.sRes = sRes;
		this.duration = duration;
		this.table = table;
		this.sample = sample;
	}
	
	@Override
	public int gen(Layer layer) {
		WarpGrain[] bands = new WarpGrain[bandCount];
		int total = (int) (sRes * duration);
		float amp = 0.5f / bandCount;
		int iband = (fMax - fMin) / bandCount;
		int ifn1 = layer.addFTable(new SoundfileFTable(sample));
		int ifn2 = layer.addFTable(new SineFTable());
		float slen = 1.0f / sRes;
		for (int i = 0; i < bandCount; i++) {
			int ifreq = fMin + (i * iband) + (iband / 2);
			float[] segments = new float[total];
			double yScaled = 1.0 * i / bandCount;
			for (int j = 0; j < total; j++) {
				double xScaled = 1.0 * j / total;
				segments[j] = table.get(xScaled, yScaled);
			}
			int id = i + 1;
			bands[i] = new WarpGrain(STRT, amp, IWSIZE, IRANDW, overlap, ifreq, iband, ifn1, ifn2, segments, slen, id);
		}
		return total * bandCount;
	}

}
