	MAKEFLAGS += --silent
INCLUDES = -I  /usr/lib/swi-prolog/include/ -I /usr/lib/jvm/default-java/include -I /usr/lib/jvm/default-java/include/linux
PRELOAD=/usr/lib/swi-prolog/lib/x86_64-linux/libswipl.so

all: scala javac header lib
scala: 
		scalac MarkovProbabilities.Scala
#The javac target 
#(1) compiles all annotation source files 
# in directory "annotations"
#(2) compiles the annotations processor, named here as AnnsProcess.java
#(3) runs the annotations processor (javac -processor) on your source Java files;
# running the annotations processor should also compile YourSourceFile.java
javac:  
	javac Disjunct.java DisjunctWrapper.java Transition.java TransitionWrapper.java
	javac AnnsProcessor.java 
	javac -processor AnnsProcessor TempSourceFile.java

#The header target generates the .h header for the native method(s), 
#declared in YourSourceFile.java
header:
	javac ScalaH.java
	java ScalaH MarkovProbabilities.class
	scalac MarkovProbabilities.Scala

lib: 
	g++  -Wno-unused-result $(INCLUDES) -shared -fPIC -o libCallPrologFromScala.so init.cpp

run:
	LD_PRELOAD=$(PRELOAD) LD_LIBRARY_PATH=./ scala MarkovProbabilities	
clean:
	@rm -rf *.class *.so
