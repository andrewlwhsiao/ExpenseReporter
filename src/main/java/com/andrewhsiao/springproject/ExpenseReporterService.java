package com.andrewhsiao.springproject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ExpenseReporterService {

    private ExpenseDatabaseService databaseService;

    public ExpenseReporterService() throws IOException, GeneralSecurityException {
        databaseService = new ExpenseDatabaseService();
    }

    public String getBalance() {
        DecimalFormat format = new DecimalFormat("#.00");
        return "$" + format.format(databaseService.getBalance());
    }

    public List<Expense> categoriesSorted() {
        Expense balance = new Expense();
        balance.amount = databaseService.getBalance();
        List<Expense> toReturn = databaseService.categoriesSorted();
        toReturn.add(0, balance);
        return toReturn;
    }

    public List<List<String>> getPieChartTable() {
        List<Expense> categories = new ArrayList<>();
        categories = databaseService.categoriesSorted();
        List<List<String>> toReturn = new ArrayList<>();
        for (Expense category: categories) {
            List<String> list = new ArrayList<>();
            list.add(category.category);
            list.add(String.valueOf(category.amount));
            toReturn.add(list);
        }
        return toReturn;
    }
}
