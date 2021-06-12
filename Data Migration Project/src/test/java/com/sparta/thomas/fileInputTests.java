package com.sparta.thomas;


import com.sparta.thomas.model.DuplicateKeyChecker;
import com.sparta.thomas.model.EmployeeDTO;
import com.sparta.thomas.model.EmployeeHolder;
import com.sparta.thomas.model.FileInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class fileInputTests
{


    @Test
    @DisplayName("tests the file is correctly read, converted and inputted onto DTO format, file path will need to be set to the employeeTest.csv filepath")
    public void dataConversionToDTOTest()
    {
        List<EmployeeDTO> employeeDTO;
        FileInput fileInput = new FileInput();
        List<String> file = fileInput.fileInput();
        EmployeeHolder employeeHolder = new EmployeeHolder();
        employeeHolder.getEmployeeList(file);
        employeeDTO = EmployeeHolder.splitEmployee();
        Assertions.assertEquals("198429",employeeDTO.get(0).getEmpId());
    }


    @Test
    @DisplayName("tests the file is being read correctly, file path will need to be set to the employeeTest.csv filepath")
    public void correctFileInput(){
        FileInput fileInput = new FileInput();
        List<String> file = fileInput.fileInput();
        Assertions.assertEquals("198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,02/01/2008,69294",file.get(1));
    }

    @Test
    @DisplayName("tests that dupelicates are being handled correctly, file path will need to be set to the employeeDupeTest.csv filepath")
    public void duplicateInputTest(){
        List<EmployeeDTO> employeeDTO;
        FileInput fileInput = new FileInput();
        List<String> file = fileInput.fileInput();
        EmployeeHolder employeeHolder = new EmployeeHolder();
        employeeHolder.getEmployeeList(file);
        employeeDTO = EmployeeHolder.splitEmployee();
        DuplicateKeyChecker.checkKey(employeeDTO.get(0));
        Assertions.assertTrue(DuplicateKeyChecker.checkKey(employeeDTO.get(1)));


    }
}
