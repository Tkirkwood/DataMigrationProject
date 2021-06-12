package com.sparta.thomas.model;

import com.sparta.thomas.util.Logging;
import com.sparta.thomas.util.LoadProperties;


import java.sql.*;
import java.util.List;


public class MigratorDAO {
    private final String URL = "jdbc:mysql://localhost:3306/myLocal?serverTimezone=GMT";
    private Connection connection;
    private final String dropTable = "DROP TABLE  IF EXISTS persons;";
    private final String createTable = "CREATE TABLE persons ( EmpId INT(6) UNSIGNED PRIMARY KEY, namePrefix VARCHAR(5) NOT NULL, firstName VARCHAR(30) NOT NULL, MiddleInitial VARCHAR(5), lastName VARCHAR(30),gender VARCHAR (10), Email VARCHAR(50),DateOfBirth DATE, DateOfJoining DATE,salary INT (10) );";
    private final String addTo = "INSERT INTO persons (EmpId,NamePrefix,firstName, MiddleInitial, lastName,Gender,Email,DateOfBirth,DateOfJoining,salary) VALUES (?,?,?,?,?,?,?,?,?,?);";



    public void addToDatabaseBatch (List<EmployeeDTO> employeeList) {
        int count = 0;
        int batchNumber = Integer.parseInt(LoadProperties.getProperty("batchInsertNumber"));

            try (PreparedStatement preparedStatement = connection.prepareStatement(addTo)) {
                for (EmployeeDTO employee : employeeList) {
                preparedStatement.setString(1, employee.getEmpId());
                preparedStatement.setString(2, employee.getNamePrefix());
                preparedStatement.setString(3, employee.getFirstName());
                preparedStatement.setString(4, employee.getMiddleInitial());
                preparedStatement.setString(5, employee.getLastName());
                preparedStatement.setString(6, employee.getGender());
                preparedStatement.setString(7, employee.getEMail());
                preparedStatement.setDate(8, employee.getDateOfBirth());
                preparedStatement.setDate(9, employee.getDateOfJoining());
                preparedStatement.setInt(10, employee.getSalary());

               preparedStatement.addBatch();
                count++;
               if (count%batchNumber==0)
               {
                  try {
                      preparedStatement.executeBatch();


                  } catch (SQLException throwables) {
                      Logging.logger.warn(throwables.getMessage());


                  }
               }
                    if ( employeeList.size()/batchNumber==0 && count == employeeList.size())
                    {
                        try {
                            preparedStatement.executeBatch();


                        } catch (SQLException throwables) {
                            Logging.logger.warn(throwables.getMessage());

                        }
                }
            }

            } catch (SQLException throwables) {
                Logging.logger.warn(throwables.getMessage());
            }



        }





    public void dropTableIfExists(){
        try(PreparedStatement preparedStatement = connection.prepareStatement(dropTable)){
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            Logging.logger.warn(throwables.getMessage());
        }

    }
    public void createTable (){
        try(Statement statement = connection.createStatement()){
            statement.execute(createTable);

        } catch (SQLException throwables) {
            Logging.logger.warn(throwables.getMessage());
        }
    }
    public Connection connectToDatabase(){
        try {
            this.connection = DriverManager.getConnection(URL, LoadProperties.getProperty("username"), LoadProperties.getProperty("password"));
            }catch (SQLException e)
        {
            Logging.logger.error(e.getMessage());
        }
        catch (Exception e)
        {
            Logging.logger.error(e.getMessage());
        }
        return connection;
    }


}
