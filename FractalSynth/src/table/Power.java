package table;

public class Power implements Filter {

	private double exponent;
	
	public Power(double exponent) {
		this.exponent = exponent;
	}
	
	@Override
	public void applyTo(Table table) {
		int tMax = table.data.length;
		int fMax = table.data[0].length;
		for (int t = 0; t < tMax; t++) {
			for (int f = 0; f < fMax; f++) {
				table.data[t][f] = (int) (Math.pow(table.data[t][f], exponent) / Math.pow(table.kMax, exponent) * table.kMax);
			}
		}
		table.kMax = (int) Math.pow(table.kMax, exponent);
		table.filters.add("power: " + exponent);
	}

}
