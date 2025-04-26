package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class PayrollCalculator {
    public static void main(String[] args) {
        String filePath = "employees.csv";
        try{
            InputStream inputStream = PayrollCalculator.class.getClassLoader().getResourceAsStream("employees.csv");
            if (inputStream == null) {
                System.out.println("File not found");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");

                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                //Create a new Employee object
                Employee employee = new Employee(id, name, hoursWorked, payRate);

                //Print the employee info
                System.out.printf("ID: %d, Name: %s, GrossPay: %.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay());

            }
        reader.close();
        }
    catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
    }
    }
}
