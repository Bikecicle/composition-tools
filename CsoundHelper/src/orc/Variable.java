package orc;

public class Variable extends Statement {

	String operator;

	public Variable(String type, String name, String operator, Statement... params) {
		super(type + name, false, 1, params);
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Statement value : params) {
			if (!value.terminal && !value.defined)
				s += value;
		}
		s += alias + " " + operator + " ";
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				s += ", ";
			s += params[i].alias;
		}
		s += "\n";
		defined = true;
		return s;
	}
}
