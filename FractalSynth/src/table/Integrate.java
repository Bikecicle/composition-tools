package table;

public class Integrate implements Filter {
	
	private double maxR; // Seconds to reach kMax at max speed
	private double zeroP; // Position of zero in kMax (0-1)
	private double startP; // Starting value in max (0-1)
	private int max;
	private int zero;
	private int start;
	private boolean rel;
	
	public Integrate(double maxR, double zeroP, double startP) {
		this.maxR = maxR;
		this.zeroP = zeroP;
		this.startP = startP;
		rel = true;
	}
	
	public Integrate(int max, int zero, int start) {
		this.max = max;
		this.zero = zero;
		this.start = start;
		rel = false;
	}
	
	@Override
	public void applyTo(Table table) {
		if (rel) {
			max = (int) (table.kMax * table.tRes * maxR);
			zero = (int) (table.kMax * zeroP);
			start = (int) (max * startP);
		}
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		for (int j = 0; j < res; j++) {
			int sum = start;
			for (int i = 0; i < steps; i++) {
				sum += data[i][j] - zero;
				int val = sum % max;
				if (val < 0 )
					val += max;
				newData[i][j] = val;
			}
		}
		table.data = newData;
		table.kMax = max;
		newData = null;
		table.filters.add("integral");

	}

}
