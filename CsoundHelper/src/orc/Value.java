package orc;

import java.util.HashMap;

public abstract class Value {

	public String alias;
	public boolean terminal;
	public Value[] params;

	public void mapInputs(HashMap<Integer, String> map) {
		for (Value param : params) {
			if (param.terminal) {
				if (param.alias.matches("p\\d+")) {
					map.put(Integer.parseInt(param.alias.substring(1)), alias.substring(1));
				}
			} else {
				param.mapInputs(map);
			}
		}
	}
	
	public abstract String read();
}