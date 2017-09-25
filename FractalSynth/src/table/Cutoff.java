package table;

public class Cutoff implements Filter {

	private int threshold;

	public Cutoff(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int[][] newData = new int[data.length][table.fRes];
		for (int t = 0; t < data.length; t++) {
			for (int f = 0; f < table.fRes; f++) {
				if (data[t][f] >= threshold)
					newData[t][f] = 1;
				else
					newData[t][f] = 0;
			}
		}
		table.data = newData;
	}

}
