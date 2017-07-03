package visual;

public class ColorTag {
	private int red;
	private int green;
	private int blue;
	private int position;

	public ColorTag(int red, int green, int blue, int position) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public int[] toColor() {
		int[] color = { red, green, blue };
		return color;
	}

	public int[] interpolate(ColorTag other, int pos, int dim) {
		int distance;
		if (this.position < other.getPosition() ){
			distance = other.getPosition() - this.position;
		} else {
			distance = dim - this.position + other.getPosition();
		}
		double alpha = (double)(pos - this.position) / distance;
		int[] color = new int[3];
		color[0] = (int) (this.red * (1.0 - alpha) + other.getRed() * alpha);
		color[1] = (int) (this.green * (1.0 - alpha) + other.getGreen() * alpha);
		color[2] = (int) (this.blue * (1.0 - alpha) + other.getBlue() * alpha);
		return color;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

}
