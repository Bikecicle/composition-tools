sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 1

	idur		=		p3		; duration
	kamp		=		p4		; amplitude
	kcps		=		p5		; frequency
	irise		=		p6		; attack
	idec		=		p7		; decay
	
	aenv linen kamp, irise, idur, idec
	asig poscil aenv, kcps 
	outs asig, asig  
	
endin

instr 2
	
	idur		=		p3		; duration
	kamp		=		p4		; amplitude
	irise		=		p5		; attack
	idec		=		p6		; decay
	kfreqratio	=		p7		; frequency mod
	ifreq		=		p8		; center frequency
	iband		=		p9		; bandwidth
	ifn			=		p10		; function table id
	iphs		=		p11*sr	; start phase
	
	kenv linen kamp, irise, idur, idec
	asig lposcil kenv, kfreqratio, 0, 0, ifn, iphs
	ares butterbp asig, ifreq, iband
	outs ares, ares
     
endin

instr 3

	idur		=		p3								; duration
	kamp		=		p4								; amplitude
	ifn1		=		giSound							; sample function table
	ilen		=		nsamp(ifn1)/sr					; sample length
	iPtrStart	random	1,ilen-1						;
	iPtrTrav	random	-1,1							;
	ktimewarp	lineseg	iPtrStart,p3,iPtrStart+iPtrTrav	; time stretch factor
	kamp		lineseg	0,p3/2,0.2,p3/2,0				; amplitude
	iresample	random	-24,24.99						;
	iresample	=		semitone(int(iresample))		; pitch mod factor
	ifn2		=		giWfn							; window function table
	ibeg		=		0								; start time offset
	iwsize		random	400,10000						; window size
	irandw		=		iwsize/3						; random values added to iwsize
	ioverlap	=		50								; window overlap density
	itimemode	=		1								; pointer mode (!0)

	aSigL,aSigR sndwarpst kamp, ktimewarp, iresample, ifn1, \
		ibeg, iwsize, irandw, ioverlap, ifn2, itimemode
		
endin