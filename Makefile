all:
	javac tp3/DoPlan.java

test:
	java tp3.DoPlan

clean:
	rm -rf $(find .|grep class$)
