package table;

public class Amplify implements Filter {

	private double gain;

	public Amplify(double gain) {
		this.gain = gain;
	}

	@Override
	public void applyTo(Table table) {
		int tMax = table.data.length;
		int fMax = table.data[0].length;
		for (int t = 0; t < tMax; t++) {
			for (int f = 0; f < fMax; f++) {
				table.data[t][f] *= gain;
			}
		}
		table.kMax *= gain;
		table.filters.add("amplify:" + gain);
	}

}
