import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";

    Iterator Iterator = new Iterator();
    public void loadCustomer(ArrayList<Customer> customerList, ArrayList<Easycard> allEasycard, ArrayList<Bike> allBike, List<RentalRecord> allRentalRecordForCustomer) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement("SELECT * FROM `customerlist`;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("password"),
                        resultSet.getString("idNumber"),
                        resultSet.getString("email"),
                        Iterator.getCustomerSystem().idgetEasycard(allEasycard, resultSet.getString("usingEasycardNumber"))
                );

                // 要把List<customer> List<Easycard>放進來
                String easycardjson = resultSet.getString("Easycard");

                if (easycardjson != null) {
                    String[] EasycardNumber = objectMapper.readValue(easycardjson, String[].class);
                    if (easycardjson != null) {
                        List<Easycard> Easycard = new ArrayList<>(EasycardNumber.length);
                        for (int i = 0; i < EasycardNumber.length; i++) {
                            if (EasycardNumber[i] != null) {
                                Easycard.add(Iterator.getCustomerSystem().idgetEasycard(allEasycard, EasycardNumber[i]));
                            } else {
                                Easycard.add(null);
                            }
                        }
                        customer.setEasycard(Easycard);
                    }
                } else {
                    customer.setEasycard(null);
                }


                // 放List<Record>
                String rentalRecordjson = resultSet.getString("rentalRecords");
                if (rentalRecordjson != null && !rentalRecordjson.isEmpty()) {
                    int[] rentalRecordId = objectMapper.readValue(rentalRecordjson, int[].class);

//                    System.out.println("should be 2 : " + rentalRecordId[1]);
                    if (rentalRecordId != null) {
                        List<RentalRecord> rentalRecord = new ArrayList<>();
                        for (int j : rentalRecordId) {
                            rentalRecord.add(Iterator.getMaintainer().idGetRentalRecordForCustomer(allRentalRecordForCustomer, j));
                        }
                        customer.setRentalRecords(rentalRecord);
                    }
                }



                if (resultSet.getString("rentedBikeId") != null){
                    customer.setRentedBike(Iterator.getMaintainer().idgetbike(allBike, resultSet.getString("rentedBikeId")));
                } else {
                    customer.setRentedBike(null);
                }

                Timestamp timestamp = resultSet.getTimestamp("rentalTime");
                LocalDateTime localDateTime;
                if (timestamp != null) {
                    localDateTime = timestamp.toLocalDateTime();
                } else {
                    localDateTime = null;
                }
                customer.setrentalTime(localDateTime);

                customer.setisRenting(resultSet.getBoolean("isRenting"));

                customer.setrentLocation(resultSet.getString("rentLocation"));

                customer.setusingEasycard(Iterator.getCustomerSystem().idgetEasycard(allEasycard, resultSet.getString("usingEasycardNumber")));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCustomer(Customer customer) {
        Iterator Iterator = new Iterator();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try  {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String sql = "INSERT INTO customerlist (phoneNumber, password, idNumber, email, Easycard, rentalRecords, rentedBikeId, rentalTime, isRenting, rentLocation, usingEasycardNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getPhoneNumber());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getIdNumber());
            pstmt.setString(4, customer.getEmail());

            // 存卡號String
            if (customer.getEasycard() != null){
                String[] Easycard = new String[customer.getEasycard().size()];
                for (int i = 0; i < customer.getEasycard().size(); i++) {
                    if (customer.getEasycard().get(i) != null) {
                        Easycard[i] = customer.getEasycard().get(i).getNumber();
                    } else {
                        Easycard[i] = null;
                    }
                }
                String easycardjson = "";
                try {
                    easycardjson = objectMapper.writeValueAsString(Easycard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pstmt.setString(5, easycardjson);
            } else {
                pstmt.setString(5, null);
            }


            // 存租借紀錄 跟EasycardDAO很像
            if (!customer.getRentalRecords().isEmpty()) {
                int[] rentalRecordId = new int[customer.getRentalRecords().size()];

                for (int i = 0; i < rentalRecordId.length; i++) {
                    rentalRecordId[i] = customer.getRentalRecords().get(i).getId();
                }
                String rentalRecordjson = "";
                try {
                    rentalRecordjson = objectMapper.writeValueAsString(rentalRecordId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pstmt.setString(6, rentalRecordjson);
            } else {
                pstmt.setString(6, null);
            }


            pstmt.setString(7, customer.getrentedBike() != null ? customer.getrentedBike().getId() : null);
            LocalDateTime rentalTime = customer.getrentalTime();
            if(rentalTime != null) {
                pstmt.setTimestamp(8, Timestamp.valueOf(rentalTime));
            } else {
                pstmt.setTimestamp(8, null);
            }
            pstmt.setBoolean(9, customer.getisRenting());
            pstmt.setString(10, customer.getrentLocation());
            pstmt.setString(11, customer.getusingEasycard() != null ? customer.getusingEasycard().getNumber() : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `customerlist`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
