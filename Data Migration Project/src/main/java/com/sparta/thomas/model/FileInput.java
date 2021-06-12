package com.sparta.thomas.model;


import com.sparta.thomas.util.Logging;
import com.sparta.thomas.util.LoadProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
    static private String inputFile = LoadProperties.getProperty("fileToMigrate");
   // static String inputFile = "resources/employees.csv";

    String line;
    List<String> fileInput = new ArrayList<>();

    public List<String> fileInput(){
    try(BufferedReader in=new BufferedReader(new InputStreamReader((new FileInputStream(inputFile)))))
    {
        while ((line = in.readLine()) != null) {
            fileInput.add(line);
        }



        return fileInput;
    }      catch (
            FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        Logging.logger.error(e.getMessage());
    }


    return null;
    }
}
