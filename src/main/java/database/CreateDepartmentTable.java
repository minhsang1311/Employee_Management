package database;

import java.sql.*;

public class CreateDepartmentTable {

    public static void main(String[] args) {
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "CREATE TABLE department ( "
                    + "department_id VARCHAR(255) PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "number_of_staff INT, "
                    + "number_of_room INT"
                    + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            System.out.println("Department table created successfully!");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}