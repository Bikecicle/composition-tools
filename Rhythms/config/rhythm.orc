sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 1
	
	idur		=		p3			; duration
	kamp		=		p4			; amplitude
	irise		=		p5			; attack
	idec		=		p6			; decay
	kfreqratio	=		1			; frequency mod
	ifn			=		p7			; function table id
	iphs		=		p8 * sr		; start phase
	
	kenv linen kamp, irise, idur, idec
	ares lposcil kenv, kfreqratio, 0, 0, ifn, iphs
	outs ares, ares
     
endin