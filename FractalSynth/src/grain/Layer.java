package grain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import orc.Instrument;
import orc.Opcode;
import orc.Orchestra;
import orc.Statement;
import orc.Variable;
import sco.FTable;
import sco.ParamMap;
import sco.Score;
import sound.Performance;

public class Layer implements Serializable, Performance {

	private static final long serialVersionUID = 2219783412831754585L;

	public String name;
	public double duration;
	public List<Grain> sequence;
	public Score sco;

	public Layer(String name, double duration) {
		this.name = name;
		this.duration = duration;
		sequence = new ArrayList<Grain>();
		sco = new Score();
	}

	public void addGrains(List<Grain> matrix) {
		sequence.addAll(matrix);
	}

	public int addFTable(FTable ft) {
		return sco.addFTable(ft);
	}

	public void clear() {
		sequence.clear();
		sco.clear();
	}

	@Override
	public Score getScore() {
		ParamMap paramMap = getOrchestra().mapParams();
		for (Grain g : sequence) {
			sco.addNote(g.getNote(paramMap));
		}
		return null;
	}

	@Override
	public Orchestra getOrchestra() {
		Orchestra orc = new Orchestra(44100, 32, 2, 1);
		
		Instrument i1 = new Instrument(1);
		Statement kamp = new Variable("k", "amp", "=", i1.p("amp"));
		Statement irise = new Variable("i", "rise", "=", i1.p("rise"));
		Statement idur = i1.dur;
		Statement idec = new Variable("i", "dec", "=", i1.p("dec"));
		Opcode aenv = new Opcode("a", "env", 1, "linen", kamp, irise, idur, idec);
		Statement kcps = new Variable("k", "cps", "=", i1.p("cps"));
		Opcode asig = new Opcode("a", "sig", 1, "poscil", aenv, kcps);
		i1.setOuts(asig);
		orc.add(i1);
		
		Instrument i2 = new Instrument(2);
		return orc;
	}
}
