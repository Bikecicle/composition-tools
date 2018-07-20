package orc;

public class Opcode extends Value {

	public int channels;
	public String opcode;

	public Opcode(String type, String name, int channels, String opcode, Value... params) {
		super(type + name, false, params);
		this.channels = channels;
		this.opcode = opcode;
	}

	@Override
	public String read() {
		String s = "";
		for (Value param : params) {
			if (!param.terminal)
				s += param.read() + "\n";
		}
		for (int i = 0; i < channels; i++) {
			if (i > 0)
				s += ", ";
			s += alias + i;
		}
		s += " " + opcode + " ";
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				s += ", ";
			s += params[i].alias;
		}
		return s;
	}
}
