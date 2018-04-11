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
	ktimewarp	line	p5, p7, p6						; time stretch factor
		
		tigoto initskip
		
	iwsize		=		p8								; window size
	irandw		=		p9								; random values added to iwsize
	ioverlap	=		p10								; window overlap density
	ifreq		=		p11								; band frequency
	iband		=		p12								; band width
	ifn1		=		p13								; sample function table
	ifn2		=		p14								; window function table
	ilen		=		nsamp(ifn1)/sr					; sample length
	kresample	=		1								; pitch mod factor
	ibeg		=		0								; start time offset
	itimemode	=		1								; pointer mode (!0)

	aSigL,aSigR sndwarpst kamp, ktimewarp, iresample, ifn1, \
		ibeg, iwsize, irandw, ioverlap, ifn2, itimemode
		
	aResL butterbp aSigL, ifreq, iband
	aResR butterbp aSigR, ifreq, iband
	
		outs aResL, aResR
		
	initskip:
		
endin