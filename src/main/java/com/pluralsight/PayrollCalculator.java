package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PayrollCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Step 1. Prompt the user for the input file
        System.out.println("Enter the name of the employee file: ");
        String fileName = input.nextLine();

        // Step 2. Prompt the user for the output file
        System.out.println("Enter the name of the payroll file to create: ");
        String outputFileName = input.nextLine();

        try {
            InputStream inputStream = PayrollCalculator.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                System.out.println("File not found: " + fileName);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<Employee> employees = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");

                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(id, name, hoursWorked, payRate);
                employees.add(employee);
            }
            reader.close();

            // Step 3. Write the output file
            if (outputFileName.endsWith(".csv")) {
                writeCsv(outputFileName, employees);
            } else if (outputFileName.endsWith(".json")) {
                writeJson(outputFileName, employees);
            } else {
                System.out.println("Unsupported file format. Please use .csv or .json");
            }
        } catch (IOException e) {
            System.out.println("Error processing the file: " + e.getMessage());
        }
    }

    // Method to write CSV output
    private static void writeCsv(String fileName, List<Employee> employees) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write("id|name|gross pay\n");

        for (Employee emp : employees) {
            writer.write(emp.getEmployeeId() + "|" + emp.getName() + "|" + String.format("%.2f", emp.getGrossPay()) + "\n");
        }

        writer.close();
        System.out.println("CSV payroll file created: " + fileName);
    }

    // Method to write JSON output
    private static void writeJson(String fileName, List<Employee> employees) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write("[\n");

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            writer.write("  { \"id\": " + emp.getEmployeeId() + ", \"name\": \"" + emp.getName() + "\", \"grossPay\": " + String.format("%.2f", emp.getGrossPay()) + " }");

            if (i < employees.size() - 1) {
                writer.write(",");
            }
            writer.write("\n");
        }

        writer.write("]");
        writer.close();
        System.out.println("JSON payroll file created: " + fileName);
    }
}
