import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class RentalRecordDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    /**
     * 需要先建立allRentalRecordForEasycard, allStation, allBike才能成功載入
     * 會把每一張easycard的租借紀錄放到easycard的List<RentalRecord>
     *     同時也會加入到allRentalRecordForEasycard
     * @param allRentalRecordForEasycard
     * @param allStation
     * @param allBike
     */
    public void loadRentalRecordForEasycard(ArrayList<RentalRecord> allRentalRecordForEasycard, ArrayList<Station> allStation, ArrayList<Bike> allBike) {
        Iterator Iterator = new Iterator();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `rentalrecordforeasycard`");

            while(resultSet.next()){
                String timeString = resultSet.getString("time");
                LocalDateTime localDateTime = LocalDateTime.parse(timeString, FORMATTER);

                RentalRecord rentalRecord = new RentalRecord(
                        localDateTime,
                        Iterator.getMaintainer().idgetStation(allStation, resultSet.getString("stationUID")),
                        Iterator.getMaintainer().idgetbike(allBike, resultSet.getString("bikeID")),
                        resultSet.getString("rentOrReturn"),
                        resultSet.getInt("cost")
                );

                rentalRecord.setId(resultSet.getInt("id"));

                allRentalRecordForEasycard.add(rentalRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 需要先建立allRentalRecordForCustomer, allStation, allBike才能成功載入
     * 會把每一個顧客的租借紀錄放到類別customer的List<RentalRecord>
     * 同時也會加入到allRentalRecordForCustomer
     * @param allrentalRecordForCustomer
     * @param allStation
     * @param allBike
     */
    public void loadRentalRecordForCustomer(ArrayList<RentalRecord> allrentalRecordForCustomer, ArrayList<Station> allStation, ArrayList<Bike> allBike) {
        Iterator Iterator = new Iterator();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `rentalrecordforcustomer`");

            while(resultSet.next()){
                String timeString = resultSet.getString("time");
                LocalDateTime localDateTime = LocalDateTime.parse(timeString, FORMATTER);

                RentalRecord rentalRecord = new RentalRecord(
                        localDateTime,
                        Iterator.getMaintainer().idgetStation(allStation, resultSet.getString("stationUID")),
                        Iterator.getMaintainer().idgetbike(allBike, resultSet.getString("bikeID")),
                        resultSet.getString("rentOrReturn"),
                        resultSet.getInt("cost")
                );

                rentalRecord.setId(resultSet.getInt("id"));

                allrentalRecordForCustomer.add(rentalRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 儲存RentalRecord到資料庫裡，只有一個rentalrecord
     * @param rentalRecordForEasycard
     */
    public void saveRentalRecordForEasycard(RentalRecord rentalRecordForEasycard) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            LocalDateTime localDateTime = rentalRecordForEasycard.getTime();
            String formattedDateTime = localDateTime.format(FORMATTER);

            String sql = "INSERT INTO rentalrecordforeasycard (id, time, stationUID, bikeID, rentOrReturn, cost) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rentalRecordForEasycard.getId());
            pstmt.setString(2, formattedDateTime);
            pstmt.setString(3, rentalRecordForEasycard.getStation().getStationUID());
            pstmt.setString(4, rentalRecordForEasycard.getBike().getId());
            pstmt.setString(5, rentalRecordForEasycard.getRentOrReturn());
            pstmt.setInt(6, rentalRecordForEasycard.getCost());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 儲存RentalRecord到資料庫裡，只有一個rentalrecord
     * @param rentalRecordForCustomer
     */
    public void saveRentalRecordForCustomer(RentalRecord rentalRecordForCustomer) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            LocalDateTime localDateTime = rentalRecordForCustomer.getTime();
            String formattedDateTime = localDateTime.format(FORMATTER);

            String sql = "INSERT INTO rentalrecordforcustomer (id, time, stationUID, bikeID, rentOrReturn, cost) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rentalRecordForCustomer.getId());
            pstmt.setString(2, formattedDateTime);
            pstmt.setString(3, rentalRecordForCustomer.getStation().getStationUID());
            pstmt.setString(4, rentalRecordForCustomer.getBike().getId());
            pstmt.setString(5, rentalRecordForCustomer.getRentOrReturn());
            pstmt.setInt(6, rentalRecordForCustomer.getCost());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 此變數用來刪除資料庫中的表格
     */
    public void dropTable(String Type) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `rentalrecordfor" + Type + "`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
