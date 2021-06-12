package com.sparta.thomas.controller;

import com.sparta.thomas.model.*;
import com.sparta.thomas.util.LoadProperties;

import java.util.ArrayList;
import java.util.List;

public class InserterThread extends Thread {
     MigratorDAO migratorDAO = new MigratorDAO();
    { migratorDAO.connectToDatabase();}

public void run () {
    List<EmployeeDTO> employeeDTO;
    List<EmployeeDTO> temp = new ArrayList<>();
    employeeDTO = EmployeeHolder.splitEmployee();
    if(employeeDTO.size()==0 || employeeDTO == null)
    {

       return;
    }
    int count = 0;
    int batchNumber =Integer.parseInt(LoadProperties.getProperty("batchInsertNumber"));
    for(EmployeeDTO employee : employeeDTO)
    {
        if(DuplicateKeyChecker.checkKey(employee))
        {
            ErrorRowHolder.addErrorRow(employee);
        }
    }
    while (count < employeeDTO.size()) {
        if (employeeDTO.size() - count < batchNumber) {
            while (count != employeeDTO.size()) {
                temp.add(employeeDTO.get(count++));
            }
            migratorDAO.addToDatabaseBatch(temp);
            temp.clear();
            break;
        }
        do {
            temp.add(employeeDTO.get(count++));
        } while (count % batchNumber != 0);
        if (temp != null) {
            migratorDAO.addToDatabaseBatch(temp);
            temp.clear();
        }
    }




    }





}

