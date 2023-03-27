#include "SWI-Prolog.h"
#include "SWI-Stream.h"
#include "SWI-cpp.h"
#include "MarkovProbabilities.h"
#include <iostream>
#include <string.h>
bool initialized = false;

void init() {
	if (!initialized) {
		const char* av[2];
		av[0] = "Caller";
		av[1] = "-q"; //don't display SWI Prolog banner

		//the following two lines are a workaround 
		const char* dir = "SWI_HOME_DIR=/usr/bin/swipl";
		putenv((char*)dir); 

		PL_initialise(2, (char**)av);

		PlCall("consult('facts.pl')");
		initialized = true;	
	}
}

/*
 * Class:     MarkovProbabilities
 * Method:    callPrologGetReachability
 * Signature: (Ljava/lang/String;Ljava/lang/String;)D
 */
JNIEXPORT jdouble JNICALL Java_MarkovProbabilities_callPrologGetReachability
  (JNIEnv * env, jobject that, jstring state1, jstring state2){
	if (!initialized) {
		init();
		initialized = true;	
	}

    jboolean iscopy;
	const char* startingState = env->GetStringUTFChars(state1, &iscopy);	
	const char* endingState = env->GetStringUTFChars(state2, &iscopy);	

    term_t p, s1, s2, s, reachability, probability;
    functor_t mc_prob, trans;

	mc_prob = PL_new_functor(PL_new_atom("mc_prob"), 2);
	trans = PL_new_functor(PL_new_atom("trans"), 3);
    s1 = PL_new_term_ref();	//state1
    PL_put_atom_chars(s1, startingState);

	s2 = PL_new_term_ref();	//state2
    PL_put_atom_chars(s2, endingState);

	s = PL_new_term_ref();
	PL_put_float(s, 0.0);

	probability = PL_new_term_ref();

	
	term_t transProbability, probabilityRes;

	probabilityRes = PL_new_term_ref();
	transProbability = PL_new_term_ref();	//response from functor

	PL_cons_functor(transProbability, trans, s1, s, s2); 

	module_t facts = PL_new_module(PL_new_atom("facts.pl"));

	//PL_call(transProbability, facts);
	functor_t a = PL_new_functor(PL_new_atom("mc_prob"), 2);
	PL_cons_functor(probabilityRes, mc_prob, transProbability, probability);

	double probScore = 0.9;
	if(PL_call(probabilityRes, facts))
		PL_get_float(probability, &probScore);

	return probScore;

    }
