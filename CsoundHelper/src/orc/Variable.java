package orc;

public class Variable extends Value {

	public String operator;

	public Variable(String type, String name, String operator, Value... params) {
		super(type + name, false, params);
		this.operator = operator;
	}
	
	@Override
	public String read() {
		String s = "";
		for (Value value : params) {
			if (!value.terminal)
				s += value.read() + "\n";
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
