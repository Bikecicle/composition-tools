package sco;

public class ArrayParam<T> extends Param<T[]> {
	
	int index;
	
	public ArrayParam(String name, T[] value) {
		super(name, value);
		index = 0;
	}
	
	@Override
	public String read() {
		String s = value[index].toString();
		index++;
		return s;
	}
}
