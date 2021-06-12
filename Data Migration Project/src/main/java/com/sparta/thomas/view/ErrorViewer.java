package com.sparta.thomas.view;

import com.sparta.thomas.model.EmployeeDTO;
import com.sparta.thomas.util.Printer;

import java.util.List;

import static com.sparta.thomas.controller.Starter.ANSI_RED;
import static com.sparta.thomas.controller.Starter.ANSI_RESET;

public class ErrorViewer {

    public static void displayError(List<EmployeeDTO> employeeDTOS){
        Printer.print("there were " + ANSI_RED + employeeDTOS.size()+ANSI_RESET + " duplicate employeeIDs");
        for (EmployeeDTO employee : employeeDTOS){
            Printer.print(employee.getEmpId() + "  " + employee.getNamePrefix() + "  " + employee.getFirstName() + "  " + employee.getMiddleInitial()+ "  " + employee.getLastName());
        }
    }

}
