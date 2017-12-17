sr = 44100
ksmps = 32
nchnls = 2
0dbfs  = 1

instr 1

;p1=xamp
;p2=irise
;p3=idur
;p4=idec
;p5=kfreqratio
;p6=kloop
;p7=kend

ares linen p1, p2, p3, p4
asig lposcil ares, p5, p6, p7, 1
     outs asig, asig

endin