package System;

import java.util.ArrayList;
import gui.LoginController;
import gui.MapController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// 2024-05-12T11:10:10 手動添加時間的格式
public class Main extends Application{
    public static ArrayList<Bike> allBike = new ArrayList<>(); // 所有 YouBike
    public static ArrayList<Bike> adjustableBike = new ArrayList<>(); // 可以調度的車子
    public static ArrayList<Station> allStation = new ArrayList<>();
    public static ArrayList<Easycard> allEasycard = new ArrayList<>();
    public static ArrayList<Customer> customerList = new ArrayList<>();
    public static ArrayList<String> PhoneNumbeofCustomer = new ArrayList<>();
    public static ArrayList<String> idNumberofCustomer = new ArrayList<>();
    public static ArrayList<RentalRecord> allRentalRecordForEasycard = new ArrayList<>();
    public static ArrayList<RentalRecord> allRentalRecordForCustomer = new ArrayList<>();
    public static Iterator Iterator = new Iterator();
    public static void main(String[] args) {

//        feed_BikeandStation_data feed_BikeandStation_data = new feed_BikeandStation_data();
//        feed_BikeandStation_data.feed(allBike, adjustableBike, allStation, System.Iterator.getMaintainer());

        // ====================================================================== //
        // ==================以上為 input Youbike 及 車站資訊勿更改================ //
        // ====================================================================== //

        DAO dao = new DAO();
        dao.loadData(
                allBike,
                adjustableBike,
                allStation,
                allEasycard,
                customerList,
                PhoneNumbeofCustomer,
                idNumberofCustomer,
                allRentalRecordForEasycard,
                allRentalRecordForCustomer
        );
        // ====================================================================== //
        // =================================操作區域=============================== //
        // ====================================================================== //


        launch(args);

        // ====================================================================== //
        // =================================操作區域=============================== //
        // ====================================================================== //
        dao.saveData(
                allBike,
                adjustableBike,
                allStation,
                allEasycard,
                customerList,
                PhoneNumbeofCustomer,
                idNumberofCustomer,
                allRentalRecordForEasycard,
                allRentalRecordForCustomer
        );





//        for (int i = 1; i < 21; i++) {
//            String s = String.valueOf(i);
//            if (i <= 3) {
//                for (int j = 1; j <= 5; j++) {
//                    s = s + "-" + String.valueOf(j);
//                    System.Easycard EasyCard = new System.Easycard(s);
//                    allEasycard.add(EasyCard);
//                    s = String.valueOf(i);
//                }
//            } else {
//                System.Easycard EasyCard = new System.Easycard(s);
//                allEasycard.add(EasyCard);
//            }
//        }
//        System.out.println();
//        System.out.println("加入的悠遊卡");
//        for (System.Easycard EasyCard : allEasycard)
//            System.out.print(EasyCard.getNumber() + " ");
//        System.out.println();
//        System.out.println();
//
//        System.out.print("密碼不一 -> ");
//        System.Iterator.getCustomerSystem().register("001", "asd", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("成功註冊 -> ");
//        System.Iterator.getCustomerSystem().register("001", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("手機號碼已註冊 -> ");
//        System.Iterator.getCustomerSystem().register("001", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("帳號已被註冊 -> ");
//        System.Iterator.getCustomerSystem().register("002", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("悠遊卡已被註冊 -> ");
//        System.Iterator.getCustomerSystem().register("002", "asd123", "asd123", "002", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("成功註冊 -> ");
//        System.Iterator.getCustomerSystem().register("002", "asd123", "asd123", "002", "001@", "2-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//
//        System.out.println();
//        for (System.Customer System.Customer : customerList) {
//            System.out.println(System.Customer.infotoString());
//            System.out.println();
//        }
//
//        System.out.println("=== 測試一個人最多只能註冊四張 System.Easycard ===");
//        System.out.println();
//        System.out.print("成功註冊 -> ");
//        System.Iterator.getCustomerSystem().addEasycard("1-2", "001", allEasycard, customerList);
//        System.out.print("成功註冊 -> ");
//        System.Iterator.getCustomerSystem().addEasycard("1-3", "001", allEasycard, customerList);
//        System.out.print("悠遊卡已記名 -> ");
//        System.Iterator.getCustomerSystem().addEasycard("1-3", "001", allEasycard, customerList);
//        System.out.print("成功註冊 -> ");
//        System.Iterator.getCustomerSystem().addEasycard("1-4", "001", allEasycard, customerList);
//        System.out.print("已註冊四張 -> ");
//        System.Iterator.getCustomerSystem().addEasycard("1-5", "001", allEasycard, customerList);
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//
//        System.out.println();
//        System.out.println("=== 測試註銷 System.Easycard ===");
//        System.out.print("這張悠遊卡記名為 : ");
//        System.out.println(System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer().getIdNumber());
//        System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").removeCustomer();
//        System.out.print("這張悠遊卡記名為 : ");
//        if (System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer() == null)
//            System.out.println("null");
//        else
//            System.out.println(System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer().getIdNumber());
//        System.out.println();
//        System.out.println("=== 測試登入 ===");
//        System.Iterator.getCustomerSystem().login("001", "asd12", customerList);
//        System.Iterator.getCustomerSystem().login("001", "asd123", customerList);
//
//        System.out.println();
//        System.out.println("=== 測試借還車 ===");
//        System.Iterator.getCustomerSystem().topUpEasyCard(1000, "1-1", allEasycard);
//        System.Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", "2024-05-13T10:00:00", allEasycard, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetEasycard(allEasycard, "1-1").getCustomer().infotoString());
//        System.out.println();
//        System.Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-2", "2024-05-13T11:00:00", allEasycard, allStation);
//        System.out.println();
//        System.Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", "2024-05-13T11:00:00", allEasycard, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//
//        System.out.println();
//        System.Iterator.getCustomerSystem().idgetEasycard(allEasycard, "1-1").Recordquery();
//        System.out.println();
//        System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).Recordquery();
//        System.out.println();
//        System.out.println("=== 測試跨區查詢 ===");
//        for (System.Bike System.Bike : System.Iterator.getMaintainer().getCrossRegion(allBike)) {
//            System.out.print(System.Bike.infoforBike());
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            break;
//        }
//        System.out.print("跨區的車輛數 : ");
//        System.out.println(System.Iterator.getMaintainer().getCrossRegion(allBike).size());
//
//        System.out.println();
//        System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-1").getCustomer().Recordquery();
//        System.out.println();
//        System.out.println(System.Iterator.getMaintainer().idgetEasycard(allEasycard, "1-1").getCustomer().infotoString());
//
//        System.out.println();
//        System.out.println("=== 測試 getBikeArrayinfo ===");
//        for (System.Station System.Station : allStation) {
//            System.out.println(System.Station.getBikeArrayinfo());
//            System.out.println();
//            break;
//        }
//        System.out.println("===============================================");
//        System.out.println();
//        System.out.println("確認所有 YouBike 和 System.Station 都為正常");
//        System.out.println("確保等等測試不會出錯");
//
//        for (System.Bike System.Bike : allBike) {
//            if (System.Bike.getState().equals("OK") == false)
//                System.out.println(System.Bike.infoforBike());
//        }
//
//        for (System.Station System.Station : allStation) {
//            if (System.Station.getBrokenpillar().size() != 0)
//                System.out.println(System.Station.getStationInfo());
//        }
//
//        System.out.println();
//        System.out.println("===============================================");
//        System.out.println();
//
//        System.out.println("=== 測試 RentBike(指定時間) ===");
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101029").getStationInfo());
//        System.Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", "2024-05-14T10:10:10", allEasycard, allStation);
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//        System.out.println();
//        System.out
//                .println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).getrentedBike().infoforBike());
//
//        System.Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", "2024-05-14T10:10:10", allEasycard, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試 RentBike(不指定時間) ===");
//        System.Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", allEasycard, allStation);
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        System.Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", allEasycard, allStation);
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).Recordquery();
//
//        System.out.println();
//        System.out.println("=== 測試 removebike ===");
//        System.out.println();
//
//        System.out.print("可調度的車子應為 0 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.println();
//
//        System.out.println("remove a bike");
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 1 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("remove a bike");
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 2 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("remove 8 bikes");
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101029").getStationInfo());
//        System.out.print("可調度的車子應為 10 -> ");
//        System.out.println(adjustableBike.size());
//
//        System.out.println();
//        System.out.println("=== 測試 addbike ===");
//        System.out.println();
//
//        System.out.print("可調度的車子應為 10 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.println();
//
//        System.out.println("add a bike");
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 9 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("add a bike");
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 8 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("add 8 bikes");
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.print("可調度的車子應為 0 -> ");
//        System.out.println(adjustableBike.size());
//
//        System.out.println();
//        System.out.println("=== 測試 MaintenanceReport ===");
//        System.out.println();
//
//        System.out.println("=== 測試 MaintenanceReport 回報車柱 ===");
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 12, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 13, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 14, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 15, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 16, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 17, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 18, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 1, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 2, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 3, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 4, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 5, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 6, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 7, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 8, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 9, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 10, null, allStation, allBike);
//        System.Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 11, null, allStation, allBike);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println();
//        System.out.print("測試能不能借車 應為不行 -> ");
//        System.Iterator.getCustomerSystem().RentBike("TPE500101030", "1-1", allEasycard, allStation);
//
//        System.out.println();
//        System.out.println("維修車柱 11, 5");
//        System.Iterator.getMaintainer().repairbrokenpillar("TPE500101030", 11, allStation);
//        System.Iterator.getMaintainer().repairbrokenpillar("TPE500101030", 5, allStation);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試 MaintenanceReport 回報YouBike ===");
//        System.out.println();
//
//        System.out.println("=== 測試1 有人借用 ===");
//
//        System.out.println("確認 001 無借車");
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println("讓 001 借車");
//        System.Iterator.getCustomerSystem().RentBike("TPE500101030", "1-1", "2024-05-14T15:30:00", allEasycard, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println("報修");
//        System.out.println();
//        System.Iterator.getCustomerSystem().MaintenanceReport(
//                System.Iterator.getCustomerSystem().idgetCustomer("001", customerList).getrentedBike().getId(), null, 0,
//                null,
//                allStation, allBike);
//        System.out.println("還車");
//        System.Iterator.getCustomerSystem().ReturnBike("TPE500101030", "1-1", "2024-05-14T16:00:25", allEasycard, allStation);
//        System.out.println(System.Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println("維修後");
//        System.Iterator.getMaintainer().BikeidSetState(allBike,
//                System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArray()[4].getId(), "OK");
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試2 無人借用(停在 System.Station) ===");
//        System.out.println();
//
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//        System.out.println("報修");
//        System.Iterator.getCustomerSystem().MaintenanceReport(
//                System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(),
//                null, 0, null, allStation, allBike);
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//        System.out.println("維修");
//        System.Iterator.getMaintainer().BikeidSetState(allBike,
//                System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(), "OK");
//        System.out.println(System.Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//
//        System.out.println();
//        System.out.println("========================");
//        System.out.println("====== test OK!! =======");
//        System.out.println("========================");
//        System.out.println();
//        System.out.println(
//                "我個人認為義大利麵就應該拌42號混泥土，因為這個螺絲釘的長度很容易直接影響到挖掘機的扭矩。你往裡砸的時候，一瞬間他就會產生大量的高能蛋白，俗稱UFO，會嚴重影響經濟的發展，以至於對整個太平洋，和充電器的核污染。再或者說透過這勾股定理很容易推斷出人工飼養的東條英機，他是可以捕獲野生的三角函數，所以說不管這秦始皇的切面是否具有放射性，川普的N次方是否有沈澱物，都不會影響到沃爾瑪跟維爾康在南極匯合。");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //preload the map because it can load in another controller idk why
            FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("/gui/MapScreen.fxml"));
            Parent mapRoot = mapLoader.load();
            MapController mapC = mapLoader.getController();
            mapC.addMap();
            FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
            Parent loginRoot = loginLoad.load();
            LoginController loginC = loginLoad.getController();

            loginC.setMapInit(mapLoader, mapRoot, mapC);

            Scene scene = new Scene(loginRoot);
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    primaryStage.close();
                }
            });

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Login Screen");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

