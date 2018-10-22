package sco;

import java.io.Serializable;

public class Param<T> implements Serializable {
	
	private static final long serialVersionUID = -2948861334151666048L;
	
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
