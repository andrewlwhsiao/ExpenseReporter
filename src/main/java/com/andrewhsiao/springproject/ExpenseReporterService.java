package com.andrewhsiao.springproject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseReporterService {

    private ExpenseDatabaseService databaseService;

    /**
     * Constructor for an ExpenseReporterService instance, initializes a databaseService object
    **/
    public ExpenseReporterService() throws IOException, GeneralSecurityException {
        databaseService = new ExpenseDatabaseService();
    }

    /**
     * Uses the class' databaseService instance to get the total balance of expenses
     * @return total balance in String form
     */
    public String getBalance() {
        DecimalFormat format = new DecimalFormat("#.00");
        return "$" + format.format(databaseService.getBalance());
    }

    /**
     * Uses the class' databaseService instance to clear the database and reset the expense data
     */
    public void clearDatabase() {
        databaseService.clearDatabase();
    }

    /**
     * Uses the class' databaseService instance to get the categorical spending data
     * @return list of each category and the amount spent
     */
    public List<Expense> categoriesSorted() {
        Expense balance = new Expense();
        balance.amount = databaseService.getBalance();
        List<Expense> toReturn = databaseService.categoriesSorted();
        toReturn.add(0, balance);
        return toReturn;
    }

    /**
     * Uses the class' databaseService instance to get the categorical spending data and converts it 
     *  into valid format for Google Charts to process
     * @return list of each category and the amount spent
     */
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
