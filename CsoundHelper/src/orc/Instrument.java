package orc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Instrument {

	public Value[] outs;
	
	private int pIndex;
	private List<Integer> pUsed;

	public Instrument() {
		pIndex = 4;
		pUsed = new ArrayList<>();
		for (int i = 0; i < pIndex; i++)
			pUsed.add(i);
	}

	public void add(Value value) {
		// TODO Auto-generated method stub

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
	
	public HashMap<Integer, String> mapInputs() {
		HashMap<Integer, String> map = new HashMap<>();
		for (Value out : outs) {
			out.mapInputs(map);
		}
		return map;
	}
	
	public String read() {
		String s = "";
		for (Value value : outs) {
			s += value.read();
		}
		return s;
	}
}
