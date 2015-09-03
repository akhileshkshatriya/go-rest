# GoEuro dev test
	For convinience I have placed "GoEuroTest.jar" in the root directly, it can also be generated with maven command "mvn clean compile assembly:single".	
	Assumptions
		GoEuro location service will always return _id, name, type, latitude and longitude.
		Above mentioned fields will not contain data with having comma ",".
	Instruction
		CSV and log file will be generated at current user directory.
		CSV File will be generated with the name "${LocationName}_Location_Detail.csv" For example for Berlin location  the file will be under the name "Berlin_Location_Details.csv".
		If file already exist in the current user directory, it will be overwritten. 
	System requirement
		Java 7 or later.
		Maven
	

