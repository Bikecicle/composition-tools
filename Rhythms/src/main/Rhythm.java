package main;

import orc.Orchestra;
import orc.Value;
import orc.Variable;
import sco.Note;
import sco.Param;
import sco.ParamMap;
import sco.Score;
import sco.SoundfileFTable;
import sound.Performable;
import orc.Constant;
import orc.Expression;
import orc.Instrument;
import orc.Opcode;

public class Rhythm implements Performable {
	
	private static final long serialVersionUID = 6111978696319487569L;

	private static final float AMP = 100;
	
	Timbre[] timbres;
	Sequence[] sequences;
	int voiceCount;

	public Rhythm(Timbre[] timbres, Sequence[] sequences) {
		this.timbres = timbres;
		this.sequences = sequences;
		voiceCount = timbres.length;
	}

	public void rate(int rating) {
		for (Timbre timbre : timbres)
			timbre.score += rating;
		for (Sequence sequence : sequences)
			sequence.score += rating;
	}

	@Override
	public Score getScore() {
		ParamMap map = getOrchestra().mapParams();
		Score sco = new Score();
		for (int v = 0; v < voiceCount; v++) {
			int ifn = sco.addFTable(new SoundfileFTable(timbres[v].sample));
			float quantLen = sequences[v].getQuantLength();
			for (int s = 0; s < sequences[v].strikeCount; s++) {
				Note note = new Note(map);
				float pos = realValue(timbres[v].pos, sequences[v].pos[s]);
				float att = realValue(timbres[v].att, sequences[v].att[s]);
				float dec = realValue(timbres[v].dec, sequences[v].dec[s]);
				float sus = realValue(timbres[v].sus, sequences[v].sus[s]);
				float rel = realValue(timbres[v].rel, sequences[v].rel[s]);
				float slev = realValue(timbres[v].slev, sequences[v].slev[s]);
				float ptch = realValue(timbres[v].ptch, sequences[v].ptch[s]);
				float dur = att + dec + sus + rel;
				note.add(new Param<Integer>(Orchestra.INSTRUMENT, 1));
				note.add(new Param<Float>(Orchestra.START, sequences[v].strt[s] * quantLen));
				note.add(new Param<Float>(Orchestra.DURATION, dur));
				note.add(new Param<Float>("strt", pos));
				note.add(new Param<Float>("att", att));
				note.add(new Param<Float>("dec", dec));
				note.add(new Param<Float>("rel", rel));
				note.add(new Param<Float>("slev", slev));
				note.add(new Param<Integer>("fn", ifn));
				note.add(new Param<Float>("pitch", ptch));
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
		Value db = new Constant<Float>(AMP);
		Value kpitch = new Constant<Float>(1f); // Variable("k", "pitch", "=", i.p());
		Value ifad = new Constant<Float>(0.05f);
		Value ifn = new Variable("i", "fn", "=", i.p());
		Value kloopstart = new Variable("k", "strt", "=",
				new Expression("nsamp(%s) / %s * %s", ifn, Orchestra.SR, i.p()));
		Value kloopend = new Constant<Float>(0f);
		Value iatt = new Variable("i", "att", "=", i.p());
		Value idec = new Variable("i", "dec", "=", i.p());
		Value islev = new Variable("i", "slev", "=", i.p());
		Value irel = new Variable("i", "rel", "=", i.p());
		Value kamp = new Opcode("k", "amp", 1, "xadsr", iatt, idec, islev, irel);
		Value loop = new Opcode("a", "sig", 2, "flooper2", new Expression("%s * %s", kamp, db), kpitch, kloopstart,
				kloopend, ifad, ifn);
		i.setOuts(loop);
		orc.add(i);
		return orc;
	}

	private float realValue(float[] range, float factor) {
		return factor * (range[1] - range[0]) + range[0];
	}
}
