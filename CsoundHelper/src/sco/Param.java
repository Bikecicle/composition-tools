package sco;

public class Param<T> {
	
	public String name;
	public T value;
	
	public Param(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public String read() {
		return value.toString();
	}
}
