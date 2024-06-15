package System;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;

public class StationDAO {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678asdfghjkl";

    /**
     * 從資料庫中載入資料，並且將每一個Station放到allStation裡
     * @param allStation
     * @param iterator
     * @param allBike
     */
    public void loadStation(ArrayList<Station> allStation, Iterator iterator, ArrayList<Bike> allBike) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String sql = "SELECT * FROM `station`;";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Station Station = new Station(
                        resultSet.getString("StationUID"),
                        resultSet.getString("StationID"),
                        resultSet.getString("AuthorityID"),
                        resultSet.getString("stationName"),
                        resultSet.getString("stationNameEN"),
                        resultSet.getDouble("PositionLon"),
                        resultSet.getDouble("PositionLat"),
                        resultSet.getString("GeoHash"),
                        resultSet.getString("address"),
                        resultSet.getString("addressEN"),
                        resultSet.getInt("BikesCapacity"));
                // 把station的狀態輸進去
                Station.setBikesNumber(resultSet.getInt("BikesNumber"));
                Station.setBikealloutOfService(resultSet.getBoolean("BikealloutOfService"));
                Station.setNullPillaralloutOfService(resultSet.getBoolean("nullPillaralloutOfService"));

                // 拿出bikeArray的時候可以直接用成bike 還要更新他的狀態
                String jsonString = resultSet.getString("BikeArray");
                if (jsonString != null){
                    ObjectMapper objectMapper = new ObjectMapper();
                    String[] stringArray = objectMapper.readValue(jsonString, String[].class);  // 取出這個json
                    Bike[] BikeArray = new Bike[resultSet.getInt("BikesCapacity")]; // 令出一個新的BikeArray
                    for (int i = 0; i < BikeArray.length; i++){
                        if (!stringArray[i].equals("null")){
                            BikeArray[i] = iterator.getMaintainer().idgetbike(allBike, stringArray[i]);
                        } else {
                            BikeArray[i] = null;
                        }
                    }
                    Station.setBikeArray(BikeArray);
                } else{
                    Bike[] BikeArray = new Bike[resultSet.getInt("BikesCapacity")];
                    for (int i = 0; i < BikeArray.length; i++){
                        BikeArray[i] = null;
                    }
                    Station.setBikeArray(BikeArray);
                }

                allStation.add(Station);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把資料傳到資料庫保存
     * @param station
     */
    public void saveStation(Station station) {
        // 先把BikeArray的bike轉成id
        // 其中要把它重新另一個String[]
        String[] bikeArray = new String[station.getBikeArray().length];
//        System.out.println("station.getBikeArray().length:" + station.getBikeArray().length);
        for (int i = 0; i < station.getBikeArray().length; i++){
//            System.out.println("i = " + i);
            if (station.getBikeArray()[i] != null){
                bikeArray[i] = station.getBikeArray()[i].getId();
            } else {
                bikeArray[i] = "null";
            }
        }

        // 将 String[] 数组转换为 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(bikeArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String sql = "INSERT INTO station (StationUID, StationID, AuthorityID, stationName, stationnameEN, PositionLon, PositionLat, GeoHash, address, addressEN, BikesCapacity, BikesNumber, BikealloutOfService, nullPillaralloutOfService, BikeArray) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, station.getStationUID());
            preparedStatement.setString(2, station.getStationID());
            preparedStatement.setString(3, station.getAuthorityID());
            preparedStatement.setString(4, station.getStationName());
            preparedStatement.setString(5, station.getStationnameEN());
            preparedStatement.setDouble(6, station.getPositionLon());
            preparedStatement.setDouble(7, station.getPositionLat());
            preparedStatement.setString(8, station.getGeoHash());
            preparedStatement.setString(9, station.getAddress());
            preparedStatement.setString(10, station.getAddressEN());
            preparedStatement.setInt(11, station.getBikesCapacity());
            preparedStatement.setInt(12, station.getBikesNumber());
            preparedStatement.setBoolean(13, station.isBikealloutOfService());
            preparedStatement.setBoolean(14, station.isNullPillaralloutOfService());
            preparedStatement.setString(15, jsonString);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此方法用來刪除資料庫中的表格
     */
    public void dropTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `station`");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
