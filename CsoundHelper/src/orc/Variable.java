package orc;

public class Variable extends Value {

	String operator;

	public Variable(String type, String name, String operator, Value... params) {
		super(type + name, false, 1, params);
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Value value : params) {
			if (!value.terminal)
				s += value + "\n";
		}
		s += alias + " " + operator + " ";
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				s += ", ";
			s += params[i].alias;
		}
		return s;
	}
}
