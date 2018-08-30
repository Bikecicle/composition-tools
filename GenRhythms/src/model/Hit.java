package model;

import evolution.core.Splicer;

public class Hit {

	public int strt;
	public float pos;
	public float att;
	public float dec;
	public float sus;
	public float rel;
	public float slev;

	public Hit(int strt, float pos, float att, float dec, float sus, float rel, float slev) {
		this.strt = strt;
		this.pos = pos;
		this.att = att;
		this.dec = dec;
		this.sus = sus;
		this.rel = rel;
		this.slev = slev;
	}

	public Hit() {

	}

	public static Hit splice(Hit h1, Hit h2) {
		Hit h3 = new Hit();
		h3.strt = Math.random() < Splicer.WEIGHT ? h1.strt : h2.strt;
		h3.pos = Math.random() < Splicer.WEIGHT ? h1.pos : h2.pos;
		h3.att = Math.random() < Splicer.WEIGHT ? h1.att : h2.att;
		h3.dec = Math.random() < Splicer.WEIGHT ? h1.dec : h2.dec;
		h3.sus = Math.random() < Splicer.WEIGHT ? h1.sus : h2.sus;
		h3.rel = Math.random() < Splicer.WEIGHT ? h1.rel : h2.rel;
		h3.slev = Math.random() < Splicer.WEIGHT ? h1.slev : h2.slev;
		return h3;
	}

	public void mutate(double mRate) {
		pos = (float) (Math.random() < mRate ? Math.random() : pos);
		att = (float) (Math.random() < mRate ? Math.random() : att);
		dec = (float) (Math.random() < mRate ? Math.random() : dec);
		sus = (float) (Math.random() < mRate ? Math.random() : sus);
		rel = (float) (Math.random() < mRate ? Math.random() : rel);
		slev = (float) (Math.random() < mRate ? Math.random() : slev);
	}
	
	public void randomize() {
		pos = (float) Math.random();
		att = (float) Math.random();
		dec = (float) Math.random();
		sus = (float) Math.random();
		rel = (float) Math.random();
		slev = (float) Math.random();
	}
}
