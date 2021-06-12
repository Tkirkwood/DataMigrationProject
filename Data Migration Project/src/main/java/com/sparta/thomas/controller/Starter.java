package com.sparta.thomas.controller;

import com.sparta.thomas.model.FileInput;
import com.sparta.thomas.model.*;
import com.sparta.thomas.util.Logging;
import com.sparta.thomas.view.ErrorViewer;
import com.sparta.thomas.util.Printer;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public void start()  {
        Printer.print("Starting the migration from the csv file to the database\nThis may take a while");
        List<InserterThread> inserterThreads = new ArrayList<>();
        MigratorDAO migratorDAO = new MigratorDAO();
        migratorDAO.connectToDatabase();
        migratorDAO.dropTableIfExists();
        migratorDAO.createTable();


        long start = System.currentTimeMillis();
        FileInput fileInput = new FileInput();
        List<String> file = fileInput.fileInput();
        EmployeeHolder employeeHolder = new EmployeeHolder();
        employeeHolder.getEmployeeList(file);
        int listSize = employeeHolder.getListSize();
        Printer.print("the number of entries to be migrated is : " + listSize);
        int count = 0;
        while (employeeHolder.getCount()<listSize)
        {
           inserterThreads.add(new InserterThread());
           inserterThreads.get(count++).start();

        }



        for( Thread thread : inserterThreads)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Logging.logger.error(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        timeTaken /=1000;
        int minutes =(int) timeTaken/60;
        timeTaken = timeTaken%60;
        Printer.print("Data migration has finished\nThe time  taken to complete the migration : " + ANSI_RED + minutes + ":"+ timeTaken + ANSI_RESET);
        Printer.print("the number of entries that were successfully transferred was " + ANSI_RED + (listSize-ErrorRowHolder.getErrorList().size()) + ANSI_RESET);
        ErrorViewer.displayError(ErrorRowHolder.getErrorList());



    }
}
