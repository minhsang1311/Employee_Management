package run;

import Model.Department;
import Model.Employee;
import dao.DepartmentDAO;
import dao.EmployeeDAO;
import database.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Choose {
    Scanner ip = new Scanner(System.in);
    public void choose() throws SQLException {
        System.out.println("Select: ");
        System.out.println("1 - Department");
        System.out.println("2 - Employee");
        System.out.println("3 - Exit");
        System.out.print("Choice: ");
        int selection = ip.nextInt();

        switch (selection) {
            case 1:
                department();
                choose();
                break;

            case 2:
                employee();
                choose();
                break;
            case 3:
                System.out.println("Thank You for coming, bye.");
                break;
            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
                choose();
        }
    }
    public void department() throws SQLException {
        System.out.println("Select: ");
        System.out.println("1 - Add Department");
        System.out.println("2 - Update Department");
        System.out.println("3 - Show List Department");
        System.out.println("4 - Exit");
        System.out.print("Choice: ");
        int selection = ip.nextInt();

        switch (selection) {
            case 1:
                addDepartment();
                choose();
                break;

            case 2:
                updateDepartment();
                choose();
                break;
            case 3:
                showAllDepartment();
                choose();
                break;
            case 4:
                System.out.println("Thank You for coming, bye.");
                break;
            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
                choose();
        }
    }
    public void addDepartment(){
        Scanner ip = new Scanner(System.in);
        System.out.println("Add Department: ");
        System.out.println("Department ID: ");
        String departmentId = ip.nextLine();
        System.out.println("Department Name: ");
        String departmentName = ip.nextLine();
        System.out.println("Number Of Room: ");
        int numberOfRoom = ip.nextInt();
        Department department = new Department(departmentId, departmentName, numberOfRoom);
        DepartmentDAO.getInstance().insert(department);
    }
    public void updateDepartment(){

    }
    public void showAllDepartment(){
        ArrayList<Department> list = DepartmentDAO.getInstance().selectAll();
        for (Department department: list) {
            System.out.println(department.toString());
        }
    }
    public void employee() throws SQLException {
        System.out.println("Select: ");
        System.out.println("1 - Add Department");
        System.out.println("2 - Update Department");
        System.out.println("3 - Show List Department");
        System.out.println("4 - Exit");
        System.out.print("Choice: ");
        int selection = ip.nextInt();

        switch (selection) {
            case 1:
                addEmployee();
                choose();
                break;

            case 2:
                updateEmployee();
                choose();
                break;
            case 3:
                deleteEmployee();
                choose();
                break;
            case 4:
                showAllEmployee();
                choose();
                break;
            case 5:
                System.out.println("Thank You for coming, bye.");
                break;
            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
                choose();
        }
    }
    private static boolean validateEmployeeType(String departmentId, String employeeType) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String query = "SELECT COUNT(*) FROM employee WHERE department_id = ? AND employee_type = ?";
        try (PreparedStatement ps = ((Connection) connection).prepareStatement(query)) {
            ps.setString(1, departmentId);
            ps.setString(2, employeeType);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if ((employeeType == "HEAD_OF_DEPARTMENT" && count >= 1) ||
                            (employeeType == "DEPUTY_HEAD" && count >= 2)) {
                        return false;
                    }
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return true;
    }
    public void addEmployee() throws SQLException {
        Scanner ip = new Scanner(System.in);
        System.out.println("Add Employee: ");
        System.out.println("Employee ID: ");
        String employeeId = ip.nextLine();
        System.out.println("fullName: ");
        String fullName = ip.nextLine();
        System.out.println("Date of birth (yyyy-MM-dd): ");
        String dob = ip.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateOfBirth = null;
        try {
            dateOfBirth = parseDateString(dob);
        } catch (DateTimeParseException e) {
            return;
        }
        System.out.println("Address: ");
        String address = ip.nextLine();
        System.out.println("Department: ");
        String departmentId = ip.nextLine();
        try {
            if (!checkDepartmentExist(departmentId)) {
                System.out.println("Department not found for ID: " + departmentId);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking department existence: " + e.getMessage());
            return;
        }
        System.out.println("Basic Salary: ");
        double basicSalary = ip.nextDouble();
        System.out.println("Insurance Rate: ");
        double insuranceRate = ip.nextInt();
        System.out.println("Employee Type: ");
        System.out.println("1 - HEAD_OF_DEPARTMENT");
        System.out.println("2 - DEPUTY_HEAD");
        System.out.println("3 - REGULAR_EMPLOYEE");
        System.out.println("4 - INTERN");
        System.out.print("Choice: ");
        int selection = ip.nextInt();
        String employeeType = null;
        switch (selection) {
            case 1:
                employeeType = "HEAD_OF_DEPARTMENT";
                break;

            case 2:
                employeeType = "DEPUTY_HEAD";
                break;
            case 3:
                employeeType = "REGULAR_EMPLOYEE";
                break;
            case 4:
                employeeType = "INTERN";
                break;
            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
        }
        try {
            if (!validateEmployeeType(departmentId, employeeType)) {
                System.out.println("Cannot add more " + employeeType + " to this department.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error validating employee type: " + e.getMessage());
            return;
        }
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFullName(fullName);
        employee.setDateOfBirth(dateOfBirth);
        employee.setAddress(address);
        Employee.setDepartmentId(departmentId);
        employee.setBasicSalary(basicSalary);
        employee.setInsuranceRate(insuranceRate);
        employee.setEmployeeType(employeeType);
        EmployeeDAO.getInstance().insert(employee);
    }
    public static LocalDate parseDateString(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(dob, formatter);
            return LocalDate.parse(dob, formatter);
        } catch (DateTimeException e) {
            return null;
        }
    }
    public boolean checkDepartmentExist(String departmentId) throws SQLException {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            String query = "SELECT * FROM department WHERE department_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, departmentId);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking department existence", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Log the exception or handle it as necessary
                    e.printStackTrace();
                }
            }
        }
    }
    public void updateEmployee(){
        Scanner ip = new Scanner(System.in);
        System.out.println("Input EmployeeId: ");
        System.out.println("Update Address: ");
        String address = ip.nextLine();
        System.out.println("Update Department: ");
        String departmentId = ip.nextLine();
        try {
            if (!checkDepartmentExist(departmentId)) {
                System.out.println("Department not found for ID: " + departmentId);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking department existence: " + e.getMessage());
            return;
        }
        System.out.println("Update Basic Salary: ");
        double basicSalary = ip.nextDouble();
        System.out.println("Update Insurance Rate: ");
        double insuranceRate = ip.nextInt();
        System.out.println("Employee Type: ");
        System.out.println("1 - HEAD_OF_DEPARTMENT");
        System.out.println("2 - DEPUTY_HEAD");
        System.out.println("3 - REGULAR_EMPLOYEE");
        System.out.println("4 - INTERN");
        System.out.print("Choice: ");
        int selection = ip.nextInt();
        String employeeType = null;
        switch (selection) {
            case 1:
                employeeType = "HEAD_OF_DEPARTMENT";
                break;

            case 2:
                employeeType = "DEPUTY_HEAD";
                break;
            case 3:
                employeeType = "REGULAR_EMPLOYEE";
                break;
            case 4:
                employeeType = "INTERN";
                break;
            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
        }
        try {
            if (!validateEmployeeType(departmentId, employeeType)) {
                System.out.println("Cannot add more " + employeeType + " to this department.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error validating employee type: " + e.getMessage());
            return;
        }
        String employeeId = ip.nextLine();
        Employee employee = new Employee();
        employee.setAddress(address);
        Employee.setDepartmentId(departmentId);
        employee.setBasicSalary(basicSalary);
        employee.setInsuranceRate(insuranceRate);
        employee.setEmployeeType(employeeType);
        EmployeeDAO.getInstance().update(employee);
        System.out.println("Update Employee Success");
    }
    public void deleteEmployee() {
        Scanner ip = new Scanner(System.in);
        System.out.println("Input EmployeeId: ");
        String employeeId = ip.nextLine();
        Employee delete = new Employee();
        delete.setEmployeeId(employeeId);
        EmployeeDAO.getInstance().delete(delete);
        System.out.println("Delete Employee Success");
    }
    public void showAllEmployee(){
        ArrayList<Employee> list = EmployeeDAO.getInstance().selectAll();
        for (Employee employee: list) {
            System.out.println(employee.toString());
        }
    }
}
