import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer {
    private String phoneNumber; // 手機號碼
    private String password; // 密碼
    private String idNumber;// 身分證字號
    private String email; // email
    private List<Easycard> Easycard; // 悠遊卡，做多四張
    private List<RentalRecord> rentalRecords; // 租借紀錄
    private Bike rentedBike = null; // 借的車子
    private LocalDateTime rentalTime = null; // 租賃時間
    private boolean isRenting = false; // 是否正在租車
    private String rentLocation = null; // 借車的地方
    private Easycard usingEasycard = null; // 正在借車的 Easycard

    // Constructor
    public Customer(String phoneNumber, String password, String idNumber, String email, Easycard Easycard) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.idNumber = idNumber;
        this.email = email;
        this.Easycard = new ArrayList<>();
        this.Easycard.add(Easycard);
        this.rentalRecords = new ArrayList<>();
    }

    // Getter和Setter方法
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public List<Easycard> getEasycard() {
        return Easycard;
    }

    public boolean getisRenting() {
        return isRenting;
    }

    public LocalDateTime getrentalTime() {
        return rentalTime;
    }

    public String getrentLocation() {
        return rentLocation;
    }

    public Bike getrentedBike() {
        return rentedBike;
    }

    public Easycard getusingEasycard() {
        return usingEasycard;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }

    public void setEasycard(List<Easycard> easycard) {
        Easycard = easycard;
    }

    public void setRentalRecords(List<RentalRecord> rentalRecords) {
        this.rentalRecords = rentalRecords;
    }

    public void setusingEasycard(Easycard usingEasycard) {
        this.usingEasycard = usingEasycard;
    }

    public void setisRenting(boolean b) {
        isRenting = b;
    }

    public void setrentedBike(Bike Bike) {
        rentedBike = Bike;
    }

    public void setrentalTime(LocalDateTime rentalTime) {
        this.rentalTime = rentalTime;
    }

    public void setrentLocation(String rentLocation) {
        this.rentLocation = rentLocation;
    }

    // 新增悠遊卡
    public void addEasycard(Easycard Easycard) {
        this.Easycard.add(Easycard);
    }

    // 新增租借紀錄
    public void addrentalRecords(RentalRecord rentalRecord) {
        rentalRecords.add(rentalRecord);
    }

    public void setRentedBike(Bike rentedBike) {
        this.rentedBike = rentedBike;
    }

    public void setRenting(boolean renting) {
        isRenting = renting;
    }

    public void setRentLocation(String rentLocation) {
        this.rentLocation = rentLocation;
    }

    public void setUsingEasycard(Easycard usingEasycard) {
        this.usingEasycard = usingEasycard;
    }

    /**
     * By KUO
     * 這個 function 可以查詢租借紀錄
     */
    public String Recordquery() {
        String s = "";
        int size = rentalRecords.size();
        s = s + "Customer : " + getIdNumber() + "\n";
        s = s + "序列           時間         借/還      車子   金額   車站" + "\n";
        s = s + "----------------------------------------------------------------" + "\n";
        for (int i = 0; i < size; i++) {
            LocalDateTime Time = rentalRecords.get(i).getTime(); // 把資料抓下來
            Station Station = rentalRecords.get(i).getStation(); // 把資料抓下來
            Bike Bike = rentalRecords.get(i).getBike(); // 把資料抓下來
            String RentOrReturn = rentalRecords.get(i).getRentOrReturn(); // 把資料抓下來
            int cost = rentalRecords.get(i).getCost(); // 把資料抓下來

            System.out.println(rentalRecords.get(i).getStation());
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

    // return info
    public String infotoString() {
        String formattedDateTime;
        if (rentalTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedDateTime = rentalTime.format(formatter);
        } else
            formattedDateTime = null;
        String s = "";
        s = s + "phoneNumber : " + phoneNumber + "\n";
        s = s + "password : " + password + "\n";
        s = s + "idNumber : " + idNumber + "\n";
        s = s + "email : " + email + "\n";
        for (int i = 0; i < Easycard.size(); i++) {
            if (i == 0)
                s = s + "Easycard : " + Easycard.get(i).getNumber() + "  餘額 : " + Easycard.get(i).getBalance() + "\n";
            else
                s = s + "           " + Easycard.get(i).getNumber() + "  餘額 : " + Easycard.get(i).getBalance() + "\n";
        }
        if (rentedBike != null)
            s = s + "rentedBike : " + rentedBike.getId() + "\n";
        else
            s = s + "rentedBike : " + "null" + "\n";
        s = s + "rentalTime : " + formattedDateTime + "\n";
        s = s + "isRenting : " + isRenting + "\n";
        s = s + "rentLocation : " + rentLocation;
        return s;
    }
}