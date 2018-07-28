package orc;

import sco.ParamMap;

public class Expression extends Value {

	public Expression(String format, Value... params) {
		super(String.format(format, (Object[]) params), true, 1, params);
		this.params = params;
	}

	@Override
	public void mapInput(int i, int p, ParamMap map) {
		parent.mapInput(i, p, map);
	}

	@Override
	public String toString() {
		String s = "";
		for (Value param : params) {
			if (!param.terminal)
				s += param + "\n";
		}
		return s;
	}
}
