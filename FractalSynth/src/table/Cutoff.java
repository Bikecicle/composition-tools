package table;

public class Cutoff implements Filter {

	private int threshold;

	public Cutoff(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int[][] newData = new int[table.length()][table.height()];
		for (int t = 0; t < table.length(); t++) {
			for (int f = 0; f < table.height(); f++) {
				if (data[t][f] >= threshold)
					newData[t][f] = 1;
				else
					newData[t][f] = 0;
			}
		}
		table.data = newData;
	}

}
