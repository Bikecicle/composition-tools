package orc;

import java.util.ArrayList;
import java.util.List;

import sco.ParamMap;

public class Instrument {

	public Value[] outs;
	public int id;
	
	private List<Integer> pUsed;
	private int pIndex;

	public Instrument(int id) {
		this.id = id;
		pIndex = 4;
		pUsed = new ArrayList<>();
		for (int i = 0; i < pIndex; i++)
			pUsed.add(i);
	}

	public void setOuts(Value... values) {
		outs = values;
	}

	public Input p(int index) {
		pUsed.add(index);
		return new Input(index);
	}

	public Input p() {
		Input input = new Input(pIndex);
		pUsed.add(pIndex);
		while (pUsed.contains(pIndex))
			pIndex++;
		return input;
	}

	public Input[] pn(int count) {
		Input[] inputs = new Input[count];
		for (int i = 0; i < count; i++) {
			inputs[i] = new Input(pIndex);
			pUsed.add(pIndex);
			while (pUsed.contains(pIndex))
				pIndex++;
		}
		return inputs;
	}
	
	public void mapInputs(ParamMap map) {
		for (Value out : outs) {
			out.mapInputs(id, map);
		}
	}
	
	public String read() {
		String s = "";
		for (Value value : outs) {
			s += value.read();
		}
		return s;
	}
}
