import java.sql.*;
import java.util.ArrayList;

public class BikeDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";
    public Maintainer maintainer = new Maintainer();

    public void loadBike(ArrayList<Bike> allBike) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `bike`");

            while(resultSet.next()){
                Bike Bike = new Bike(   // 創建新的腳踏車並加入allBike與outOfServiceBike
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("AuthorityID")
                );
                Bike.setState(resultSet.getString("state"));
                Bike.setNowStationString(resultSet.getString("nowStation"));
                Bike.setPillar(resultSet.getInt("pillar"));
                Bike.setIsCrossRegional(resultSet.getBoolean("IsCrossRegional"));
// 另外開表格到時候用裡面的邏輯存進去(?
                allBike.add(Bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAdjustableBike(ArrayList<Bike> adjustableBike) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `adjustablebike`");

            while(resultSet.next()){
                Bike Bike = new Bike(   // 創建新的腳踏車並加入allBike與outOfServiceBike
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("AuthorityID")
                );
                Bike.setState(resultSet.getString("state"));
                Bike.setNowStationString(resultSet.getString("nowStation"));
                Bike.setPillar(resultSet.getInt("pillar"));
                Bike.setIsCrossRegional(resultSet.getBoolean("IsCrossRegional"));
                adjustableBike.add(Bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBikeProperty(ArrayList<Bike> allBike) {

    }

    public void saveBike(Bike bike, String savetype) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            String sql = "INSERT INTO " + savetype + " (id, AuthorityID, type, state, nowStation, pillar, IsCrossRegional) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bike.getId());
            pstmt.setString(2, bike.getAuthorityID());
            pstmt.setString(3, bike.getType());
            pstmt.setString(4, bike.getState());

            if (bike.getnowStation() != null) {
                pstmt.setString(5, bike.getNowStation().getStationUID()); // 假設你要儲存的是站點的ID 可能不在站點上
            } else {
                pstmt.setNull(5, java.sql.Types.VARCHAR);
            }

            pstmt.setInt(6, bike.getPillar());
            pstmt.setBoolean(7, bike.isCrossRegional());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable(String savetype) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `" + savetype + "`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
