package System;

import java.util.ArrayList;

public class DAO {
    // 引入資料庫需要的操作函式
    StationDAO stationdao = new StationDAO();
    BikeDAO bikedao = new BikeDAO();
    EasycardDAO easycarddao = new EasycardDAO();
    CustomerDAO customerdao = new CustomerDAO();
    RentalRecordDAO rentalRecordDAO = new RentalRecordDAO();
    PhoneNumberDAO phoneNumberdao = new PhoneNumberDAO();
    IdNumberDAO idNumberdao = new IdNumberDAO();
    Iterator Iterator = new Iterator();

    /**
     * 將所有資料載入
     * 整合全部的DAO，如此就不需要知道背後運作的邏輯，只需要把想要取得的資料丟進來即可
     * @param allBike
     * 表示一個陣列存放所有的腳踏車
     * @param adjustableBike
     * 表示一個陣列存放所有可調度的腳踏車
     * @param allStation
     * 表示一個陣列存放所有的站點
     * @param allEasycard
     * 表示一個陣列存放所有的悠遊卡
     * @param customerList
     * 表示一個陣列存放所有的顧客
     * @param PhoneNumbeofCustomer
     * 表示一個陣列存放所有顧客的手機號碼
     * @param idNumberofCustomer
     * 表示一個陣列存放所有顧客的身分證字號
     * @param allRentalRecordForEasycard
     * 表示一個陣列存放所有悠遊卡的租借紀錄
     * @param allRentalRecordForCustomer
     * 表示一個陣列存放所有顧客的租借紀錄
     */
    public void loadData(ArrayList<Bike> allBike,
                         ArrayList<Bike> adjustableBike,
                         ArrayList<Station> allStation,
                         ArrayList<Easycard> allEasycard,
                         ArrayList<Customer> customerList,
                         ArrayList<String> PhoneNumbeofCustomer,
                         ArrayList<String> idNumberofCustomer,
                         ArrayList<RentalRecord> allRentalRecordForEasycard,
                         ArrayList<RentalRecord> allRentalRecordForCustomer) {
        System.out.println("===test load all bike===");
        bikedao.loadBike(allBike);
        System.out.println("done\n");

        System.out.println("===test load all adjustable bike===");
        bikedao.loadAdjustableBike(adjustableBike);
        System.out.println("done\n");

        System.out.println("===test load all station===");
        stationdao.loadStation(allStation, Iterator, allBike);
        System.out.println("done\n");

//         把nowStation set進去，因為在bike建立時，station不存在，因此要在station建立後手動添加
        for (Bike bike: allBike){
            if (bike.getNowStationString() != null) {
                bike.setnowStation(Iterator.getMaintainer().idgetStation(allStation, bike.getNowStationString()));
            } else {
                bike.setnowStation(null);
            }
        }

        // adjustable bike同理
        for (Bike adjustablebike: adjustableBike){
            adjustablebike.setnowStation(Iterator.getMaintainer().idgetStation(allStation, adjustablebike.getNowStationString()));
        }

        System.out.println("===test load rentalRecordForEasycard===");
        rentalRecordDAO.loadRentalRecordForEasycard(allRentalRecordForEasycard, allStation, allBike);
        System.out.println("done\n");

        System.out.println("===test load rentalRecordForCustomer===");
        rentalRecordDAO.loadRentalRecordForCustomer(allRentalRecordForCustomer, allStation, allBike);
        System.out.println("done\n");


        System.out.println("===test load all easycard===");
        easycarddao.loadEasycard(allEasycard, allRentalRecordForEasycard);
        System.out.println("done\n");


        System.out.println("===test load all customer===");
        customerdao.loadCustomer(customerList, allEasycard, allBike, allRentalRecordForCustomer);
        System.out.println("done\n");

        // 在load easycard時還沒有customer，所以要在建完後做
        for (Easycard easycard: allEasycard) {
            if (easycard.getCustomerid() != null) {
                easycard.setCustomer(Iterator.getCustomerSystem().idgetCustomer(easycard.getCustomerid(), customerList));
            } else {
                easycard.setCustomer(null);
            }
        }

        System.out.println("===test load PhoneNumbeofCustomer===");
        phoneNumberdao.loadPhoneNumber(PhoneNumbeofCustomer);
        System.out.println("done\n");

        System.out.println("===test load idNumberofCustomer===");
        idNumberdao.loadIdNumber(idNumberofCustomer);
        System.out.println("done\n");

        System.out.println("=================");
        System.out.println("==load all data==");
        System.out.println("=================\n\n");
    }

