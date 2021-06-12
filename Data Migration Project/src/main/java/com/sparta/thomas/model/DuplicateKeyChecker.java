package com.sparta.thomas.model;

import java.util.HashSet;

public class DuplicateKeyChecker {
   private static HashSet key = new HashSet();

public static boolean checkKey(EmployeeDTO employee){
      if (!key.contains(employee.getEmpId()))
      {
         key.add(employee.getEmpId());
         return false;
      }else return true;

}


}
