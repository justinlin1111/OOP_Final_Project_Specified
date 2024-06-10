import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class feed_BikeandStation_data {

    public feed_BikeandStation_data() {
    };

    public void feed(ArrayList<Bike> allBike, ArrayList<Bike> outOfServiceBike, ArrayList<Station> allStation,
            Maintainer Maintainer) {
        // 登錄 Bike 資訊
        // Bike(String id, String type, String AuthorityID)
        File file = new File("C:\\Users\\acer\\IdeaProjects\\OOP_final_prosect_read_json_write_intp_SQL\\src\\main\\java\\Bikes.json");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int action = 0;
            String id = "";
            String type = "";
            String AuthorityID = "";
            while (true) {
                line = br.readLine();
                String Line = line.trim();
                // System.out.println(Line);
                if (Line.equals("["))
                    continue;
                if (Line.equals("]"))
                    break;
                if (Line.equals("{")) {
                    action = 1;
                    continue;
                }
                if (action == 1 || action == 2 || action == 3) {
                    String[] parts = line.split(":");
                    String need = parts[1];
                    String[] part = need.split("\"");
                    // System.out.println(part[1]);
                    if (action == 1)
                        id = part[1];
                    if (action == 2)
                        type = part[1];
                    if (action == 3)
                        AuthorityID = part[1];
                    action = action + 1;
                    continue;
                }
                if (action == 4) {
                    Bike Bike = new Bike(id, type, AuthorityID);
                    allBike.add(Bike);
                    outOfServiceBike.add(Bike);
                    action = 0;
                    continue;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 登入車站資訊
        // Station(String stationName, double PositionLon, double PositionLat, int
        // BikesCapacity, String AuthorityID)
        File files = new File("C:\\Users\\acer\\IdeaProjects\\OOP_final_prosect_read_json_write_intp_SQL\\src\\main\\java\\Taipei.json");
        try {
            FileReader fr = new FileReader(files);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int action = 0;
            double PositionLon = 0;
            double PositionLat = 0;
            String StationUID = "";
            String StationID = "";
            String stationName = "";
            String AuthorityID = "";
            String stationnameEN = "";
            String GeoHash = "";
            String address = "";
            String addressEN = "";
            int BikesCapacity = 0;
            while (true) {
                line = br.readLine();
                String Line = line.trim();
                // System.out.println(Line);
                if (Line.equals("["))
                    continue;
                if (Line.equals("]"))
                    break;
                if (Line.equals("{")) {
                    action = 1;
                    continue;
                }
                if (action == 12) {
                    Station Station = new Station(StationUID, StationID, AuthorityID, stationName, stationnameEN,
                            PositionLon, PositionLat, GeoHash, address, addressEN,
                            BikesCapacity);
                    allStation.add(Station);
                    action = 0;
                    continue;
                } else {
                    if (Line.equals("\"StationName\": {") || Line.equals("\"StationPosition\": {")
                            || Line.equals("\"StationAddress\": {") || Line.equals("},"))
                        continue;
                    if (action == 6 || action == 7 || action == 11) {
                        String[] parts = Line.split(" ");
                        String need = parts[1];
                        String[] part = need.split(",");
                        if (action == 6)
                            PositionLon = Double.parseDouble(part[0]);
                        if (action == 7)
                            PositionLat = Double.parseDouble(part[0]);
                        if (action == 11)
                            BikesCapacity = Integer.parseInt(part[0]);
                        action = action + 1;
                    } else {
                        String[] parts = Line.split(":");
                        String need = parts[1];
                        if (need.equals(" {"))
                            continue;
                        String[] part = need.split("\"");
                        String s = part[1];
                        // System.out.println(s);
                        if (action == 1)
                            StationUID = s;
                        if (action == 2)
                            StationID = s;
                        if (action == 3)
                            AuthorityID = s;
                        if (action == 4)
                            stationName = s;
                        if (action == 5)
                            stationnameEN = s;
                        if (action == 8)
                            GeoHash = s;
                        if (action == 9)
                            address = s;
                        if (action == 10)
                            addressEN = s;
                        action = action + 1;
                    }
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 登入車站資訊
        // Station(String stationName, double PositionLon, double PositionLat, int
        // BikesCapacity, String AuthorityID)
        File fileS = new File("C:\\Users\\acer\\IdeaProjects\\OOP_final_prosect_read_json_write_intp_SQL\\src\\main\\java\\NewTaipei.json");
        try {
            FileReader fr = new FileReader(fileS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int action = 0;
            double PositionLon = 0;
            double PositionLat = 0;
            String StationUID = "";
            String StationID = "";
            String stationName = "";
            String AuthorityID = "";
            String stationnameEN = "";
            String GeoHash = "";
            String address = "";
            String addressEN = "";
            int BikesCapacity = 0;
            while (true) {
                line = br.readLine();
                String Line = line.trim();
                // System.out.println(Line);
                if (Line.equals("["))
                    continue;
                if (Line.equals("]"))
                    break;
                if (Line.equals("{")) {
                    action = 1;
                    continue;
                }
                if (action == 12) {
                    Station Station = new Station(StationUID, StationID, AuthorityID, stationName, stationnameEN,
                            PositionLon, PositionLat, GeoHash, address, addressEN,
                            BikesCapacity);
                    allStation.add(Station);
                    action = 0;
                    continue;
                } else {
                    if (Line.equals("\"StationName\": {") || Line.equals("\"StationPosition\": {")
                            || Line.equals("\"StationAddress\": {") || Line.equals("},"))
                        continue;
                    if (action == 6 || action == 7 || action == 11) {
                        String[] parts = Line.split(" ");
                        String need = parts[1];
                        String[] part = need.split(",");
                        if (action == 6)
                            PositionLon = Double.parseDouble(part[0]);
                        if (action == 7)
                            PositionLat = Double.parseDouble(part[0]);
                        if (action == 11)
                            BikesCapacity = Integer.parseInt(part[0]);
                        action = action + 1;
                    } else {
                        String[] parts = Line.split(":");
                        String need = parts[1];
                        if (need.equals(" {"))
                            continue;
                        String[] part = need.split("\"");
                        String s = part[1];
                        // System.out.println(s);
                        if (action == 1)
                            StationUID = s;
                        if (action == 2)
                            StationID = s;
                        if (action == 3)
                            AuthorityID = s;
                        if (action == 4)
                            stationName = s;
                        if (action == 5)
                            stationnameEN = s;
                        if (action == 8)
                            GeoHash = s;
                        if (action == 9)
                            address = s;
                        if (action == 10)
                            addressEN = s;
                        action = action + 1;
                    }
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 登入 dock 資訊
        File File = new File("C:\\Users\\acer\\IdeaProjects\\OOP_final_prosect_read_json_write_intp_SQL\\src\\main\\java\\Docks.json");
        try {
            FileReader fr = new FileReader(File);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int action = 0;
            String DockUID = "";
            String DockID = "";
            String StationUID = "";
            String Bike = "";
            int s = 0;
            while (true) {
                line = br.readLine();
                String Line = line.trim();
                // System.out.println(Line);
                if (Line.equals("["))
                    continue;
                if (Line.equals("]"))
                    break;
                if (Line.equals("{")) {
                    action = 1;
                    continue;
                }
                if (action == 1 || action == 2 || action == 3 || action == 4) {
                    String[] parts = Line.split(":");
                    String need = parts[1];
                    if (need.equals(" \"\"") && action == 4) {
                        s = s + 1;
                        Bike = "";
                        action = action + 1;
                        continue;
                    }
                    String[] part = need.split("\"");
                    if (action == 1)
                        DockUID = part[1];
                    if (action == 2)
                        DockID = part[1];
                    if (action == 3)
                        StationUID = part[1];
                    if (action == 4) {
                        Bike = part[1];
                    }
                    action = action + 1;
                    continue;
                }
                if (action == 5) {
                    if (Bike.equals("") == false) {
                        int pillar = Integer.parseInt(DockID);
                        Maintainer.addBike(StationUID, outOfServiceBike, pillar, Bike, allStation);
                        action = 0;
                        continue;
                    } else
                        action = 0;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
