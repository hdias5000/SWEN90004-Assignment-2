# Java Wealth Distribution Model

This is an implementation of the agent-based, wealth distribution model in Java. At the very basic level, it is a replication of the Netlogo "Wealth Distribution" model in its model library. The Java implementation also supports a set of extension features such as wealth inheritance.

## Usage

To build the model, simply run `make` in the project directory (where the makefile is located). The make process generates `program.jar` as the compiled Java program. It can be run with a set of parameters by the following command:

`java -cp ./program.jar Simulation [numPeople] [maxVision] [metabolismMax] [lifeExpectancyMin] [lifeExpectancyMax] [percentBestLand] [grainGrowthInterval] [grainGrowthRate] [timeMax] [filename]`

...where most of the parameters correspond to those in the Netlogo model. The `timeMax` parameter specifies the duration of simulation, and `filename` specifies the name of CSV file output.

There is also a Python script for experimenting and data visualisation. In order to use it, simply call:

`python3 ./experiment.py`

or 

`python ./experiment.py` if Python 3 is installed as default.

The script calls the Java program automatically and generates a set of plots in the output folder. Parameters can be modified in `experiment.py`.
