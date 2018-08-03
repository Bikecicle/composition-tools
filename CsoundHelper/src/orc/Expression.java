package orc;

import sco.ParamMap;

public class Expression extends Value {

	public Expression(String format, Value... params) {
		super(null, false, 1, params);
		this.params = params;
		Object[] strs = new String[params.length];
		for (int i = 0; i < params.length; i++)
			strs[i] = params[i].alias;
		alias = String.format(format, strs);
	}

	@Override
	public void mapInput(int i, int p, ParamMap map) {
		parent.mapInput(i, p, map);
	}

	@Override
	public String toString() {
		String s = "";
		for (Value param : params) {
			if (!param.terminal & !param.defined) {
				s += param + "\n";
			}
		}
		defined = true;
		return s;
	}
}
