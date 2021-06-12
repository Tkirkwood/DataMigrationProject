package com.sparta.thomas.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorRowHolder {
    private static List<EmployeeDTO> errors = new ArrayList<>();

    public static void addErrorRow(EmployeeDTO employee){
        errors.add(employee);
    }

    public static List<EmployeeDTO> getErrorList(){
        return errors;
    }
}
