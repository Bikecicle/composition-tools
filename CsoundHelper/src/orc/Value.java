package orc;

import sco.ParamMap;

public abstract class Value {

	public String alias;
	public boolean terminal;
	public Value[] params;

	public void mapInputs(int id, ParamMap map) {
		for (Value param : params) {
			if (param.terminal) {
				if (param.alias.matches("p\\d+")) {
					map.put(id, Integer.parseInt(param.alias.substring(1)), alias.substring(1));
				}
			} else {
				param.mapInputs(id, map);
			}
		}
	}
	
	public abstract String read();
}