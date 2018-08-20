package orc;

public class Expression extends Statement {

	public Expression(String format, Statement... params) {
		super(null, false, 1, params);
		this.params = params;
		Object[] strs = new String[params.length];
		for (int i = 0; i < params.length; i++)
			strs[i] = params[i].alias;
		alias = String.format(format, strs);
	}

	@Override
	public String toString() {
		String s = "";
		for (Statement param : params) {
			if (!param.terminal & !param.defined) {
				s += param;
			}
		}
		defined = true;
		return s;
	}
}
