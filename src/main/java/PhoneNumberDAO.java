import java.sql.*;
import java.util.ArrayList;

public class PhoneNumberDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";

    /**
     * 此方法用來載入資料庫中的資料
     * @param PhoneNumberofCustomer
     */
    public void loadPhoneNumber(ArrayList<String> PhoneNumberofCustomer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `phonenumberofcustomer`");

            while(resultSet.next()){
                PhoneNumberofCustomer.add(resultSet.getString("phoneNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用於將資料存入資料庫中
     * @param phonenumber
     */
    public void savePhoneNumber(String phonenumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            String sql = "INSERT INTO PhoneNumberofCustomer (phoneNumber) " +
                    "VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phonenumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此變數用來刪除資料庫中的表格
     */
    public void dropTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `PhoneNumberofCustomer`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
