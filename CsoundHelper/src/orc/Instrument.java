package orc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instrument implements Serializable {
	
	private static final long serialVersionUID = -4284762653198415691L;
	
	public Input inst;
	public Input strt;
	public Input dur;

	Statement[] outs;
	int id;
	List<Input> inputs;

	private List<Integer> pUsed;
	private int pIndex;

	public Instrument(int id) {
		this.id = id;
		inputs = new ArrayList<>();
		inst = new Input(1, Orchestra.INSTRUMENT);
		strt = new Input(2, Orchestra.START);
		dur = new Input(3, Orchestra.DURATION);
		inputs.add(inst);
		inputs.add(strt);
		inputs.add(dur);
		pIndex = 4;
		pUsed = new ArrayList<>();
		for (int i = 0; i < pIndex; i++)
			pUsed.add(i);
	}

	public void setOuts(Statement... values) {
		outs = values;
	}

	public Input p(int index, String paramName) {
		Input input = new Input(index, paramName);
		inputs.add(input);
		pUsed.add(index);
		return input;
	}

	public Input p(String paramName) {
		Input input = p(pIndex, paramName);
		while (pUsed.contains(pIndex))
			pIndex++;
		return input;
	}

	public Input[] pn(int count, String paramName) {
		Input[] inputs = new Input[count];
		for (int i = 0; i < count; i++)
			inputs[i] = p(paramName + i);
		return inputs;
	}

	public String toString() {
		String s = "";
		for (Statement value : outs) {
			s += value;
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
		s += "\n";
		return s;
	}
}
