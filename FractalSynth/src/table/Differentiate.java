package table;

public class Differentiate implements Filter {

	@Override
	public void applyTo(Table table) {
		int tMax = table.data.length;
		int fMax = table.data[0].length;
		int[][] data = new int[tMax][fMax];
		for (int t = 0; t < tMax; t++) {
			for (int f = 0; f < fMax; f++) {
				int c = 0;
				data[t][f] = 0;
				if (t < tMax - 1) {
					data[t][f] += Math.abs(table.data[t][f] - table.data[t + 1][f]);
					c++;
				}
				if (t > 0) {
					data[t][f] += Math.abs(table.data[t][f] - table.data[t - 1][f]);
					c++;
				}
				if (f < fMax - 1) {
					data[t][f] += Math.abs(table.data[t][f] - table.data[t][f + 1]);
					c++;
				}
				if (f > 0) {
					data[t][f] += Math.abs(table.data[t][f] - table.data[t][f - 1]);
				}
				data[t][f] /= c;
			}
		}
		table.data = data;
		table.filters.add("differentiate");
	}

}
