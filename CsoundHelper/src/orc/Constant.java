package orc;

public class Constant<T> extends Value {
	
	T constant;

	public Constant(T constant) {
		super(constant.toString(), true, 1);
		this.constant = constant;
	}

	@Override
	public String toString() {
		return alias;
	}
}
