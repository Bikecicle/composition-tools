package sco;

public class Param<T> {
	
	String name;
	T value;
	
	public Param(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public String read() {
		return value.toString();
	}
}
