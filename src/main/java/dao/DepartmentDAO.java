package dao;

import Model.Department;
import database.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DepartmentDAO implements DAOInterface<Department> {
    public static DepartmentDAO getInstance() { return  new DepartmentDAO(); }

    @Override
    public int insert(Department t) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO department(departmentId, departmentName, numberOfRoom)"
                    + "VALUES ('"+t.getDepartmentId()+"', '"+t.getDepartmentName()+"', '"+t.getNumberOfRoom()+"')";
            int result = statement.executeUpdate(sql);
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Department t) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE department"
                    + " SET "
                    + " departmentName = '" +t.getDepartmentName()+"'"
                    + ", numberOfRoom = '" +t.getNumberOfRoom()+"'"
                    + " WHERE departmentId = '"+t.getDepartmentId()+"'";
            int result = statement.executeUpdate(sql);
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Department t) {
        try {
        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM department" +
                " WHERE departmentId = '"+t.getDepartmentId()+"'";
        int result = statement.executeUpdate(sql);
        JDBCUtil.closeConnection(connection);
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return 0;
    }

    @Override
    public ArrayList<Department> selectAll() {
        ArrayList<Department> result = new ArrayList<Department>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String departmentId = rs.getString("departmentId");
                String departmentName = rs.getString("departmentName");
                int numberOfEmployee = rs.getInt("numberOfEmployee");
                int numberOfRoom = rs.getInt("numberOfRoom");
                Department department = new Department(departmentId, departmentName, numberOfRoom);
                result.add(department);
            }
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Department selectById(Department t) {

        Department result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM department WHERE ID = '"+t.getDepartmentId()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String departmentId = rs.getString("departmentId");
                String departmentName = rs.getString("departmentName");
                int numberOfEmployee = rs.getInt("numberOfEmployee");
                int numberOfRoom = rs.getInt("numberOfRoom");
                result = new Department(departmentId, departmentName, numberOfRoom);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Department> selectByCondition(String condition) {
        return null;
    }
}
