# GoEuro dev test
	The Jar (GoEuroTest.jar) could be directly be found in root also it could be generated in target directory	with maven command "clean compile assembly:single"	
	Assumptions
		The location service will not return data having comma (",").
		GoEuro location service will always return _id, name, type, latitude and longitude will never be null
	Instruction
		CSV and log file will be generated at current user directory.
		CSV File will be generated with the name "${LocationName}_Location_Detail.csv" For example for Berlin location       the file will be under the name "Berlin_Location_Details".
		If file already exist it will be overwritten it. 
	System requirement
		Java 7 or later.
		Maven
	

