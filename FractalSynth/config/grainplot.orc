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
	kloop = p8 * sr; start time
	kend = p9 * sr; end time
	ifreq = p10; center frequency
	iband = p11; bandwidth
	ifn = p12; function table id
	
	kenv linen kamp, irise, idur, idec
	asig lposcil kenv, kfreqratio, kloop, kend, ifn
	;ares butterbp asig, ifreq, iband
	outs asig, asig
     
endin