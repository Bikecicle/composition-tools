package orc;

public class Input extends Statement {

	int index;
	String paramName;

	public Input(int index, String paramName) {
		super("p" + index, true, 1);
		this.index = index;
		this.paramName = paramName;
	}

	@Override
	public String toString() {
		defined = true;
		return alias;
	}
}
