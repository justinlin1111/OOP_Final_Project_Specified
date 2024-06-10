import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class CustomerSystem {
    // Constructor
    public CustomerSystem() {
    }

    // 註冊功能
    public void register(String phoneNumber, String password, String passwordAgain, String idNumber, String email,
            String Easycardnumber, ArrayList<Easycard> allEasycard, ArrayList<String> PhoneNumbeofCustomer,
            ArrayList<Customer> customerList, ArrayList<String> idNumberofCustomer) {
        if (!password.equals(passwordAgain)) {
            System.out.println("請輸入相同的密碼");
            return;
        }
        if (PhoneNumbeofCustomer.contains(phoneNumber)) {
            System.out.println("此手機號碼已註冊");
            return;
        }
        if (idNumberofCustomer.contains(idNumber)) {
            System.out.println("此帳號已被註冊");
            return;
        }
        Easycard EasyCard = null;
        for (Easycard e : allEasycard) {
            if (e.getNumber().equals(Easycardnumber)) {
                EasyCard = e;
                break;
            }
        }
        if (EasyCard == null) {
            System.out.println("找不到您的悠遊卡");
            return;
        }
        if (EasyCard.getCustomer() != null) {
            System.out.println("此悠遊卡已被登記");
            return;
        }
        Customer newCustomer = new Customer(phoneNumber, password, idNumber, email, EasyCard);
        // 將新用戶加入用戶列表
        customerList.add(newCustomer);
        PhoneNumbeofCustomer.add(phoneNumber);
        idNumberofCustomer.add(idNumber);
        EasyCard.registerCustomer(newCustomer);
        System.out.println("註冊成功！");
    }

    // 登入功能
    public boolean login(String phoneNumber, String password, ArrayList<Customer> customerList) {
        // 檢查用戶是否存在
        for (Customer customer : customerList) {
            if (customer.getPhoneNumber().equals(phoneNumber) && customer.getPassword().equals(password)) {
                System.out.println("登入成功！");
                return true;
            }
        }
        System.out.println("登入失敗，請檢查手機號碼和密碼！");
        return false;
    }

    /**
     * 用來新增 Easycard
     * 
     * @param Easycard 新增的Easycard
     * @param Customer 想要新增的 Customer
     */
    public void addEasycard(String Easycardnum, String CustomerID, ArrayList<Easycard> allEasycards,
            ArrayList<Customer> customerList) {
        Easycard Easycard = idgetEasycard(allEasycards, Easycardnum);
        Customer Customer = idgetCustomer(CustomerID, customerList);
        if (Customer.getEasycard().size() >= 4) {
            System.out.println("您已註冊四張悠遊卡，無法再新增");
            return;
        }
        if (Easycard.getCustomer() != null) {
            System.out.println("已記名，若要更新請先註銷");
            return;
        }
        Customer.addEasycard(Easycard);
        Easycard.registerCustomer(Customer);
        System.out.println("成功新增！！");
    }

    /**
     * By KUO
     * 這個 function 用來借車 並使用當前時間
     * 
     * @param Station  傳入借車的站點
     * @param Easycard 用這張 Easycard 借車的
     */
    void RentBike(String StationUID, String Easycardnum, ArrayList<Easycard> allEasycard,
            ArrayList<Station> allStation, ArrayList<RentalRecord> allRentalRecordForEasycard, ArrayList<RentalRecord> allRentalRecordForCustomer) {
        Station Station = idgetStation(allStation, StationUID);
        Easycard Easycard = idgetEasycard(allEasycard, Easycardnum);
        if (Station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (Easycard == null) {
            System.out.println("查無此 Easycard");
            return;
        }
        Customer Customer = Easycard.getCustomer();
        if (Customer == null) {
            System.out.println("此 Easycard 尚未註冊");
            return;
        }
        if (Customer.getisRenting()) {
            System.out.println("已借車，不能再借");
            return;
        }
        if (Station.hasBikes() == false) {
            System.out.println("無可借用 YouBike");
            return;
        }
        if (Easycard.getBalance() <= 0) {
            System.out.println("餘額不足");
            return;
        }
        LocalDateTime now = LocalDateTime.now(); // 紀錄借車時間
        int Index = -1;
        for (int i = 0; i < Station.getBikesCapacity(); i++) {
            if (Station.getBikeArray()[i] != null && Station.getBikeArray()[i].getState().equals("OK")) {
                int action = 0;
                if (Station.getBrokenpillar().contains(i))
                    action = 1;
                if (action == 0) {
                    Index = i;
                    break;
                }
            }
        }
        Station.getBikeArray()[Index].setState("isRented"); // 更改 Bike State
        Station.getBikeArray()[Index].setPillar(-1);
        Station.getBikeArray()[Index].setnowStation(null);
        Customer.setisRenting(true); // 更新 Customer 資訊
        Customer.setrentedBike(Station.getBikeArray()[Index]); // 更新 Customer 資訊
        Customer.setrentalTime(now); // 更新 Customer 資訊
        Customer.setrentLocation(Station.getAuthorityID()); // 更新 Customer 資訊
        Customer.setusingEasycard(Easycard); // 更新 Customer 資訊
        // 新增租借紀錄
        int id = allRentalRecordForCustomer.size() + 1;
        RentalRecord RentalRecord = new RentalRecord(now, Station, Station.getBikeArray()[Index], "Rent", 0);
        RentalRecord.setId(id);
        allRentalRecordForEasycard.add(RentalRecord);
        allRentalRecordForCustomer.add(RentalRecord);
        Customer.addrentalRecords(RentalRecord);
        Easycard.addrentalRecords(RentalRecord);

        Station.PopBikeByArray(Index); // 更新 Station 資訊
        Station.rentBike_minusNumber(); // 更新 Station 資訊
        Station.determineBikeAlloutOfService(); // 更新 Station 資訊
        Station.determineNullPillaralloutOfService(); // 更新 Station 資訊
        return;
    }

    /**
     * 這個 function 用來借車 但可以指定時間
     * str 的格式 : YYYY-MM-DDTHH:MM:SS, ex. 2024-05-12T11:10:10
     * 
     * @param Station  傳入借車的站點
     * @param Easycard 用這張 Easycard 借車的
     * @param str      指定時間
     */
    void RentBike(String StationUID, String Easycardnum, String str, ArrayList<Easycard> allEasycard,
            ArrayList<Station> allStation, ArrayList<RentalRecord> allRentalRecordForEasycard, ArrayList<RentalRecord> allRentalRecordForCustomer) {
        Station Station = idgetStation(allStation, StationUID);
        Easycard Easycard = idgetEasycard(allEasycard, Easycardnum);
        if (Station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (Easycard == null) {
            System.out.println("查無此 Easycard");
            return;
        }
        Customer Customer = Easycard.getCustomer();
        if (Customer == null) {
            System.out.println("此 Easycard 尚未註冊");
            return;
        }
        if (Customer.getisRenting()) {
            System.out.println("已借車，不能再借");
            return;
        }
        if (Station.hasBikes() == false) {
            System.out.println("無可借用 YouBike");
            return;
        }
        if (Easycard.getBalance() <= 0) {
            System.out.println("餘額不足");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now;
        try {
            now = LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            System.out.println("時間輸入格是不正確");
            return;
        }
        int Index = -1;
        for (int i = 0; i < Station.getBikesCapacity(); i++) {
            if (Station.getBikeArray()[i] != null && Station.getBikeArray()[i].getState().equals("OK")) {
                int action = 0;
                if (Station.getBrokenpillar().contains(i))
                    action = 1;
                if (action == 0) {
                    Index = i;
                    break;
                }
            }
        }
        Station.getBikeArray()[Index].setState("isRented"); // 更改 Bike State
        Station.getBikeArray()[Index].setPillar(-1);
        Station.getBikeArray()[Index].setnowStation(null);
        Customer.setrentedBike(Station.getBikeArray()[Index]); // 更新 Customer 資訊
        Customer.setrentalTime(now); // 更新 Customer 資訊
        Customer.setisRenting(true); // 更新 Customer 資訊
        Customer.setrentLocation(Station.getAuthorityID()); // 更新 Customer 資訊
        Customer.setusingEasycard(Easycard); // 更新 Customer 資訊
        // 新增租借紀錄
        int id = allRentalRecordForCustomer.size() + 1;
        RentalRecord RentalRecord = new RentalRecord(now, Station, Station.getBikeArray()[Index], "Rent", 0);
        RentalRecord.setId(id);
        allRentalRecordForEasycard.add(RentalRecord);
        allRentalRecordForCustomer.add(RentalRecord);
        Customer.addrentalRecords(RentalRecord);
        Easycard.addrentalRecords(RentalRecord);

        Station.PopBikeByArray(Index); // 更新 Station 資訊
        Station.rentBike_minusNumber(); // 更新 Station 資訊
        Station.determineBikeAlloutOfService(); // 更新 Station 資訊
        Station.determineNullPillaralloutOfService();
    }

    /**
     * By KUO
     * 這個 function 用來還車 並使用當前時間
     * 
     * @param Station  傳入還車的站點
     * @param Easycard 用這張 Easycard 借車的
     */
    void ReturnBike(String StationUID, String Easycardnum, ArrayList<Easycard> allEasycard,
            ArrayList<Station> allStation, ArrayList<RentalRecord> allRentalRecordForEasycard, ArrayList<RentalRecord> allRentalRecordForCustomer) {
        Station Station = idgetStation(allStation, StationUID);
        Easycard Easycard = idgetEasycard(allEasycard, Easycardnum);
        if (Station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (Easycard == null) {
            System.out.println("查無此 Easycard");
            return;
        }
        Customer Customer = Easycard.getCustomer();
        if (Customer == null) {
            System.out.println("此 Easycard 尚未註冊");
            return;
        }
        if (Customer.getisRenting() == false) {
            System.out.println("無借車，無法還車");
            return;
        }
        if (Customer.getusingEasycard().equals(Easycard) != true) {
            System.out.println("刷錯張悠遊卡了");
            return;
        }
        if (Station.hasEmptySlots() == false) {
            System.out.println("無可用車位可還");
            return;
        }
        int Index = -1;
        for (int i = 0; i < Station.getBikesCapacity(); i++) {
            if (Station.getBikeArray()[i] == null) {
                int action = 0;
                if (Station.getBrokenpillar().contains(i))
                    action = 1;
                if (action == 0) {
                    Index = i;
                    break;
                }
            }
        }
        LocalDateTime now = LocalDateTime.now(); // 紀錄還車時間
        long minutes = ChronoUnit.MINUTES.between(Customer.getrentalTime(), now); // 計算租借時間
        if (minutes == 0)
            minutes = 1;

        int range; // 級距
        if (minutes % 30 == 0)
            range = (int) minutes / 30;
        else
            range = (int) minutes / 30 + 1;
        // 計算價格
        int cost = 0;
        for (int i = 1; i <= range; i++) {
            if (i == 1) {
                if (Customer.getrentLocation().equals("TPE"))
                    cost = cost + 0;
                else
                    cost = cost + 5;
            } else if (i >= 2 && i <= 8)
                cost = cost + 10;
            else if (i >= 9 && i <= 16)
                cost = cost + 20;
            else
                cost = cost + 40;
        }
        Easycard.PayForRent(cost); // 扣錢

        // 新增租借紀錄
        int id = allRentalRecordForCustomer.size() + 1;
        RentalRecord RentalRecord = new RentalRecord(now, Station, Customer.getrentedBike(), "Return", cost);
        RentalRecord.setId(id);
        allRentalRecordForEasycard.add(RentalRecord);
        allRentalRecordForCustomer.add(RentalRecord);
        Customer.addrentalRecords(RentalRecord);
        Easycard.addrentalRecords(RentalRecord);

        if (Customer.getrentedBike().getState().equals("isRented"))
            Customer.getrentedBike().setState("OK"); // 更改 Bike State
        else
            Customer.getrentedBike().setState("outOfService"); // 更改 Bike State
        Customer.getrentedBike().setnowStation(Station);
        Customer.getrentedBike().setPillar(Index);
        Customer.getrentedBike().determineIsCrossRegional();
        Station.PushBikeByArray(Customer.getrentedBike(), Index); // 更新 Station 資訊
        Station.returnBike_plusNumber(); // 更新 Station 資訊
        Station.determineBikeAlloutOfService(); // 更新 Station 資訊
        Station.determineNullPillaralloutOfService();
        Customer.setrentedBike(null); // 更新 Customer 資訊
        Customer.setisRenting(false); // 更新 Customer 資訊
        Customer.setrentalTime(null); // 更新 Customer 資訊
        Customer.setrentLocation(null); // 更新 Customer 資訊
        Customer.setusingEasycard(null); // 更新 Customer 資訊
        return;
    }

    /**
     * 這個 function 用來還車 但可以指定時間
     * str 的格式 : YYYY-MM-DDTHH:MM:SS, ex. 2024-05-12T11:10:10
     *
     * 
     * @param Station  傳入借車的站點
     * @param Easycard 用這張 Easycard 借車的
     * @param str      指定時間
     */
    void ReturnBike(String StationUID, String Easycardnum, String str, ArrayList<Easycard> allEasycard,
            ArrayList<Station> allStation, ArrayList<RentalRecord> allRentalRecordForEasycard, ArrayList<RentalRecord> allRentalRecordForCustomer) {
        Station Station = idgetStation(allStation, StationUID);
        Easycard Easycard = idgetEasycard(allEasycard, Easycardnum);
        if (Station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (Easycard == null) {
            System.out.println("查無此 Easycard");
            return;
        }
        Customer Customer = Easycard.getCustomer();
        if (Customer == null) {
            System.out.println("此 Easycard 尚未註冊");
            return;
        }
        if (Customer.getisRenting() == false) {
            System.out.println("無借車，無法還車");
            return;
        }
        if (Customer.getusingEasycard().equals(Easycard) != true) {
            System.out.println("刷錯張悠遊卡了");
            return;
        }
        if (Station.hasEmptySlots() == false) {
            System.out.println("無可用車位可還");
            return;
        }
        int Index = -1;
        for (int i = 0; i < Station.getBikesCapacity(); i++) {
            if (Station.getBikeArray()[i] == null) {
                int action = 0;
                if (Station.getBrokenpillar().contains(i))
                    action = 1;
                if (action == 0) {
                    Index = i;
                    break;
                }
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now;
        try {
            now = LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            System.out.println("時間輸入格是不正確");
            return;
        }
        long minutes = ChronoUnit.MINUTES.between(Customer.getrentalTime(), now); // 計算租借時間
        if (minutes == 0)
            minutes = 1;

        int range; // 級距
        if (minutes % 30 == 0)
            range = (int) minutes / 30;
        else
            range = (int) minutes / 30 + 1;
        // 計算價格
        int cost = 0;
        for (int i = 1; i <= range; i++) {
            if (i == 1) {
                if (Customer.getrentLocation().equals("TPE"))
                    cost = cost + 0;
                else
                    cost = cost + 5;
            } else if (i >= 2 && i <= 8)
                cost = cost + 10;
            else if (i >= 9 && i <= 16)
                cost = cost + 20;
            else
                cost = cost + 40;
        }
        Easycard.PayForRent(cost); // 扣錢

        // 新增租借紀錄
        int id = allRentalRecordForCustomer.size() + 1;
        RentalRecord RentalRecord = new RentalRecord(now, Station, Customer.getrentedBike(), "Return", cost);
        RentalRecord.setId(id);
        allRentalRecordForEasycard.add(RentalRecord);
        allRentalRecordForCustomer.add(RentalRecord);
        Customer.addrentalRecords(RentalRecord);
        Easycard.addrentalRecords(RentalRecord);

        if (Customer.getrentedBike().getState().equals("isRented"))
            Customer.getrentedBike().setState("OK"); // 更改 Bike State
        else
            Customer.getrentedBike().setState("outOfService"); // 更改 Bike State
        Customer.getrentedBike().setnowStation(Station);
        Customer.getrentedBike().setPillar(Index);
        Customer.getrentedBike().determineIsCrossRegional();
        Station.PushBikeByArray(Customer.getrentedBike(), Index); // 更新 Station 資訊
        Station.returnBike_plusNumber(); // 更新 Station 資訊
        Station.determineBikeAlloutOfService(); // 更新 Station 資訊
        Station.determineNullPillaralloutOfService();
        Customer.setrentedBike(null); // 更新 Customer 資訊
        Customer.setisRenting(false); // 更新 Customer 資訊
        Customer.setrentalTime(null); // 更新 Customer 資訊
        Customer.setrentLocation(null); // 更新 Customer 資訊
        Customer.setusingEasycard(null); // 更新 Customer 資訊
        return;
    }

    /**
     * 這個方法是用來儲值悠遊卡的，如果在欠錢的狀況下儲值金額不夠會導致儲值失敗
     * 
     * @param amount   我想要存入的數目
     * @param easycard 傳入的悠遊卡
     */
    public void topUpEasyCard(int amount, String Easycardnum, ArrayList<Easycard> allEasycard) {
        Easycard Easycard = idgetEasycard(allEasycard, Easycardnum);
        if (Easycard == null) {
            System.out.println("查無此 Easycard");
            return;
        }
        if (Easycard.getCustomer() == null) {
            System.out.println("本悠遊卡尚未註冊");
            return;
        }
        if (Easycard.getBalance() + amount < 0) {
            System.out.println("存款失敗，您存入的金額仍為負數，請存入金額使得餘額為正數");
            return;
        }
        Easycard.deposit(amount);
        System.out.println("存款成功，你存入的金額為" + amount);
    }

    /**
     * 這個方法是用來提交維修報告的，會向系統報告維修問題，並且把單車或是站點的狀態更改
     * 
     * @param bike              傳入需要維修的單車
     * @param station           傳入需要維修的站點
     * @param repairDescription 傳入需維修物品的描述
     */
    public void MaintenanceReport(String bikeID, String stationUID, int pillar, String repairDescription,
            ArrayList<Station> allStation, ArrayList<Bike> allBikes) {
        if (bikeID != null) {
            Bike bike = idgetbike(allBikes, bikeID);
            if (bike == null) {
                System.out.println("查無此 YouBike");
                return;
            }
            bike.setState("OutofService");
            if (bike.getnowStation() != null) {
                bike.getnowStation().determineBikeAlloutOfService();
                bike.getnowStation().determineNullPillaralloutOfService();
            }
            System.out.println("已成功提交維修通報：");
            System.out.println("車號：" + bike.getId());
        }
        if (stationUID != null) {
            Station station = idgetStation(allStation, stationUID);
            if (station == null) {
                System.out.println("查無此站點");
                return;
            }
            if (pillar < 1 || pillar > station.getBikesCapacity()) {
                System.out.println("輸入車柱數字有誤");
                return;
            }
            pillar = pillar - 1;
            station.pillarbroken(pillar);
            station.determineBikeAlloutOfService();
            station.determineNullPillaralloutOfService();
            System.out.println("已成功提交維修通報：");
            System.out.println("車柱資訊：" + station.getStationName() + " 第 " + String.valueOf(pillar + 1) + " 柱");
        }
        System.out.println("維修項目：" + repairDescription);
    }

    // 輸入 IdNumber 去找 Customer
    public Customer idgetCustomer(String IdNumber, ArrayList<Customer> customerList) {
        for (Customer Customer : customerList) {
            if (Customer.getIdNumber().equals(IdNumber)) {
                return Customer;
            }
        }
        return null;
    }

    /**
     * 用 easyCardNumber 去找 Easycard
     * 
     * @param a           allEasycard
     * @param PhoneNumber easyCardNumber
     * @return Easycard
     */
    public Easycard idgetEasycard(ArrayList<Easycard> a, String easyCardNumber) {
        for (Easycard Easycard : a) {
            if (Easycard.getNumber().equals(easyCardNumber))
                return Easycard;
        }
        return null;
    }

    /**
     * use id to get bike object
     * 
     * @param a  the arraylist contains all the bikes
     * @param id the target bike's id number
     * @return return the bike object havs the id number
     */
    private Bike idgetbike(ArrayList<Bike> a, String id) {
        for (Bike bike : a) {
            if (bike.getId().equals(id)) {
                return bike;
            }
        }
        return null;
    }

    /**
     * 用車站的 StationUID 去找車站
     * 
     * @param a  所有的車站
     * @param id 要找的車站的 StationUID
     * @return 車站
     */
    public Station idgetStation(ArrayList<Station> a, String StationUID) {
        for (Station Station : a) {
            if (Station.getStationUID().equals(StationUID))
                return Station;
        }
        return null;
    }

    public double getDistanceBetweenStationAndPoint(double longitude1, double latitude1, Station Station) {
        double longitude2 = Station.getPositionLon();
        double latitude2 = Station.getPositionLat();
        double theta = longitude1 - longitude2;
        double distance = 60 * 1.1515 * (180 / Math.PI) * Math.acos(
                Math.sin(latitude1 * (Math.PI / 180)) * Math.sin(latitude2 * (Math.PI / 180)) +
                        Math.cos(latitude1 * (Math.PI / 180)) * Math.cos(latitude2 * (Math.PI / 180))
                                * Math.cos(theta * (Math.PI / 180)));
        return Math.round(distance * 1.609344 * 1000 * 100.00) / 100.00;
    }

    public String checkStation(String str, ArrayList<Station> allStations) {
        if (idgetStation(allStations, str) == null)
            return null;
        return idgetStation(allStations, str).getStationInfo();
    }

    /**
     *
     * @param lon        查詢的經度
     * @param lat        查詢的緯度
     * @param r          查詢的距離，若不需要請填入 0 ，單位為公尺
     * @param str        查詢有車位 : "hasEmptySlot"，查詢有車 : "hasbike"，若不限制 : ""
     * @param allStation allstation
     * @return
     */
    public ArrayList<Station> checkStation(double lon, double lat, double r, String str,
                                           ArrayList<Station> allStation) {
        ArrayList<Station> a = new ArrayList<>();
        for (Station Station : allStation) {
            double dis = getDistanceBetweenStationAndPoint(lon, lat, Station);
            if (r == 0 || dis <= r) {
                if (str.equals("hasEmptySlot")) {
                    if (Station.hasEmptySlots())
                        a.add(Station);
                } else if (str.equals("hasbike")) {
                    if (Station.hasBikes())
                        a.add(Station);
                }
                if (str.equals(""))
                    a.add(Station);
            }
        }
        return a;
    }
}