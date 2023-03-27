# It is up to you how you decide to structure your proof of concept

# You might find the included Makefile helpful.

# In the least, it contains all the correct includes;

# it also suggests putting Java annotations to the annotations directory

# Nevertheless, you should feel free to implement your solution

# any way you like, as long as it can be executed by running "make run"

# if your "make run" requires additional parameters,

# you may want to include run.sh

# We have also included init.cpp in case if you might find it helpful.

Based on a markov process where a customer has loyalties associated with brands b1, b2, b3, b4, b5
Each of these people has a chance of moving from one brand to another, and a probability of whether this person is a loyal person or not
A person can only move between numerically adjacent brands to theirs. I.E. b1 can only move to b2 or b5, b2 can only move to b3 or b4

A customer has a .7 chance of being loyal and a .3 chance of being !loyal

For loyal customers :

<!--

   b1 | b2 | b3 | b4 | b5
b1 .7 |.15 | 00 | 00 |.15
----------------------
b2 .3 | .5 | .2 | 00 | 00
----------------------
b3 00 | .4 | .4 | .2 | 00
----------------------
b4 00 | 00 | 00 | .9 | .1
----------------------
b5 .3 | 00 | 00 | .1 | .6

-->

For !loyal customers :

<!--
   b1 | b2 | b3 | b4 | b5
b1 .5 |.25 | 00 | 00 |.25
----------------------
b2 .35 | .3 |.35 | 00 | 00
----------------------
b3 00 | .3 | .4 | .3 | 00
----------------------
b4 00 | 00 | .2 | .6 | .2
----------------------
b5 .3 | 00 | 00 | .3 | .4
-->
