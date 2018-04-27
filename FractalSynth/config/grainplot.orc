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
	
	idur		=		p3			; duration
	kamp		=		p4			; amplitude
	irise		=		p5			; attack
	idec		=		p6			; decay
	kfreqratio	=		p7			; frequency mod
	ifreq		=		p8			; center frequency
	iband		=		p9			; bandwidth
	ifn			=		p10			; function table id
	iphs		=		p11 * sr	; start phase
	
	kenv linen kamp, irise, idur, idec
	asig lposcil kenv, kfreqratio, 0, 0, ifn, iphs
	ares butterbp asig, ifreq, iband
	outs ares, ares
     
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

instr 4

	idur		=		p3								; duration
	kamp		=		p4								; amplitude
	ifn1		=		p5								; sample function table
	ifn2		=		p6								; window function table
	ia			=		p7 * nsamp(ifn1) / sr			;
	ib			=		p8 * nsamp(ifn1) / sr			;
	islen		=		p9								;
	ktime		line	ia, islen, ib					; time stretch factor
		
		tigoto initskip
		
	iwsize		=		p10								; window size
	irandw		=		p11								; random values added to iwsize
	ioverlap	=		p12								; window overlap density
	ifreq		=		p13								; band frequency
	iband		=		p14								; band width
	ilen		=		nsamp(ifn1)/sr					; sample length
	iresample	=		1								; pitch mod factor
	ibeg		=		0								; start time offset
	itimemode	=		1								; pointer mode (!0)

	aSigL,aSigR sndwarpst kamp, ktime, iresample, ifn1, \
		ibeg, iwsize, irandw, ioverlap, ifn2, itimemode
		
	aResL butterbp aSigL, ifreq, iband
	aResR butterbp aSigR, ifreq, iband
	
		outs aResL, aResR
		
	initskip:
		
endin