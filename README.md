# GoEuro Java Developer Test
Its an Java implementation for GoEuro developer test.Its a stand alone java application which could be ran from command line interface in Jar format.

### Feature Implemented
Following are the tasks has been implemented in this implementation.
* It invokes JSON based GoEuro location service api
* Transform the JSON response
* Create a file and write it on the disk.

### Code Quality
The development has been carried out with Test Driven Development with 90% code coverage.
![alt tag](https://github.com/akhileshkshatriya/goeuro-devtest/blob/master/code-coverage.png)
### Instruction.
* To Run the application
 * Make sure you have Java 7 (or later).
 * Make sure you have write permissions from the directory you are running the program, it generates log files and csv file.
 * Download "GoEuroTest.jar".
 * Open console/Terminal and execute "java -jar GoEuroTest.jar Berlin", Here Berlin represent location
 * It will generate a CSV File with name "${LocationName}_Location_Detail.csv" For example for Berlin location       the file will be under the name "Berlin_Location_Details".
 * If file already exist it will be overwritten it.

* For Developers
 * Make sure you have Maven installed.
 * I have used maven assembly plugin to generate am executable jar, The jar could be generated with Maven command _"mvn clean compile assembly:single"_
 * Cobertura plugin has been used to generate code covergae report, The report could be reproduced with maven command _"mvn clean cobertura:cobertura"_

### Assumptions Made
* The location service will not return data having comma (",").
* GoEuro location service will always return _id, name, type, latitude and longitude will never be null