    /**
     * 將所有資料存入資料庫
     * 整合全部的DAO，如此就不需要知道背後運作的邏輯，只需要把想要存入的資料丟進來即可
     * @param allBike
     * 表示一個陣列存放所有的腳踏車
     * @param adjustableBike
     * 表示一個陣列存放所有可調度的腳踏車
     * @param allStation
     * 表示一個陣列存放所有的站點
     * @param allEasycard
     * 表示一個陣列存放所有的悠遊卡
     * @param customerList
     * 表示一個陣列存放所有的顧客
     * @param PhoneNumbeofCustomer
     * 表示一個陣列存放所有顧客的手機號碼
     * @param idNumberofCustomer
     * 表示一個陣列存放所有顧客的身分證字號
     * @param allRentalRecordForEasycard
     * 表示一個陣列存放所有悠遊卡的租借紀錄
     * @param allRentalRecordForCustomer
     * 表示一個陣列存放所有顧客的租借紀錄
     */
    public void saveData(ArrayList<Bike> allBike,
                         ArrayList<Bike> adjustableBike,
                         ArrayList<Station> allStation,
                         ArrayList<Easycard> allEasycard,
                         ArrayList<Customer> customerList,
                         ArrayList<String> PhoneNumbeofCustomer,
                         ArrayList<String> idNumberofCustomer,
                         ArrayList<RentalRecord> allRentalRecordForEasycard,
                         ArrayList<RentalRecord> allRentalRecordForCustomer) {
        // 存進SQL
        System.out.println("save all station...");
        stationdao.dropTable();
        for(Station station: allStation){
            stationdao.saveStation(station);
        }
        System.out.println("done\n");

        System.out.println("save all bike...");
        bikedao.dropTable("bike");
        for (Bike bike: allBike){
            bikedao.saveBike(bike, "bike");
        }
        System.out.println("done\n");

        System.out.println("save all adjustable bike...");
        bikedao.dropTable("adjustablebike");
        for(Bike adjustablebike: adjustableBike){
            bikedao.saveBike(adjustablebike, "adjustablebike");
        }
        System.out.println("done\n");

        easycarddao.dropTable();
        System.out.println("save all easycard...");
        for (Easycard easycard : allEasycard) {
            easycarddao.saveEasycard(easycard);
        }
        System.out.println("done\n");

        customerdao.dropTable();
        System.out.println("save customer list...");
        for (Customer customer : customerList) {
            customerdao.saveCustomer(customer);
        }
        System.out.println("done\n");

        rentalRecordDAO.dropTable("easycard");
        System.out.println("save all rentalRecordForEasycard...");
        for (RentalRecord rentalRecordForEasycard : allRentalRecordForEasycard) {
            rentalRecordDAO.saveRentalRecordForEasycard(rentalRecordForEasycard);
        }
        System.out.println("done\n");

        rentalRecordDAO.dropTable("customer");
        System.out.println("save all rentalRecordForCustomer...");
        for (RentalRecord rentalRecordForCustomer : allRentalRecordForCustomer) {
            rentalRecordDAO.saveRentalRecordForCustomer(rentalRecordForCustomer);
        }
        System.out.println("done\n");

        System.out.println("save all Phone Number of System.Customer");
        phoneNumberdao.dropTable();
        for (String phonenumber : PhoneNumbeofCustomer) {
            phoneNumberdao.savePhoneNumber(phonenumber);
        }
        System.out.println("done\n");

        System.out.println("save all id Number of customer");
        idNumberdao.dropTable();
        for (String idNumber : idNumberofCustomer) {
            idNumberdao.saveIdNumber(idNumber);
        }
        System.out.println("done\n");

        System.out.println("=================");
        System.out.println("==save all data==");
        System.out.println("=================");
    }
}
