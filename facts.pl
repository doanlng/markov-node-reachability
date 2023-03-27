:- use_module(library(mcintyre)).
:- mc.
:- begin_lpad.
trans(s0, S, s0): 0.5; trans(s0, S, s1): 0.4; trans(s0, S, s2): 0.1.
trans(s2, S, s0): 0.18; trans(s2, S, s3): 0.39; trans(s2, S, s4): 0.43.
reach(S,I,T):- trans(S,I,U), reach(U,next(I),T).
reach(S,_,S).
trans(s4,_,s3).
:- end_lpad.
:- end_lpad.
