# Comment this line to run with default java
JAVA_PATH=/usr/lib/jvm/java-11-openjdk-amd64/bin/

JAVAC=javac
JAVA=java
LIB_DIR=lib
OUT_DIR=out
# Directory where spark is installed
SPARK_FOLDER=/opt/spark-3.5.5
# Extra libraries to include, replace with your own .jars
CLASSPATH=$(OUT_DIR):$(LIB_DIR)/json-20250107.jar:$(SPARK_FOLDER)/jars/*

SOURCES=$(shell find src -name "*.java")

all: build run

build:
	$(JAVA_PATH)$(JAVAC) -cp $(CLASSPATH) -d out $(SOURCES)

run:
	$(JAVA_PATH)$(JAVA) -cp $(CLASSPATH) FeedReaderMain

runHeuristic: build  
	$(JAVA_PATH)$(JAVA) -cp $(CLASSPATH) FeedReaderMain -ne

clean:
	rm -rf $(OUT_DIR)
