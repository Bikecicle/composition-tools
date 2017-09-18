package table;

public class Blur implements Filter {

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (data[i][j] == 1) {
					newData[i][j] = 1;
					if (i > 0)
						newData[i - 1][j] = 1;
					if (i < steps - 1)
						newData[i + 1][j] = 1;
					if (j > 0)
						newData[i][j - 1] = 1;
					if (j < res - 1)
						newData[i][j + 1] = 1;
				}
			}
		}
		table.data = newData;
		table.filters.add("blur");
	}

}
