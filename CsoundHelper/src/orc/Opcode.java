package orc;

public class Opcode extends Value {

	String opcode;

	public Opcode(String type, String name, int channels, String opcode, Value... params) {
		super(type + name, false, channels, params);
		this.opcode = opcode;
	}

	@Override
	public String toString() {
		String s = "";
		String o = "";
		for (Value param : params) {
			if (!param.terminal && !param.defined) {
				if (param instanceof Opcode) {
					o += param + "\r\n";
				} else {
					s += param + "\r\n";
				}
				
			}
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
		defined = true;
		return s;
	}
}
