# DataMigrationProject
### by Thomas Kirkwood

## Intro
The aim of the project is to develop a program that can take a csv file and migrate the data in it to a database. the project had the extra requirement of using threads to increase the speed of data migration.
Tools and techniques used
•	GitHub for version control
•	Intell-j as the IDE
•	Maven to manage dependencies
•	Log4j to log any and all exceptions



##User guide

On start the program will output the number of entries that are stored in the csv file. The program will then begin migrating the data and once that is complete the console will output the total time taken to perform the operation, the total number of entries that where successfully migrated and will output any entries that where unable to be migrated.


##Callable methods


### Starter
|     Return Type    	|     Call   Method    	|     Description                         	|
|--------------------	|----------------------	|-----------------------------------------	|
|     void           	|     start            	|     Controls the flow of the program    	|

### InserterThread

|     Return Type    	|     Call   Method    	|     Description                                                                                                                                           	|
|--------------------	|----------------------	|-----------------------------------------------------------------------------------------------------------------------------------------------------------	|
|     Void           	|     run              	|     Each new inserterThread object will create a   new thread that will take a pre-definable number of rows and batch insert   them into the database.    	|

### ConvertToEmployee

|     Return Type          	|     Call   Method            	|     Description                                                                                                                  	|
|--------------------------	|------------------------------	|----------------------------------------------------------------------------------------------------------------------------------	|
|     List<EmployeeDTO>    	|     convert(List<String>)    	|     Takes in a list of strings generated from a   csv file and converts them to a DTO ready to be inserted into the database.    	|
|     EmployeeDTO          	|     convert(String)          	|     Takes in a single string and outputs it as a   EmployeeDTO                                                                   	|
  
### DupelicateKeyChecker
  
|     Return Type    	|     Call   Method    	|     Description                                                                                                        	|
|--------------------	|----------------------	|------------------------------------------------------------------------------------------------------------------------	|
|     boolean        	|     checkKey         	|     Adds empoyeeID to a hashset if it does not   already exist and returns true if it already exists within hashset    	|

### EmployeeHolder
  
| Return Type       	| Call Method                   	| Description                                                                                                                                           	|
|-------------------	|-------------------------------	|-------------------------------------------------------------------------------------------------------------------------------------------------------	|
| void              	| getEmployeeList(List<String>) 	| Calls ConvertToEmployee.convert and store the results in static variables employeeDTO and listSize                                                    	|
| List<EmployeeDTO> 	| splitEmployee()               	| Returns a sub-list of employeeDTO of a pre-definable size. Has synchronized block to ensure that each thread takes a different section of employeeDTO 	|
  
### ErrorRowHolder
  
|     Return Type          	|     Call   Method               	|     Description                                                       	|
|--------------------------	|---------------------------------	|-----------------------------------------------------------------------	|
|     Void                 	|     addErrorRow(EmployeeDTO)    	|     Adds a row into the static   List<EmployeeDTO> variable errors    	|
|     List<EmployeeDTO>    	|     getErrorList()              	|     Returns the variable errors                                       	|
  
  
### FileInput
  
|     Return Type     	|     Call   Method    	|     Description                                                                                                   	|
|---------------------	|----------------------	|-------------------------------------------------------------------------------------------------------------------	|
|     List<String>    	|     fileInput()      	|     Will read the file with the path defined in   the property filepathToMigrate and return a list of strings.    	|
  
### LoadProperties
  
|     Return Type    	|     Call   Method          	|     Description                                                  	|
|--------------------	|----------------------------	|------------------------------------------------------------------	|
|     String         	|     getProperty(String)    	|     Static method used to get the value of a   given property    	|
  
### MigratorDAO
  
|     Return Type    	|     Call   Method                            	|     Description                                                                                                                            	|
|--------------------	|----------------------------------------------	|--------------------------------------------------------------------------------------------------------------------------------------------	|
|     void           	|     addToDatabaseBatch(List<EmployeeDTO>)    	|     Adds the inputted list in   batches of configurable size                                                                               	|
|     void           	|     dropTableIfExists()                      	|     Used during first setting   up tables and drops any table already in the database with the same name as   the one set to be created    	|
|     Void           	|     createTable()                            	|     Creates a table in a   server database for the data to be placed into                                                                  	|
|     Connection     	|     connectToDatabase                        	|     Establishes a connection   to a database                                                                                               	|
  
### ErrorViewer
  
|     Return Type    	|     Call   Method                      	|     Description                                                                                                            	|
|--------------------	|----------------------------------------	|----------------------------------------------------------------------------------------------------------------------------	|
|     void           	|     displayError(List<EmployeeDTO>)    	|     Prints out the number of entries in the list and prints specific   information from the DTOs held in the list          	|
  
  
  
 ## Testing guide
###Automated tests
Used: Junit
Test name: dataConversionToDTOTest
This test is designed to test that the data in the csv file is being correctly read and converted into a DTO. for this test the pathfile should be set to employeestest.csv

Test name: correctFileInput
This test is designed to check whether the csv file is being read correctly, for this test the pathfile should be set to employeestest.csv

Test name: duplicateInputTest
This test is designed to check that the duplicate primary key checker is functioning as expected. for this test the pathfile should be set to employeesDupeTest.csv

Test name: databaseInputTest
This test is designed to check that data is being inputted into the database correctly. , for this test the pathfile should be set to employeestest.csv


 

	


###Performance testing

For performance testing I ran the program at different points in its development using the same file with approx. 10000 rows. Testing performance with threading and batch inputting of data. These tests where run on the same machine and results should only be taken as a representation of performance and not absolute values.

  #### Data Migration Project Performance Tests
  
  |     Threaded                           	|     Batch Count    	|     Time                                   	|
|----------------------------------------	|--------------------	|--------------------------------------------	|
|     no                                 	|     None           	|     31 minutes      38 seconds             	|
|     no                                 	|     100            	|     21 minutes     31 seconds              	|
|     no                                 	|     250            	|     22 minutes     31 seconds              	|
|     Yes     1000 entries per thread    	|     100            	|     7 minutes     38 seconds               	|
|     Yes     1000 entries per thread    	|     250            	|     7 minutes     37 seconds               	|
|     Yes     500 entries per thread     	|     500            	|     5 minutes     38 seconds               	|
|     Yes     100 entries per thread     	|     100            	|     2 minutes     17 seconds               	|
|     Yes      50 entries per thread     	|     50             	|     1 minute     37 seconds                	|
  
  Note: when using sizes as small as 50 for number of entries per thread although the data migrates successfully for a file of 10000 rows too many connection errors where frequently encountered.
