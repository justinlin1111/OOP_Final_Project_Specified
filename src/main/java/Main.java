import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.util.ArrayList;

// 2024-05-12T11:10:10 手動添加時間的格式
public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8"); // 確認output 出的中文不是亂碼
        ArrayList<Bike> allBike = new ArrayList<>(); // 所有 YouBike
        ArrayList<Bike> adjustableBike = new ArrayList<>(); // 可以調度的車子
        ArrayList<Station> allStation = new ArrayList<>();
        ArrayList<Easycard> allEasycard = new ArrayList<>();
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<String> PhoneNumbeofCustomer = new ArrayList<>();
        ArrayList<String> idNumberofCustomer = new ArrayList<>();
        ArrayList<RentalRecord> allRentalRecordForEasycard = new ArrayList<>();
        ArrayList<RentalRecord> allRentalRecordForCustomer = new ArrayList<>();
        Iterator Iterator = new Iterator();
//        feed_BikeandStation_data feed_BikeandStation_data = new feed_BikeandStation_data();
//        feed_BikeandStation_data.feed(allBike, adjustableBike, allStation, Iterator.getMaintainer());

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
        System.out.println("=== 測試 MaintenanceReport 回報YouBike ===");
        System.out.println();

        System.out.println("=== 測試1 有人借用 ===");

        System.out.println("確認 002 無借車");
        System.out.println(Iterator.getCustomerSystem().idgetCustomer("002", customerList).infotoString());
        System.out.println("讓 002 有錢");
        Iterator.getCustomerSystem().topUpEasyCard(1000, "2-1", allEasycard);
        System.out.println("讓 002 借車");
        Iterator.getCustomerSystem().RentBike("TPE500101030", "2-1", "2024-05-14T15:30:00", allEasycard, allStation, allRentalRecordForEasycard,allRentalRecordForCustomer);
        System.out.println(Iterator.getCustomerSystem().idgetCustomer("002", customerList).infotoString());
        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
        System.out.println("報修");
        System.out.println();
        Iterator.getCustomerSystem().MaintenanceReport(
                Iterator.getCustomerSystem().idgetCustomer("002", customerList).getrentedBike().getId(), null, 0,
                null,
                allStation, allBike);
        System.out.println("還車");
        Iterator.getCustomerSystem().ReturnBike("TPE500101030", "2-1", "2024-05-14T16:00:25", allEasycard, allStation, allRentalRecordForEasycard, allRentalRecordForCustomer);
        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
        System.out.println("維修後");
        Iterator.getMaintainer().BikeidSetState(allBike,
                Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArray()[4].getId(), "OK");
        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());

        System.out.println();
        System.out.println("=== 測試2 無人借用(停在 Station) ===");
        System.out.println();

        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
        System.out.println("報修");
        Iterator.getCustomerSystem().MaintenanceReport(
                Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(),
                null, 0, null, allStation, allBike);
        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
        System.out.println("維修");
        Iterator.getMaintainer().BikeidSetState(allBike,
                Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(), "OK");
        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());

        System.out.println();



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
//                    Easycard EasyCard = new Easycard(s);
//                    allEasycard.add(EasyCard);
//                    s = String.valueOf(i);
//                }
//            } else {
//                Easycard EasyCard = new Easycard(s);
//                allEasycard.add(EasyCard);
//            }
//        }
//        System.out.println();
//        System.out.println("加入的悠遊卡");
//        for (Easycard EasyCard : allEasycard)
//            System.out.print(EasyCard.getNumber() + " ");
//        System.out.println();
//        System.out.println();
//
//        System.out.print("密碼不一 -> ");
//        Iterator.getCustomerSystem().register("001", "asd", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("成功註冊 -> ");
//        Iterator.getCustomerSystem().register("001", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("手機號碼已註冊 -> ");
//        Iterator.getCustomerSystem().register("001", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("帳號已被註冊 -> ");
//        Iterator.getCustomerSystem().register("002", "asd123", "asd123", "001", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("悠遊卡已被註冊 -> ");
//        Iterator.getCustomerSystem().register("002", "asd123", "asd123", "002", "001@", "1-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//        System.out.print("成功註冊 -> ");
//        Iterator.getCustomerSystem().register("002", "asd123", "asd123", "002", "001@", "2-1", allEasycard,
//                PhoneNumbeofCustomer, customerList, idNumberofCustomer);
//
//        System.out.println();
//        for (Customer Customer : customerList) {
//            System.out.println(Customer.infotoString());
//            System.out.println();
//        }
//
//        System.out.println("=== 測試一個人最多只能註冊四張 Easycard ===");
//        System.out.println();
//        System.out.print("成功註冊 -> ");
//        Iterator.getCustomerSystem().addEasycard("1-2", "001", allEasycard, customerList);
//        System.out.print("成功註冊 -> ");
//        Iterator.getCustomerSystem().addEasycard("1-3", "001", allEasycard, customerList);
//        System.out.print("悠遊卡已記名 -> ");
//        Iterator.getCustomerSystem().addEasycard("1-3", "001", allEasycard, customerList);
//        System.out.print("成功註冊 -> ");
//        Iterator.getCustomerSystem().addEasycard("1-4", "001", allEasycard, customerList);
//        System.out.print("已註冊四張 -> ");
//        Iterator.getCustomerSystem().addEasycard("1-5", "001", allEasycard, customerList);
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//
//        System.out.println();
//        System.out.println("=== 測試註銷 Easycard ===");
//        System.out.print("這張悠遊卡記名為 : ");
//        System.out.println(Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer().getIdNumber());
//        Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").removeCustomer();
//        System.out.print("這張悠遊卡記名為 : ");
//        if (Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer() == null)
//            System.out.println("null");
//        else
//            System.out.println(Iterator.getMaintainer().idgetEasycard(allEasycard, "1-3").getCustomer().getIdNumber());
//        System.out.println();
//        System.out.println("=== 測試登入 ===");
//        Iterator.getCustomerSystem().login("001", "asd12", customerList);
//        Iterator.getCustomerSystem().login("001", "asd123", customerList);
//
//        System.out.println();
//        System.out.println("=== 測試借還車 ===");
//        Iterator.getCustomerSystem().topUpEasyCard(1000, "1-1", allEasycard);
//        Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", "2024-05-13T10:00:00", allEasycard, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetEasycard(allEasycard, "1-1").getCustomer().infotoString());
//        System.out.println();
//        Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-2", "2024-05-13T11:00:00", allEasycard, allStation);
//        System.out.println();
//        Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", "2024-05-13T11:00:00", allEasycard, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//
//        System.out.println();
//        Iterator.getCustomerSystem().idgetEasycard(allEasycard, "1-1").Recordquery();
//        System.out.println();
//        Iterator.getCustomerSystem().idgetCustomer("001", customerList).Recordquery();
//        System.out.println();
//        System.out.println("=== 測試跨區查詢 ===");
//        for (Bike Bike : Iterator.getMaintainer().getCrossRegion(allBike)) {
//            System.out.print(Bike.infoforBike());
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            break;
//        }
//        System.out.print("跨區的車輛數 : ");
//        System.out.println(Iterator.getMaintainer().getCrossRegion(allBike).size());
//
//        System.out.println();
//        Iterator.getMaintainer().idgetEasycard(allEasycard, "1-1").getCustomer().Recordquery();
//        System.out.println();
//        System.out.println(Iterator.getMaintainer().idgetEasycard(allEasycard, "1-1").getCustomer().infotoString());
//
//        System.out.println();
//        System.out.println("=== 測試 getBikeArrayinfo ===");
//        for (Station Station : allStation) {
//            System.out.println(Station.getBikeArrayinfo());
//            System.out.println();
//            break;
//        }
//        System.out.println("===============================================");
//        System.out.println();
//        System.out.println("確認所有 YouBike 和 Station 都為正常");
//        System.out.println("確保等等測試不會出錯");
//
//        for (Bike Bike : allBike) {
//            if (Bike.getState().equals("OK") == false)
//                System.out.println(Bike.infoforBike());
//        }
//
//        for (Station Station : allStation) {
//            if (Station.getBrokenpillar().size() != 0)
//                System.out.println(Station.getStationInfo());
//        }
//
//        System.out.println();
//        System.out.println("===============================================");
//        System.out.println();
//
//        System.out.println("=== 測試 RentBike(指定時間) ===");
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101029").getStationInfo());
//        Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", "2024-05-14T10:10:10", allEasycard, allStation);
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//        System.out.println();
//        System.out
//                .println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).getrentedBike().infoforBike());
//
//        Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", "2024-05-14T10:10:10", allEasycard, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試 RentBike(不指定時間) ===");
//        Iterator.getCustomerSystem().RentBike("TPE500101029", "1-1", allEasycard, allStation);
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        Iterator.getCustomerSystem().ReturnBike("TPE500101029", "1-1", allEasycard, allStation);
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println();
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getStationInfo());
//
//        Iterator.getCustomerSystem().idgetCustomer("001", customerList).Recordquery();
//
//        System.out.println();
//        System.out.println("=== 測試 removebike ===");
//        System.out.println();
//
//        System.out.print("可調度的車子應為 0 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.println();
//
//        System.out.println("remove a bike");
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 1 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("remove a bike");
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101029").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 2 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("remove 8 bikes");
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        Iterator.getMaintainer().removeBike("TPE500101029", adjustableBike, allStation);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101029").getStationInfo());
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
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.println();
//
//        System.out.println("add a bike");
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 9 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("add a bike");
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArrayinfo());
//        System.out.print("可調度的車子應為 8 -> ");
//        System.out.println(adjustableBike.size());
//        System.out.println();
//
//        System.out.println("add 8 bikes");
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        Iterator.getMaintainer().addBike("TPE500101030", adjustableBike, allStation);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.print("可調度的車子應為 0 -> ");
//        System.out.println(adjustableBike.size());
//
//        System.out.println();
//        System.out.println("=== 測試 MaintenanceReport ===");
//        System.out.println();
//
//        System.out.println("=== 測試 MaintenanceReport 回報車柱 ===");
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 12, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 13, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 14, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 15, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 16, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 17, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 18, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 1, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 2, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 3, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 4, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 5, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 6, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 7, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 8, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 9, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 10, null, allStation, allBike);
//        Iterator.getCustomerSystem().MaintenanceReport(null, "TPE500101030", 11, null, allStation, allBike);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println();
//        System.out.print("測試能不能借車 應為不行 -> ");
//        Iterator.getCustomerSystem().RentBike("TPE500101030", "1-1", allEasycard, allStation);
//
//        System.out.println();
//        System.out.println("維修車柱 11, 5");
//        Iterator.getMaintainer().repairbrokenpillar("TPE500101030", 11, allStation);
//        Iterator.getMaintainer().repairbrokenpillar("TPE500101030", 5, allStation);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試 MaintenanceReport 回報YouBike ===");
//        System.out.println();
//
//        System.out.println("=== 測試1 有人借用 ===");
//
//        System.out.println("確認 001 無借車");
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println("讓 001 借車");
//        Iterator.getCustomerSystem().RentBike("TPE500101030", "1-1", "2024-05-14T15:30:00", allEasycard, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetCustomer("001", customerList).infotoString());
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println("報修");
//        System.out.println();
//        Iterator.getCustomerSystem().MaintenanceReport(
//                Iterator.getCustomerSystem().idgetCustomer("001", customerList).getrentedBike().getId(), null, 0,
//                null,
//                allStation, allBike);
//        System.out.println("還車");
//        Iterator.getCustomerSystem().ReturnBike("TPE500101030", "1-1", "2024-05-14T16:00:25", allEasycard, allStation);
//        System.out.println(Iterator.getCustomerSystem().idgetStation(allStation, "TPE500101030").getStationInfo());
//        System.out.println("維修後");
//        Iterator.getMaintainer().BikeidSetState(allBike,
//                Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getBikeArray()[4].getId(), "OK");
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101030").getStationInfo());
//
//        System.out.println();
//        System.out.println("=== 測試2 無人借用(停在 Station) ===");
//        System.out.println();
//
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//        System.out.println("報修");
//        Iterator.getCustomerSystem().MaintenanceReport(
//                Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(),
//                null, 0, null, allStation, allBike);
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//        System.out.println("維修");
//        Iterator.getMaintainer().BikeidSetState(allBike,
//                Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getBikeArray()[3].getId(), "OK");
//        System.out.println(Iterator.getMaintainer().idgetStation(allStation, "TPE500101031").getStationInfo());
//
//        System.out.println();
//        System.out.println("========================");
//        System.out.println("====== test OK!! =======");
//        System.out.println("========================");
//        System.out.println();
//        System.out.println(
//                "我個人認為義大利麵就應該拌42號混泥土，因為這個螺絲釘的長度很容易直接影響到挖掘機的扭矩。你往裡砸的時候，一瞬間他就會產生大量的高能蛋白，俗稱UFO，會嚴重影響經濟的發展，以至於對整個太平洋，和充電器的核污染。再或者說透過這勾股定理很容易推斷出人工飼養的東條英機，他是可以捕獲野生的三角函數，所以說不管這秦始皇的切面是否具有放射性，川普的N次方是否有沈澱物，都不會影響到沃爾瑪跟維爾康在南極匯合。");

    }
}
