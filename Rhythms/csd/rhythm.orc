sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 1

	kamp		=		p4									; amplitude
	kpitch		=		1									; pitch line
	istart		=		0									; start position
	ifad		=		0.05								; crossfade
	ifn			=		p5									; function table
	ilen		=		nsamp(ifn) / sr - ifad				; loop length
	
	aSigR,aSigL flooper kamp, kpitch, istart, ilen, ifad, ifn
		
		outs aSigR, aSigL
		
	initskip:
		
endin