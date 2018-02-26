sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 1

	idur = p3; duration
	kamp = p4; amplitude
	kcps = p5; frequency
	irise = p6; attack
	idec = p7; decay
	
	aenv linen kamp, irise, idur, idec
	asig poscil aenv, kcps 
	outs asig, asig  
	
endin

instr 2
	
	idur = p3; duration
	kamp = p4; amplitude
	irise = p5; attack
	idec = p6; decay
	kfreqratio = p7; frequency mod
	ifreq = p8; center frequency
	iband = p9; bandwidth
	ifn = p10; function table id
	iphs = p11 * sr; start phase
	
	kenv linen kamp, irise, idur, idec
	asig lposcil kenv, kfreqratio, 0, 0, ifn, iphs
	ares butterbp asig, ifreq, iband
	outs ares, ares
     
endin