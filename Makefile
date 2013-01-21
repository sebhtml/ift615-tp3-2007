all: tp3/DoPlan.class

tp3/DoPlan.class:
	javac tp3/DoPlan.java

test: tp3/DoPlan.class
	java tp3.DoPlan

clean:
	rm -rf tp3/*.class tp3/*/*.class tp3/*/*/*.class
