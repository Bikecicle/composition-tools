package model;

import orc.Orchestra;
import orc.Statement;
import orc.Variable;
import sco.Note;
import sco.Param;
import sco.ParamMap;
import sco.Score;
import sco.SoundfileFTable;
import sound.Performance;
import orc.Constant;
import orc.Expression;
import orc.Instrument;
import orc.Opcode;

public class Rhythm implements Performance {
	
	private static final long serialVersionUID = 6111978696319487569L;
	
	public Timbre[] timbres;
	public Sequence[] sequences;
	public int voiceCount;

	public Rhythm(Timbre[] timbres, Sequence[] sequences) {
		this.timbres = timbres;
		this.sequences = sequences;
		voiceCount = timbres.length;
	}

	public void rateTimbre(int rating) {
		for (Timbre timbre : timbres)
			timbre.score += rating;
	}
	
	public void rateSequence(int rating) {
		for (Sequence sequence : sequences)
			sequence.score += rating;
	}

	public void resetScore() {
		for (Timbre timbre : timbres)
			timbre.score = 0;
		for (Sequence sequence : sequences)
			sequence.score = 0;
	}
	
	@Override
	public Score getScore() {
		ParamMap map = getOrchestra().mapParams();
		Score sco = new Score();
		for (int v = 0; v < voiceCount; v++) {
			int ift = sco.addFTable(new SoundfileFTable(timbres[v].sample));
			float quantLen = sequences[v].getQuantLen();
			for (int s = 0; s < sequences[v].hCount; s++) {
				Note note = new Note(map);
				float pos = realValue(timbres[v].pos, sequences[v].hits[s].pos);
				float att = realValue(timbres[v].att, sequences[v].hits[s].att);
				float dec = realValue(timbres[v].dec, sequences[v].hits[s].dec);
				float sus = realValue(timbres[v].sus, sequences[v].hits[s].sus);
				float rel = realValue(timbres[v].rel, sequences[v].hits[s].rel);
				float slev = realValue(timbres[v].slev, sequences[v].hits[s].slev);
				float dur = att + dec + sus + rel;
				note.add(new Param<Integer>(Orchestra.INSTRUMENT, 1));
				note.add(new Param<Float>(Orchestra.START, sequences[v].hits[s].strt * quantLen));
				note.add(new Param<Float>(Orchestra.DURATION, dur));
				note.add(new Param<Float>("amp", timbres[v].amp));
				note.add(new Param<Float>("pos", pos));
				note.add(new Param<Float>("att", att));
				note.add(new Param<Float>("dec", dec));
				note.add(new Param<Float>("rel", rel));
				note.add(new Param<Float>("slev", slev));
				note.add(new Param<Integer>("ft", ift));
				sco.addNote(note);
			}
		}
		return sco;
	}

	@Override
	public Orchestra getOrchestra() {
		int sr = 44100;
		int ksmps = 32;
		int nchnls = 2;
		float dbfs = 1.0f;
		Orchestra orc = new Orchestra(sr, ksmps, nchnls, dbfs);
		Instrument i = new Instrument(1);
		Statement db = new Variable("i", "amp", "=", i.p("amp"));
		Statement kpitch = new Constant<Float>(1f); // Variable("k", "pitch", "=", i.p());
		Statement ifad = new Constant<Float>(0f);
		Statement ifn = new Variable("i", "ft", "=", i.p("ft"));
		Statement kloopstart = new Constant<Float>(0f);
		Statement kloopend = new Variable("k", "end", "=", new Expression("nsamp(%s) / %s", ifn, Orchestra.SR, i.dur));
		Statement ipos = new Variable("i", "pos", "=", new Expression("(nsamp(%s) / %s - %s) * %s", ifn, Orchestra.SR, i.dur, i.p("pos")));
		Statement iatt = new Variable("i", "att", "=", i.p("att"));
		Statement idec = new Variable("i", "dec", "=", i.p("dec"));
		Statement islev = new Variable("i", "slev", "=", i.p("slev"));
		Statement irel = new Variable("i", "rel", "=", i.p("rel"));
		Statement kamp = new Opcode("k", "env", 1, "xadsr", iatt, idec, islev, irel);
		Statement loop = new Opcode("a", "sig", 2, "flooper2", new Expression("%s * %s", kamp, db), kpitch, kloopstart,
				kloopend, ifad, ifn, ipos);
		i.setOuts(loop);
		orc.add(i);
		return orc;
	}

	private float realValue(float[] range, float factor) {
		return factor * (range[1] - range[0]) + range[0];
	}
}
