package orc;

public class Opcode extends Value {

	int channels;
	public String opcode;

	public Opcode(String type, String name, int channels, String opcode, Value... params) {
		this.channels = channels;
		this.opcode = opcode;
		this.params = params;
		this.alias =  type + name;
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
