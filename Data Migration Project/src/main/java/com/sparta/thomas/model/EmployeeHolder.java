package com.sparta.thomas.model;

import com.sparta.thomas.util.LoadProperties;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHolder {
   private static List<EmployeeDTO> employeeDTO = new ArrayList<>();
   private static int count = 0 ;
   private static int listSize;
    public static int getCount() {
        return count;
    }

    public static int getListSize() {
        return listSize;
    }


   private static  Object lock = new Object();

   public void getEmployeeList(List<String> file){
       ConvertToEmployee convertToEmployee = new ConvertToEmployee();
       this.employeeDTO = convertToEmployee.convert(file);
       this.listSize = employeeDTO.size();
   }

    static public List<EmployeeDTO> splitEmployee(){
       int threadLineCount = Integer.parseInt(LoadProperties.getProperty("threadLineCount"));

        List<EmployeeDTO> splitEmployee = new ArrayList<>();
        synchronized (lock) {
            if (employeeDTO.size()-count>threadLineCount) {
                for (int i = 0; i < threadLineCount; i++) {
                    splitEmployee.add(employeeDTO.get(i + count));
                }
                count = count + threadLineCount;
            }else {
                while (count < employeeDTO.size()){
                    splitEmployee.add(employeeDTO.get(count++));
                }
            }
            return splitEmployee;


        }
    }


}
