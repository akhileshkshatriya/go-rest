# GoEuro dev test
	For convinience I have placed "GoEuroTest-1.0.RELEASE.jar" in the root directly, it can also be generated with maven command "mvn clean compile assembly:single".	

	Instruction
		Command to generate csv file "java -jar GoEuroTest-1.0.RELEASE.jar ${location_name}" 
		Example: java -jar GoEuroTest-1.0.RELEASE.jar Berlin (It will generate "Berlin_Location_Detail.csv")
		CSV and log file will be generated in current user directory.
		CSV File will be generated with the name "${LocationName}_Location_Detail.csv".
		If file already exist in the current user directory, it will be overwritten. 

	Assumptions
		GoEuro location service will always return _id, name, type, latitude and longitude.
		Above mentioned fields will not contain data having comma ",".
	
	Tools and Technogies
		The development is carried out using TDD.
		The implementation is been carried out with Spring 4 with Java configuration.
		Junit4 is been used for Unit test cases with Mockito as a mocking framework.
		95% code coverage.
		Maven is used as build tool.
		Assembly Plugin has been used to generate Jar.
		SLF4J api has been used with log4j for logging.
		Java Jackson api is used for JSON object mapping.
	
	System requirement
		Java 7 or later.
	

