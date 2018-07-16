package orc;

public class Input extends Value {
	
	int index;
	
	public Input(int index) {
		this.index = index;
		alias = "p" + index;
		terminal = true;
	}

	@Override
	public String read() {
		return alias;
	}
}
