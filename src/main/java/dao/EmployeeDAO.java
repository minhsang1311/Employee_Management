package dao;

import Model.Department;
import Model.Employee;
import database.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class EmployeeDAO implements DAOInterface<Employee> {
    public static EmployeeDAO getInstance() { return new EmployeeDAO(); }

    @Override
    public int insert(Employee t) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO employee(employeeId, fullName, dateOfBirth, address, departmentId, basicSalary, insuranceRate, employeeType)"
                    + "VALUES ('"+t.getEmployeeId()+"', '"+t.getFullName()+"', '"+t.getDateOfBirth()+"', '"+t.getAddress()+"', '"+t.getDepartmentId()+"', '"+t.getBasicSalary()+"', "+t.getInsuranceRate()+"', '"+t.getEmployeeType()+"' )";
            int result = statement.executeUpdate(sql);
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Employee t) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE employee"
                    + " SET "
                    + " address = '" +t.getAddress()+"'"
                    + ", department = '" +t.getDepartmentId()+"'"
                    + ", basicSalary = '" +t.getBasicSalary()+"'"
                    + ", insuranceRate = '" +t.getInsuranceRate()+"'"
                    + ", employeeType = '" +t.getEmployeeType()+"'"
                    + " WHERE employeeId = '"+t.getEmployeeId()+"'";
            int result = statement.executeUpdate(sql);
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Employee t) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM employee" +
                    " WHERE employeeId = '"+t.getEmployeeId()+"'";
            int result = statement.executeUpdate(sql);
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<Employee> selectAll() {
        ArrayList<Employee> result = new ArrayList<Employee>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM employee";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String employeeId = rs.getString("employee_id");
                String fullName = rs.getString("full_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String address = rs.getString("address");
                String departmentId = rs.getString("department_id");
                double basicSalary = rs.getDouble("basic_salary");
                double netSalary = rs.getDouble("net_salary");
                double insuranceRate = rs.getDouble("insurance_rate");
                String employeeType = rs.getString("employee_type");
                String headOfDepartmentName = rs.getString("employee_type");
                Employee employee = new Employee(employeeId, fullName, dateOfBirth, address, departmentId, basicSalary, netSalary, insuranceRate, employeeType, headOfDepartmentName);
                result.add(employee);
            }
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Employee selectById(Employee t) {
        return null;
    }

    @Override
    public ArrayList<Employee> selectByCondition(String condition) {
        return null;
    }

}
