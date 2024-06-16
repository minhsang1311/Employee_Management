package Model;

import database.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class Employee {


    private String employeeId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String address;
    private static String departmentId;
    private String departmentName; // Reference to the department object
    private double basicSalary;
    private double netSalary;
    private double insuranceRate;
    private static String employeeType; // (e.g., "Head of Department", "Deputy Head", "Specialist")
    private String headOfDepartmentName;
    public Employee() {
        super();
    }

    public Employee(String employeeId, String fullName, LocalDate dateOfBirth, String address, String departmentId,
                    double basicSalary, double netSalary, double insuranceRate, String employeeType, String headOfDepartmentName) throws SQLException {
        super();
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.departmentId = departmentId;
        this.basicSalary = basicSalary;
        this.insuranceRate = insuranceRate;
        this.employeeType = employeeType;
        this.netSalary = calculateNetSalary();
        this.headOfDepartmentName = getHeadOfDepartmentName();// Calculate net salary upon creation
    }
public static String getHeadOfDepartmentName() throws SQLException {
    Connection connection = JDBCUtil.getConnection();
    String query = "SELECT fullName Employee WHERE employeeType = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, employeeType);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String headOfDepartmentName = rs.getString("fullName");
                // Return a new Department object
                return headOfDepartmentName;
            } else {
                throw new SQLException("Department not found for ID: " + departmentId);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        if (connection != null) {
            connection.close();
        }
    }
}
    // Method to fetch Department by ID
    public static String getDepartmentById() throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        String query = "SELECT department_name department WHERE department_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String departmentName = rs.getString("department_name");
                    // Return a new Department object
                    return departmentName;
                } else {
                    throw new SQLException("Department not found for ID: " + departmentId);
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static String getDepartmentId() {
        return departmentId;
    }

    public static void setDepartmentId(String departmentId) {
        Employee.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double calculateNetSalary() {
        return basicSalary * (1 - insuranceRate);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(double insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", departmentName=" + departmentName +
                ", basicSalary=" + basicSalary +
                ", netSalary=" + netSalary +
                ", insuranceRate=" + insuranceRate +
                ", employeeType=" + employeeType +
                ", headOfDepartmentName=" + headOfDepartmentName +
                '}';
    }
}
