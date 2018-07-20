package orc;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

	public Value[] outs;
	public int id;
	public List<Input> inputs;
	
	private List<Integer> pUsed;
	private int pIndex;

	public Instrument(int id) {
		this.id = id;
		inputs = new ArrayList<>();
		pIndex = 4;
		pUsed = new ArrayList<>();
		for (int i = 0; i < pIndex; i++)
			pUsed.add(i);
	}

	public void setOuts(Value... values) {
		outs = values;
	}
	
	public Input p(int index) {
		Input input = new Input(index);
		inputs.add(input);
		pUsed.add(index);
		return input;
	}

	public Input p() {
		Input input = p(pIndex);
		while (pUsed.contains(pIndex))
			pIndex++;
		return input;
	}

	public Input[] pn(int count) {
		Input[] inputs = new Input[count];
		for (int i = 0; i < count; i++)
			inputs[i] = p();
		return inputs;
	}
	
	public String read() {
		String s = "";
		for (Value value : outs)
			s += value.read();
		return s;
	}
}
