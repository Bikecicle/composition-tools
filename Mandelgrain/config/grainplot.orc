sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1.0

instr 1
	; p4=amp
	; p5=freq
	; p6=attack time
	; p7=release time
	ares linen  p4, p6, p3, p7 
	asig poscil ares, p5, 1    
	outs asig, asig  
endin