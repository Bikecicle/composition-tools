package main;

public class Rhythm {
	
	String sample;
	Timbre[] timbres;
	Sequence[] sequences;
	int voiceCount;
	
	public Rhythm(String sample, Timbre[] timbres, Sequence[] sequences, int voiceCount) {
		this.sample = sample;
		this.timbres = timbres;
		this.sequences = sequences;
		this.voiceCount = voiceCount;
	}
	
	public String toScore(float bpm, int fID) {
		String score = "";
		score += "f 1 0 0 1 \"samples/" + sample + "\" 0.0 0 0";
		for (int v = 0; v < voiceCount; v++) {
			float quantLen = 240 / bpm  / sequences[v].quant;
			for (int s = 0; s < sequences[v].strikeCount; s++) {
				float strt = sequences[v].strt[s] * quantLen;
				float dur = timbres[v].trueDur(sequences[v].dur[s]);
				float amp = 0.5f;
				float[] env = timbres[v].trueEnv(sequences[v].env[s]);
				float pos = timbres[v].truePos(sequences[v].pos[s]);
				score += "/ni ";
			}
		}
		return score;
	}
}
