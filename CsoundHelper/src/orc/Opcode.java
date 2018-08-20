package orc;

public class Opcode extends Statement {

	String opcode;

	public Opcode(String type, String name, int channels, String opcode, Statement... params) {
		super(type + name, false, channels, params);
		this.opcode = opcode;
	}

	@Override
	public String toString() {
		String s = "";
		String o = "";
		for (Statement param : params) {
			if (!param.terminal && !param.defined)
				s += param;
		}
		s += o;
		if (channels > 1) {
			for (int i = 0; i < channels; i++) {
				if (i > 0)
					s += ", ";
				s += alias + i;
			}
		} else {
			s += alias;
		}
		s += " " + opcode + " ";
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
