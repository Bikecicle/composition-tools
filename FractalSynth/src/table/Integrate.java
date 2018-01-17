package table;

public class Integrate implements Filter {
	
	private int zero;
	
	public Integrate(int zero) {
		this.zero = zero;
	}

	@Override
	public void applyTo(Table table) {
		int[][] data = table.data;
		int steps = data.length;
		int res = data[0].length;
		int[][] newData = new int[steps][res];
		for (int j = 0; j < res; j++) {
			int sum = 0;
			for (int i = 0; i < steps; i++) {
				sum += data[i][j] - zero;
				sum = sum >= 0 ? sum : 0;
				newData[i][j] = sum; 
			}
		}
		table.data = newData;
		newData = null;
		table.filters.add("integral");

	}

}
