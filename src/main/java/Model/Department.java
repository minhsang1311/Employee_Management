package Model;

import database.JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Department {
    private String departmentId;
    private String departmentName;
    private int numberOfEmployee;
    private int numberOfRoom;
    public Department() {
        super();
    }
    public Department(String departmentId, String departmentName, int numberOfRoom) {
        super();
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.numberOfEmployee = calculateEmployee();
        this.numberOfRoom = numberOfRoom;
    }
    public int calculateEmployee() {
        int count = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*)"
                    + "FROM employee"
                    + "WHERE department_id =" + departmentId;
            Statement statement = connection.createStatement();
            count = statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }

    public void setNumberOfEmployee(int numberOfEmployee) {
        this.numberOfEmployee = numberOfEmployee;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }
    @Override
    public String toString(){
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", numberOfEmployee=" + numberOfEmployee + '\'' +
                ", numberOfRoom=" + numberOfRoom +
                '}';
    }
}
