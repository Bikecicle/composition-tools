sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 2
ifn = p4
ilen = nsamp(ifn) / sr - 0.05
iatt = p6
idec = p7
islev = p8
irel = p9
kenv xadsr iatt, idec, islev, irel
asig0, asig1 flooper kenv, 1, 0, ilen, 0.05, ifn
outs asig0, asig1
endin

instr 1
ifn = p4
ilen = nsamp(ifn) / sr - 0.05
iatt = p6
idec = p7
islev = p8
irel = p9
kenv xadsr iatt, idec, islev, irel
asig0, asig1 flooper kenv, 1, 0.0, ilen, 0.05, ifn
outs asig0, asig1
endin

instr 3

	idur		=		p3									; duration
	kamp		=		p4									; amplitude
	ip1			=		p5									; starting pitch
	ip2			=		p6									; ending pitch
	islen		=		p7									; segment length
	kpitch		line 	ip1, islen, ip2						; pitch line
	kfreq		line	abs(p8 * ip1), islen, abs(p8 * ip2)	; frequency line
	
		tigoto initskip
		
	istart		=		0									; start position
	ifad		=		0.05								; crossfade
	iband		=		p9									; bandwidth
	ifn			=		p10									; function table
	ilen		=		nsamp(ifn) / sr - ifad				; loop length
	
	aSigR,aSigL flooper kamp, kpitch, istart, ilen, ifad, ifn
	aResR butterbp aSigR, kfreq, iband
	aResL butterbp aSigL, kfreq, iband
		
		outs aResR, aResL
		
	initskip:
		
endin