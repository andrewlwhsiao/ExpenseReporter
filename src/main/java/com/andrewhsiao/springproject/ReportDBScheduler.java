package com.andrewhsiao.springproject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReportDBScheduler {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how many hours per update: ");
        int updateFreq = scanner.nextInt();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SheetsDataRetriever sheetsRetriever = new SheetsDataRetriever();
        ExpenseDatabaseService databaseService = new ExpenseDatabaseService();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                updateDatabase(sheetsRetriever, databaseService);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, updateFreq, TimeUnit.HOURS);
        scanner.close();
    }

    private static void updateDatabase(SheetsDataRetriever sheetsRetriever, ExpenseDatabaseService databaseService) throws IOException{
        List<List<Object>> sheetData = sheetsRetriever.retrieveData();
        databaseService.addToDB(sheetData);
    }
}
