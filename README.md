# ExpenseReporter
Web application (MVC Model) using Java Spring Boot where users can view a report of the expense data that they submit to Google Forms

## Procedure
1. The stored access token may have expired when starting the application, so delete the StoredCredential file in the tokens directory.
2. Run the SheetsDataRetriever.java file to make initial updates to the backend MySQL Database.
3. In the project directory terminal, run ./mvnw spring-boot:run to start the spring boot application.
4. Navigate to http://localhost:8080/report to view your expense report.

