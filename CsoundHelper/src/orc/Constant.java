package orc;

public class Constant<T> extends Value {
	
	public T constant;

	public Constant(T constant) {
		super(constant.toString(), true);
		this.constant = constant;
	}

	@Override
	public String read() {
		return alias;
	}
}
