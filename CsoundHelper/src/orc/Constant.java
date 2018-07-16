package orc;

public class Constant<T> extends Value {
	
	T constant;

	public Constant(T constant) {
		this.constant = constant;
		alias = constant.toString();
		terminal = true;
	}

	@Override
	public String read() {
		return constant.toString();
	}
}
