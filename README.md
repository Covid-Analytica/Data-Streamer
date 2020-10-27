### Data-Streamer
Fetches data for events and num. of Covid-19 cases per State.

### CovidDataImporter
Run `python3 import.py`

### EventsDataStreamer
Client.java contains the main method.
Libraries required are provides in the libs folder of the project. They needed to be the classpath of the project before running them. 
Compile the java classes. 'javac -cp [class, names] --classpath -d [path to libraries]'
And run 'jar -cvf events.jar -C events/ '

### DataProcess
Run `sbt run`

### Analysis
Libraries required are provides in the libs folder of the project. They needed to be the classpath of the project before running them. 
Compile the java classes. 'javac -cp [class, names] --classpath -d [path to libraries]'
And run 'jar -cvf analysis.jar -C analysis/ '

The current config is set for a Mongodatabase running on lacalhost on port 27017 without user or password
