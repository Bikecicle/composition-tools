package visual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Palette {

	public int dim;
	public List<ColorTag> key;
	public int[][] gradient;

	public Palette(int dim) {
		this.dim = dim;
		this.key = new ArrayList<ColorTag>();
		this.gradient = new int[dim][3];
		calculateGradient();
	}

	public void add(ColorTag color) {
		key.add(color);
		key.sort(new Comparator<ColorTag>() {

			@Override
			public int compare(ColorTag arg0, ColorTag arg1) {
				return arg0.getPosition() - arg1.getPosition();
			}
		});
		calculateGradient();
	}

	private void calculateGradient() {
		int size = key.size();
		if (size == 0) {
			for (int i = 0; i < dim; i++) {
				gradient[i][0] = 0;
				gradient[i][1] = 0;
				gradient[i][2] = 0;
			}
		} else if (size == 1) {
			for (int i = 0; i < dim; i++) {
				gradient[i] = key.get(0).toColor();
			}
		} else {
			ColorTag a = key.get(size - 1);
			ColorTag b = key.get(0);
			int keyNum = 1;
			for (int i = 0; i < dim; i++) {
				if (i >= b.getPosition() && keyNum <= size) {
					a = b;
					if (keyNum == size) {
						b = key.get(0);
						keyNum++;
					} else {
						b = key.get(keyNum++);
					}

				}
				gradient[i] = a.interpolate(b, i, dim);
			}
		}
	}

	public int[] get( int i ) {
		return gradient[i % dim];
	}
	
	@Override
	public String toString() {
		if (key.isEmpty()) {
			return null;
		}
		String str = "[" + key.get(0);
		for (int i = 1; i < key.size(); i ++) {
			str += ", " + key.get(i);
		}
		return str + "]";
	}
}
