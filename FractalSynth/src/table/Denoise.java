package table;

public class Denoise implements Filter {

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (data[i][j] == 1) {
					if ((i == 0 || data[i - 1][j] == 0) && (i == steps - 1 || data[i + 1][j] == 0)
							&& (j == 0 || data[i][j - 1] == 0) && (j == res - 1 || data[i][j + 1] == 0)) {
						newData[i][j] = 0;
					} else {
						newData[i][j] = 1;
					}

				}
			}
		}
		table.data = newData;
		table.filters.add("denoise");
	}

}
