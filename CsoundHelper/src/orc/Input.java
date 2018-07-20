package orc;

import sco.ParamMap;

public class Input extends Value {
	
	public int index;

	public Input(int index) {
		super("p" + index, true);
		this.index = index;
	}
	
	public void mapInput(int i, ParamMap map) {
		mapInput(i, index, map);
	}
	
	@Override
	public void mapInput(int i, int p, ParamMap map) {
		parent.mapInput(i, p, map);
	}

	@Override
	public String read() {
		return alias;
	}
}
