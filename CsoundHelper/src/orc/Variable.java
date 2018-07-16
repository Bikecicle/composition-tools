package orc;

public class Variable extends Value {

	String operator;

	public Variable(String type, String name, String operator, Value... params) {
		this.operator = operator;
		this.params = params;
		this.alias = type + name;
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
