import java.sql.*;
import java.util.ArrayList;

public class IdNumberDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";

    public void loadIdNumber(ArrayList<String> idNumberofCustomer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `idnumberofcustomer`");

            while(resultSet.next()){
                idNumberofCustomer.add(resultSet.getString("idNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveIdNumber(String idnumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            String sql = "INSERT INTO idNumberofCustomer (idNumber) " +
                    "VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idnumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `idNumberofCustomer`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
