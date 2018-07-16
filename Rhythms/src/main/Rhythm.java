package main;

import orc.Orchestra;
import orc.Value;
import orc.Variable;
import sco.Note;
import sco.Param;
import sco.ArrayParam;

import java.util.HashMap;
import java.util.List;

import orc.Constant;
import orc.Instrument;
import orc.Opcode;

public class Rhythm {
	
	public String[] samples;
	public Timbre[] timbres;
	public Sequence[] sequences;
	public int voiceCount;
	
	public Rhythm(String[] samples, Timbre[] timbres, Sequence[] sequences, int voiceCount) {
		this.samples = samples;
		this.timbres = timbres;
		this.sequences = sequences;
		this.voiceCount = voiceCount;
	}
	
	public String getScore(float bpm, int fID, Orchestra orc) {
		String score = "";
		List<HashMap<Integer, String>> paramMap = orc.mapParams();
		int ift = 1;
		for (int v = 0; v < voiceCount; v++) {
			float quantLen = 240 / bpm  / sequences[v].quant;
			for (int s = 0; s < sequences[v].strikeCount; s++) {
				Note note = new Note(paramMap);
				note.add(new Param<Integer>(Orchestra.INSTRUMENT, 1));
				note.add(new Param<Float>(Orchestra.START, sequences[v].strt[s] * quantLen));
				note.add(new Param<Float>(Orchestra.DURATION, timbres[v].trueDur(sequences[v].dur[s])));
				note.add(new Param<Float>("freqratio", 1.0f));
				note.add(new Param<Integer>("ft", ift));
				note.add(new Param<Float>("pos", timbres[v].truePos(sequences[v].pos[s])));
				note.add(new ArrayParam<Float>("env", timbres[v].trueEnv(sequences[v].env[s])));
				score += note + "\n";
			}
		}
		return score;
	}
	
	public static Orchestra getOrchestra() {
		int sr = 44100;
		int ksmps = 32;
		int nchnls = 2;
		float dbfs0 = 1.0f;
		Orchestra orc = new Orchestra(sr, ksmps, nchnls, dbfs0);
		Instrument in = new Instrument();
		Value kfreqratio = new Variable("k", "freqratio", "=", in.p());
		Value kloop = new Constant<Integer>(0);
		Value kend = new Constant<Integer>(0);
		Value ifn = new Variable("i", "ft", "=", in.p());
		Value ipos = new Variable("i", "pos", "=", in.p());
		Value kenv = new Variable("k" , "env", "linseg", in.pn(Timbre.ENVELOPE_DIM));
		Opcode loop = new Opcode("a", "sig", 2, "lposcilsa", kenv, kfreqratio, kloop, kend, ifn, ipos);
		in.setOuts(loop);
		orc.add(in);
		return orc;
	}
}
