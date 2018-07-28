package orc;

import sco.ParamMap;

public abstract class Value {

	String alias;
	boolean terminal;
	Value[] params;
	Value parent;
	int channels;
	
	public Value(String alias, boolean terminal, int channels, Value... params) {
		this.alias = alias;
		this.terminal = terminal;
		this.channels = channels;
		this.params = params;
		for (Value param : params)
			param.parent = this;
	}
	
	public void mapInput(int i, int p, ParamMap map) {
		map.put(i, p, alias.substring(1));
	}
}