package orc;

import sco.ParamMap;

public abstract class Value {

	public String alias;
	public boolean terminal;
	public Value[] params;
	public Value parent;
	
	public Value(String alias, boolean terminal, Value... params) {
		this.alias = alias;
		this.terminal = terminal;
		this.params = params;
		for (Value param : params)
			param.parent = this;
	}
	
	public void mapInput(int i, int p, ParamMap map) {
		map.put(i, p, alias.substring(1));
	}
	
	public abstract String read();
}