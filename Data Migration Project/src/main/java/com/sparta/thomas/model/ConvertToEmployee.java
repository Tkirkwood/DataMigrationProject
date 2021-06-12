package com.sparta.thomas.model;

import com.sparta.thomas.util.Logging;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConvertToEmployee {


    public List<EmployeeDTO> convert(List<String> file) {
        String line;
        List<EmployeeDTO> employeeDTO = new ArrayList<>();

          for(int i =1;i<file.size();i++){
              try {
                  employeeDTO.add(convert(file.get(i)));
              } catch (ParseException e) {
                  Logging.logger.error(e.getMessage());
              }
           }
        return employeeDTO;
       }

    public EmployeeDTO convert(String employee) throws ParseException {
        String[] employeeDetails = employee.split(",");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employeeDetails[0]);
        employeeDTO.setNamePrefix(employeeDetails[1]);
        employeeDTO.setFirstName(employeeDetails[2]);
        employeeDTO.setLastName(employeeDetails[4]);
        employeeDTO.setMiddleInitial(employeeDetails[3]);
        employeeDTO.setGender(employeeDetails[5]);
        employeeDTO.setEMail(employeeDetails[6]);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/yyyy");
        Date date = Date.valueOf(LocalDate.parse(employeeDetails[7],df));
        employeeDTO.setDateOfBirth(date);
        date = Date.valueOf(LocalDate.parse(employeeDetails[8],df));
        employeeDTO.setDateOfJoining(date);
        int salary = Integer.parseInt(employeeDetails[9]);
        employeeDTO.setSalary(salary);
        return employeeDTO;
    }



    }
