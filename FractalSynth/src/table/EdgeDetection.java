package table;

public class EdgeDetection implements Filter {
	
	private int threshold;
	
	public EdgeDetection(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (i < steps - 1 && Math.abs(data[i][j] - data[i + 1][j]) >= threshold) {
					newData[i][j] = 1;
				} else if (i > 0 && Math.abs(data[i][j] - data[i - 1][j]) >= threshold) {
					newData[i][j] = 1;
				} else if (j < res - 1 && Math.abs(data[i][j] - data[i][j + 1]) >= threshold) {
					newData[i][j] = 1;
				} else if (j > 0 && Math.abs(data[i][j] - data[i][j - 1]) >= threshold) {
					newData[i][j] = 1;
				} else {
					newData[i][j] = 0;
				}
			}
		}
		table.data = newData;
		table.filters.add("edge detection");
	}
}
