package orc;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

	Value[] outs;
	int id;
	List<Input> inputs;

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

	public String toString() {
		String s = "";
		for (Value value : outs) {
			s += value + "\r\n";
		}
		s += "outs ";
		for (int i = 0; i < outs.length; i++) {
			if (outs[i].channels > 1) {
			for (int j = 0; j < outs[i].channels; j++) {
				if (i > 0 || j > 0)
					s += ", ";
				s += outs[i].alias + j;
			}
			} else {
				if (i > 0)
					s += ", ";
				s += outs[i].alias;
			}
		}
		return s;
	}
}
