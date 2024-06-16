package database;

import java.sql.*;

public class CreateEmployeeTable {

    public static void main(String[] args) {
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "CREATE TABLE employee ( "
                    + "employee_id VARCHAR(255) PRIMARY KEY, "
                    + "full_name VARCHAR(255) NOT NULL, "
                    + "date_of_birth DATE NOT NULL, "
                    + "address VARCHAR(255), "
                    + "department_id VARCHAR(255), "
                    + "basic_salary DECIMAL(10,2), "
                    + "net_salary DECIMAL(10,2), "
                    + "insurance_rate DECIMAL(5,2), "
                    + "employeeType ENUM ('HEAD_OF_DEPARTMENT', 'DEPUTY_HEAD', 'REGULAR_EMPLOYEE', 'INTERN') NOT NULL, "
                    + "headOfDepartmentName VARCHAR(255), "
                    + "FOREIGN KEY (department_id) REFERENCES department(department_id)"
                    + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            System.out.println("Employee table created successfully!");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}