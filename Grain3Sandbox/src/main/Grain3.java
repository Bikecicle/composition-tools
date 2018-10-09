package main;

import orc.Constant;
import orc.FTGen;
import orc.Instrument;
import orc.Opcode;
import orc.Orchestra;
import orc.Statement;
import orc.Variable;
import sco.Note;
import sco.Param;
import sco.Score;
import sco.SineFTable;
import sco.SoundfileFTable;
import sco.Window;
import sco.WindowFTable;
import sound.Performance;
import sound.Performer;

public class Grain3 implements Performance {
	
	private static final long serialVersionUID = 3237328588588632814L;

	public static void main(String[] args) {
		Grain3 grain3 = new Grain3();
		Performer performer = new Performer();
		performer.play(grain3);
	}

	@Override
	public Score getScore() {
		Score sco = new Score();
		int fn = sco.addFTable(new SineFTable());
		int wfn = sco.addFTable(new WindowFTable(0, 1024, Window.hanning, 1));
		Note n1 = new Note(1, 0, 5, getOrchestra().mapParams());
		n1.add(new Param<Integer>("cps1", 64));
		n1.add(new Param<Integer>("cps2", 512));
		n1.add(new Param<Integer>("fmd1", 64));
		n1.add(new Param<Integer>("fmd2", 512));
		n1.add(new Param<Float>("pmd1", 0.5f));
		n1.add(new Param<Float>("pmd2", 2f));
		n1.add(new Param<Integer>("fn", fn));
		n1.add(new Param<Integer>("wfn", wfn));
		sco.addNote(n1);
		return sco;
	}

	@Override
	public Orchestra getOrchestra() {
		Orchestra orc = new Orchestra(44100, 32, 2, 1);
		Instrument i1 = new Instrument(1);
		Statement kcps = new Variable("k", "cps", "line", i1.p("cps1"), i1.dur, i1.p("cps2"));
		Statement kphs = new Constant<Float>(0.5f);
		Statement kfmd = new Variable("k", "fmd", "line", i1.p("fmd1"), i1.dur, i1.p("fmd2"));
		Statement kpmd = new Variable("k", "pmd", "line", i1.p("pmd1"), i1.dur, i1.p("pmd2"));
		Statement kgdur = new Constant<Float>(0.01f);
		Statement kdens = new Constant<Integer>(1000);
		Statement imaxovr = new Constant<Integer>(10);
		Statement ifn = new Variable("i", "fn", "=", i1.p("fn"));
		Statement iwfn = new Variable("i", "wfn", "=", i1.p("wfn"));
		Statement kfrpow = new Constant<Integer>(1);
		Statement kprpow = new Constant<Integer>(1);
		Opcode g3 = new Opcode("a", "sig", 1, "grain3", kcps, kphs, kfmd, kpmd, kgdur, kdens, imaxovr, ifn, iwfn,
				kfrpow, kprpow);
		i1.setOuts(g3);
		orc.add(i1);
		return orc;
	}

}
