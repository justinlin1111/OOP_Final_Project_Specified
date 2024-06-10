import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Easycard {
    private String easyCardNumber;
    private int balance;
    private Customer Customer;
    private String Customerid;
    private List<RentalRecord> rentalRecords; // 租借紀錄

    // Constructor
    public Easycard(String easyCardNumber) {
        this.easyCardNumber = easyCardNumber;
        balance = 0;
        Customer = null;
        this.rentalRecords = new ArrayList<>();
    }

    // Getter和Setter方法
    public Customer getCustomer() {
        return Customer;
    }

    public String getNumber() {
        return new String(easyCardNumber);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setRentalRecords(List<RentalRecord> rentalRecords) {
        this.rentalRecords = rentalRecords;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    public String getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(String customerid) {
        Customerid = customerid;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }

    // 註冊
    public void registerCustomer(Customer Customer) {
        this.Customer = Customer;
    }

    // 註銷
    public void removeCustomer() {
        if (this.Customer == null) {
            System.out.println("您尚未註冊");
        } else {
            System.out.println("已註銷記名");
            this.Customer = null;
        }
    }

    // 紀錄租借紀錄
    public void addrentalRecords(RentalRecord rentalRecord) {
        rentalRecords.add(rentalRecord);
    }

    public void addRentalRecords(RentalRecord rentalRecord) {
        rentalRecords.add(rentalRecord);
    }

    // 加值
    public void deposit(int amount) {
        this.balance += amount;
    }

    // 消費
    public void PayForRent(int cost) {
        balance = balance - cost;
    }

    /**
     * By KUO
     * 這個 function 可以查詢租借紀錄
     */
    public String Recordquery() {
        String s = "";
        s = s + "EasyCard : " + getNumber() + "\n";
        if (rentalRecords == null) {
            s = s + "無紀錄";
            return s;
        }
        int size = rentalRecords.size();
        s = s + "序列           時間         借/還      車子   金額   車站" + "\n";
        s = s + "----------------------------------------------------------------" + "\n";
        for (int i = 0; i < size; i++) {
            LocalDateTime Time = rentalRecords.get(i).getTime(); // 把資料抓下來
            Station Station = rentalRecords.get(i).getStation(); // 把資料抓下來
            Bike Bike = rentalRecords.get(i).getBike(); // 把資料抓下來
            String RentOrReturn = rentalRecords.get(i).getRentOrReturn(); // 把資料抓下來
            int cost = rentalRecords.get(i).getCost(); // 把資料抓下來

            // 把時間格式化並轉成字串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = Time.format(formatter);

            // 把車站和車子的 Name, ID 抓下來
            String stationName = Station.getStationName();
            String BikeUID = Bike.getId();

            // 序列 - 時間 - 借/還 - 車子 - 金額 - 車站
            if (i + 1 < 10)
                s = s + "  " + String.valueOf(i + 1) + "   ";
            else if (i + 1 < 100)
                s = s + " " + String.valueOf(i + 1) + "   ";
            else
                s = s + String.valueOf(i + 1) + "   ";
            for (int a = 0; i < (19 - formattedDateTime.length()); a++)
                s = s + " ";
            s = s + formattedDateTime + "   ";
            if (RentOrReturn.length() == 4)
                s = s + "  " + RentOrReturn + "   ";
            else
                s = s + RentOrReturn + "   ";
            s = s + BikeUID + "   ";
            if (cost < 10)
                s = s + "   " + String.valueOf(cost) + "   ";
            else if (cost < 100)
                s = s + "  " + String.valueOf(cost) + "   ";
            else if (cost < 1000)
                s = s + " " + String.valueOf(cost) + "   ";
            else
                s = s + String.valueOf(cost) + "   ";
            s = s + stationName + "\n";
        }
        return s;
    }
}