package com.andrewhsiao.springproject;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDatabaseService {
    private final String jdbcURL = "jdbc:mysql://expense.c5s6eymugmjd.us-east-1.rds.amazonaws.com:3306/expenses";
    private final String username = "admin";
    private final String password = "Defi22foss!";
    private int numExpensesRetrieved = -1;

    public ExpenseDatabaseService() {}

    /**
     * Uses jdbc to connect to the rds MySQL database and add expense data to the database
     * @param values
     */
    public void addToDB(List<List<Object>> values) {
        Statement statement = null;
        ResultSet resultSet = null; 
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();
            numExpensesRetrieved += values.size();
            
            for (List<Object> value: values) {
                String name = String.valueOf(value.get(0));
                String category = String.valueOf(value.get(1));
                String location = String.valueOf(value.get(2));
                String date = String.valueOf(value.get(3));
                DecimalFormat format = new DecimalFormat("#.00");
                double amount = Double.valueOf(format.format(Double.valueOf(String.valueOf(value.get(4)))));
                String sqlQuery = String.format("INSERT INTO expenses(merchant, category, location, purchase_date, amount) VALUES('%s', '%s', '%s', '%s', %s);", name, category, location, date, amount);
                statement.executeUpdate(sqlQuery);
            }     
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Uses jdbc to connect to the rds MySQL database and query the number of expenses from the database
     * @return number of expenses
     */
    public int getnumExpensesRetrieved() {
        if (numExpensesRetrieved != -1) {
            return numExpensesRetrieved;
        }
        Statement statement = null;
        ResultSet resultSet = null; 
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();
            
            String sqlQuery = "SELECT COUNT(*) FROM expenses;";
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                numExpensesRetrieved = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numExpensesRetrieved;
    }

    /**
     * Uses jdbc to connect to the rds MySQL database and query the total balance from the database
     * @return total balance
     */
    public double getBalance() {
        Statement statement = null;
        ResultSet resultSet = null; 
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();
            
            String sqlQuery = "SELECT SUM(amount) FROM expenses;";
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                return resultSet.getDouble("SUM(amount)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * uses jdbc to connect to the rds MySQL database and query the categorical spending from the database
     * @return categorical spending data
     */
    public List<Expense> categoriesSorted() {
        List<Expense> categories = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null; 
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();
            
            String sqlQuery = "SELECT category, SUM(amount) AS amount FROM expenses GROUP BY category ORDER BY amount DESC;";
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                Expense category = new Expense();
                category.category = resultSet.getString("category");
                category.amount = resultSet.getDouble("amount");
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    /**
     * uses jdbc to connect to the rds MySQL database and delete all rows from the database
     */
    public void clearDatabase() {
        if (numExpensesRetrieved != -1) {
            return;
        }
        Statement statement = null;
        ResultSet resultSet = null; 
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();
            
            String sqlQuery = "DELETE FROM expenses;";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
