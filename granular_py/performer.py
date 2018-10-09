import ctcsound as csnd6

orc = """
sr = 44100
ksmps = 32
nchnls = 2
0dbfs = 1

instr 1
kcps = 60; frequency
kphs = 0.5; phase
kfmd = 60; frequency variation
kpmd = 0.5; phase variation
kgdur = 0.01; grain duration
kdens = 100; grains per second
imaxovr = 1
kfn = 1; grain waveform
iwfn = 2; window waveform
kfrpow = 1; frequency variation distribution
kprpow = 1; phase variation distribution

ares grain3 kcps, kphs, kfmd, kpmd, kgdur, kdens, imaxovr, kfn, iwfn, \
      kfrpow, kprpow
outs ares, ares
endin
"""

sco = """
f 1 0 0 1 "ah.wav" 0 0 0
f 2 0 512 20 2

i 1 0 1
e
"""

c = csnd6.Csound()
c.setOption("-odac")
c.compileOrc(orc)
c.readScore(sco)

c.start()

while c.performKsmps() == 0:
    pass

c.stop()
