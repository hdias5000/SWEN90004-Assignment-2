JAVAC=javac
sources = src/main/java/*.java
classes = *.class

default:
	JAVAC $(sources) -d ./
	jar cvf program.jar $(classes)
	rm -f $(classes)

clean:
	rm -f program.jar