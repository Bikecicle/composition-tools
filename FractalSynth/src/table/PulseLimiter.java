package table;

public class PulseLimiter implements Filter {

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		int chain = 0;
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (data[i][j] == 1) {
					chain++;
					if (j == res - 1) {
						newData[i][j - chain / 2] = 1;
						chain = 0;
					}
				} else if (chain > 0) {
					newData[i][j - chain / 2] = 1;
					chain = 0;
				}
			}
		}
		for (int j = 0; j < res; j++) {
			for (int i = 0; i < steps; i++) {
				if (data[i][j] == 1) {
					chain++;
					if (i == steps - 1) {
						newData[i - chain / 2][j] = 1;
						chain = 0;
					}
				} else if (chain > 0) {
					newData[i - chain / 2][j] = 1;
					chain = 0;
				}
			}
		}
		table.data = newData;
		table.filters.add("pulse limiter");
	}

}
