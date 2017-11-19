package table;

public class Invert implements Filter {

	@Override
	public void applyTo(Table table) {
		int tMax = table.data.length;
		int fMax = table.data[0].length;
		for (int t = 0; t < tMax; t++) {
			for (int f = 0; f < fMax; f++) {
				table.data[t][f] = table.kMax - table.data[t][f];
			}
		}
		table.filters.add("invert");
	}

}
