import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EasycardDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";

    /**
     * 使用此方法前要先load allRentalRecordForEasycard，不然會查不到這個rentalRecord
     * 這個方法是用來從資料庫載入Easycard資訊的
     * @param allEasycard
     * 表示所有的悠遊卡
     * @param allRentalRecordForEasycard
     * 表示悠遊卡的所有租借紀錄
     */
    public void loadEasycard(ArrayList<Easycard> allEasycard, List<RentalRecord> allRentalRecordForEasycard) {
        Iterator Iterator = new Iterator();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement("SELECT * FROM `easycard`;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Easycard easycard = new Easycard(
                        resultSet.getString("easyCardNumber"));
                easycard.setBalance(resultSet.getInt("balance"));
                easycard.setCustomerid(resultSet.getString("customerid"));


                String jsonString = resultSet.getString("rentalRecordForEasycard");

                if (jsonString != null && !jsonString.isEmpty()){
                    // 使用 Jackson 将 JSON 字符串转换为 int[] 数组
                    int[] rentalRecordid = objectMapper.readValue(jsonString, int[].class);

                    if (rentalRecordid != null) {
                        // 把這個id轉成rentalRecord
                        List<RentalRecord> rentalRecord = new ArrayList<>();
                        for (int j : rentalRecordid) {
                            rentalRecord.add(Iterator.getMaintainer().idGetRentalRecordForEasycard(allRentalRecordForEasycard, j));
                        }
                        // 此時這張easycard已經有一個List<RentalRecord>了
                        easycard.setRentalRecords(rentalRecord);
                    }
                }

                allEasycard.add(easycard);
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用來將資料存入資料庫，
     * @param easycard
     */
    public void saveEasycard(Easycard easycard) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String sql = "INSERT INTO easycard (easyCardNumber, balance, customerid, rentalRecordForEasycard) " +
                    "VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, easycard.getNumber());
            pstmt.setInt(2, easycard.getBalance());
            pstmt.setString(3, easycard.getCustomer() != null ? easycard.getCustomer().getIdNumber() : null);

            // 長度是每個悠遊卡的租借紀錄次數
            if (easycard.getRentalRecords() != null) {
                int[] rentalRecordId = new int[easycard.getRentalRecords().size()];

                // 把所有的id都init進即將要存進去的array
                for (int i = 0; i < rentalRecordId.length; i++) {
                    rentalRecordId[i] = easycard.getRentalRecords().get(i).getId();
                }

                // 把int[]變成json
                ObjectMapper objectMapper = new ObjectMapper();
                String rentalRecordIdjson = "";
                try {
                    rentalRecordIdjson = objectMapper.writeValueAsString(rentalRecordId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pstmt.setString(4, rentalRecordIdjson);
            }


            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 這個方法是用來把資料庫中的表格刪除，從而可以做到執行完一次就更新
     */
    public void dropTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `easycard`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
