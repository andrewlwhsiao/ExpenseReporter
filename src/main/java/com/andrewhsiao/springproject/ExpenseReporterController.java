package com.andrewhsiao.springproject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ExpenseReporterController {
    private ExpenseReporterService reportService;
    
    @GetMapping("/")
    public String home() throws IOException, GeneralSecurityException {
        return "redirect:/report"; 
    }

    @GetMapping("/report")
    public String report(Model model) throws IOException, GeneralSecurityException {
        model.addAttribute("balance", getService().getBalance());
        return "index";
    }

    @GetMapping("/clearDatabase")
    public String clearDatabase() throws IOException, GeneralSecurityException {
        getService().clearDatabase();
        return "redirect:/report";
    }

    @GetMapping("/getCategories")
    @ResponseBody
    public List<Expense> getCategories() throws IOException, GeneralSecurityException {
        return getService().categoriesSorted();
    }

    @GetMapping("/getPieChartData")
    @ResponseBody
    public List<List<String>> getPieChartData() throws IOException, GeneralSecurityException {
        return getService().getPieChartTable();
    }
 
    private ExpenseReporterService getService() throws IOException, GeneralSecurityException{
        if (reportService == null) {
            reportService = new ExpenseReporterService();
        } 
        return reportService;
    }

}
