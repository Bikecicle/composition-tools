package orc;

import sco.ParamMap;

public class Expression extends Value {

	public String operator;

	public Expression(String operator, Value... params) {
		super("", true, params);
		this.operator = operator;
		this.params = params;
		alias += params[0].alias;
		for (int i = 1; i < params.length; i++)
			alias += " " + operator + " " + params[i].alias;
	}
	
	@Override
	public void mapInput(int i, int p, ParamMap map) {
		parent.mapInput(i, p, map);
	}
	
	@Override
	public String read() {
		String s = "";
		for (Value param : params) {
			if (!param.terminal)
				s += param.read() + "\n";
		}
		return s;
	}
}
