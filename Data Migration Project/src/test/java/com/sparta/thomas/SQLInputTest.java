package com.sparta.thomas;

import com.sparta.thomas.model.EmployeeDTO;
import com.sparta.thomas.model.EmployeeHolder;
import com.sparta.thomas.model.FileInput;
import com.sparta.thomas.model.MigratorDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLInputTest {


    @Test
    @DisplayName("tests that the program is correctly inputting data into the database, file path will need to be set to the employeeTest.csv filepath")
   public void databaseInputTest(){
        MigratorDAO migratorDAO = new MigratorDAO();
        migratorDAO.connectToDatabase();
        migratorDAO.dropTableIfExists();
        migratorDAO.createTable();
        List<EmployeeDTO> employeeDTO;
        FileInput fileInput = new FileInput();
        List<String> file = fileInput.fileInput();
        EmployeeHolder employeeHolder = new EmployeeHolder();
        employeeHolder.getEmployeeList(file);
        employeeDTO = EmployeeHolder.splitEmployee();
        migratorDAO.addToDatabaseBatch(employeeDTO);

      try(
              PreparedStatement preparedStatement = migratorDAO.connectToDatabase().prepareStatement("SELECT * FROM persons")){

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet != null)
        {
            while(resultSet.next())
            {
                Assertions.assertEquals("198429",resultSet.getString(1));
            }
        }

    }
       catch (
    SQLException throwables) {
        throwables.printStackTrace();
    }


}
}
