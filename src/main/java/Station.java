import java.util.ArrayList;

public class Station {
    private String StationUID;
    private String StationID;
    private String AuthorityID;
    private String stationName;
    private String stationnameEN;
    private double PositionLon;
    private double PositionLat;
    private String GeoHash;
    private String address;
    private String addressEN;
    private int BikesCapacity;
    private int BikesNumber; // 在這個站的腳踏車數量
    private Bike[] BikeArray; // 在這個站的腳踏車 陣列大小為 BikesCapacity
    private boolean BikealloutOfService; // 是否全部的車都為 outOfService
    private boolean nullPillaralloutOfService;
    private ArrayList<Integer> brokenpillar; // 空的 element 為 -1

    // Constructor
    public Station(String StationUID, String StationID, String AuthorityID, String stationName, String stationnameEN,
            double PositionLon, double PositionLat, String GeoHash, String address, String addressEN,
            int BikesCapacity) {
        this.StationUID = StationUID;
        this.StationID = StationID;
        this.AuthorityID = AuthorityID;
        this.stationName = stationName;
        this.stationnameEN = stationnameEN;
        this.PositionLon = PositionLon;
        this.PositionLat = PositionLat;
        this.GeoHash = GeoHash;
        this.address = address;
        this.addressEN = addressEN;
        this.BikesCapacity = BikesCapacity;
        this.BikeArray = new Bike[BikesCapacity];
        this.brokenpillar = new ArrayList<Integer>();
        this.BikesNumber = 0;
    }

    // Getter和Setter方法
    public String getStationUID() {
        return StationUID;
    }

    public String getStationID() {
        return StationID;
    }

    public String getStationName() {
        return new String(stationName);
    }

    public void setBikesNumber(int bikesNumber) {
        BikesNumber = bikesNumber;
    }

    public void setBikeArray(Bike[] bikeArray) {
        BikeArray = bikeArray;
    }

    public String getStationnameEN() {
        return stationnameEN;
    }

    public double getPositionLon() {
        return PositionLon;
    }

    public double getPositionLat() {
        return PositionLat;
    }

    public String getGeoHash() {
        return GeoHash;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressEN() {
        return addressEN;
    }

    public boolean isBikealloutOfService() {
        return BikealloutOfService;
    }

    public boolean isNullPillaralloutOfService() {
        return nullPillaralloutOfService;
    }

    public void setBikealloutOfService(boolean bikealloutOfService) {
        BikealloutOfService = bikealloutOfService;
    }

    public void setNullPillaralloutOfService(boolean nullPillaralloutOfService) {
        this.nullPillaralloutOfService = nullPillaralloutOfService;
    }

    public void setBrokenpillar(ArrayList<Integer> brokenpillar) {
        this.brokenpillar = brokenpillar;
    }

    public int getBikesNumber() {
        return BikesNumber;
    }

    public int getBikesCapacity() {
        return BikesCapacity;
    }

    public Bike[] getBikeArray() {
        return BikeArray;
    }

    public String getAuthorityID() {
        return new String(AuthorityID);
    }

    public ArrayList<Integer> getBrokenpillar() {
        return brokenpillar;
    }

    public boolean getnullPillaralloutOfService() {
        return nullPillaralloutOfService;
    }

    public boolean getBikeAlloutOfService() {
        return BikealloutOfService;
    }

    /**
     * By KUO
     * 這個 function 用來判斷停在 Station 的 Bike 是否全部都為 outOfService
     */
    public void determineBikeAlloutOfService() {
        for (int i = 0; i < BikesCapacity; i++) {
            if (BikeArray[i] == null)
                continue;
            if (BikeArray[i].getState().equals("outOfService"))
                continue;
            if (brokenpillar.contains(i))
                continue;
            else {
                BikealloutOfService = false;
                return;
            }
        }
        BikealloutOfService = true;
        return;
    }

    /**
     * By KUO
     * 這個 function 用來判斷在 Station 的 車位 是否全部都為 outOfService
     */
    public void determineNullPillaralloutOfService() {
        for (int i = 0; i < BikesCapacity; i++) {
            if (BikeArray[i] == null) {
                int action = 0;
                if (brokenpillar.contains(i))
                    action = 1;
                if (action == 0) {
                    nullPillaralloutOfService = false;
                    return;
                }
            }
        }
        nullPillaralloutOfService = true;
    }

    // 增加壞掉的車柱
    public void pillarbroken(int num) {
        if (getBrokenpillar().contains(num))
            return;
        brokenpillar.add(num);
    }

    // 修壞掉的車柱
    public void repairbrokenpillar(int num) {
        int action = 0;
        for (int i : brokenpillar) {
            if (i == num)
                break;
            action = action + 1;
        }
        brokenpillar.remove(action);
        determineBikeAlloutOfService();
        determineNullPillaralloutOfService();
    }

    // 減少 YouBike 數量
    public void rentBike_minusNumber() {
        BikesNumber = BikesNumber - 1;
    }

    // 增加 YouBike 數量
    public void returnBike_plusNumber() {
        BikesNumber = BikesNumber + 1;
    }

    // 還車或是addbike
    public void PushBikeByArray(Bike Bike, int number) {
        BikeArray[number] = Bike;
    }

    // 借車或是removebike
    public void PopBikeByArray(int number) {
        BikeArray[number] = null;
    }

    public int min(int a, int b) {
        if (a >= b)
            return b;
        else
            return a;
    }

    // return 車柱的資訊
    public String getBikeArrayinfo() {
        String s = "";
        s = s + getStationName() + "\n";
        s = s + "車柱 : ";
        for (int i = 0; i < min(BikesCapacity, 20); i++) {
            if (i != 0)
                s = s + " ";
            if (i <= 8)
                s = s + " ";
            s = s + String.valueOf(i + 1);
        }
        s = s + "\n";
        s = s + "車子 : ";
        for (int i = 0; i < min(BikesCapacity, 20); i++) {
            if (i != 0)
                s = s + " ";
            s = s + " ";
            if (BikeArray[i] == null)
                s = s + "X";
            else
                s = s + "O";
        }
        s = s + "\n";
        s = s + "報修 : ";
        for (int i = 0; i < min(BikesCapacity, 20); i++) {
            if (i != 0)
                s = s + " ";
            s = s + " ";
            if (brokenpillar.contains(i)) {
                s = s + "*";
            } else if (BikeArray[i] != null) {
                if (BikeArray[i].getState().equals("OK") == false)
                    s = s + "*";
                else
                    s = s + " ";
            } else
                s = s + " ";
        }
        if (BikesCapacity > 20) {
            s = s + "\n";
            s = s + "車柱 : ";
            for (int i = 20; i < min(BikesCapacity, 40); i++) {
                if (i != 20)
                    s = s + " ";
                if (i <= 8)
                    s = s + " ";
                s = s + String.valueOf(i + 1);
            }
            s = s + "\n";
            s = s + "車子 : ";
            for (int i = 20; i < min(BikesCapacity, 40); i++) {
                if (i != 20)
                    s = s + " ";
                s = s + " ";
                if (BikeArray[i] == null)
                    s = s + "X";
                else
                    s = s + "O";
            }
            s = s + "\n";
            s = s + "報修 : ";
            for (int i = 20; i < min(BikesCapacity, 40); i++) {
                if (i != 20)
                    s = s + " ";
                s = s + " ";
                if (brokenpillar.contains(i)) {
                    s = s + "*";
                } else if (BikeArray[i] != null) {
                    if (BikeArray[i].getState().equals("OK") == false)
                        s = s + "*";
                } else
                    s = s + " ";
            }
        }
        if (BikesCapacity > 40) {
            s = s + "\n";
            s = s + "車柱 : ";
            for (int i = 40; i < min(BikesCapacity, 60); i++) {
                if (i != 40)
                    s = s + " ";
                if (i <= 8)
                    s = s + " ";
                s = s + String.valueOf(i + 1);
            }
            s = s + "\n";
            s = s + "車子 : ";
            for (int i = 40; i < min(BikesCapacity, 60); i++) {
                if (i != 40)
                    s = s + " ";
                s = s + " ";
                if (BikeArray[i] == null)
                    s = s + "X";
                else
                    s = s + "O";
            }
            s = s + "\n";
            s = s + "報修 : ";
            for (int i = 40; i < min(BikesCapacity, 60); i++) {
                if (i != 40)
                    s = s + " ";
                s = s + " ";
                if (brokenpillar.contains(i)) {
                    s = s + "*";
                } else if (BikeArray[i] != null) {
                    if (BikeArray[i].getState().equals("OK") == false)
                        s = s + "*";
                } else
                    s = s + " ";
            }
        }
        if (BikesCapacity > 60) {
            s = s + "\n";
            s = s + "車柱 : ";
            for (int i = 60; i < min(BikesCapacity, 80); i++) {
                if (i != 60)
                    s = s + " ";
                if (i <= 8)
                    s = s + " ";
                s = s + String.valueOf(i + 1);
            }
            s = s + "\n";
            s = s + "車子 : ";
            for (int i = 60; i < min(BikesCapacity, 80); i++) {
                if (i != 60)
                    s = s + " ";
                s = s + " ";
                if (BikeArray[i] == null)
                    s = s + "X";
                else
                    s = s + "O";
            }
            s = s + "\n";
            s = s + "報修 : ";
            for (int i = 60; i < min(BikesCapacity, 80); i++) {
                if (i != 60)
                    s = s + " ";
                s = s + " ";
                if (brokenpillar.contains(i)) {
                    s = s + "*";
                } else if (BikeArray[i] != null) {
                    if (BikeArray[i].getState().equals("OK") == false)
                        s = s + "*";
                } else
                    s = s + " ";
            }
        }
        return s;
    }

    // return 可用車位數量
    private int getAvailableSlots() {
        int num = 0;
        for (int i = 0; i < BikesCapacity; i++) {
            if (BikeArray[i] == null) {
                if (brokenpillar.contains(i) == false)
                    num = num + 1;
            }
        }
        return num;
    }

    // return 可用Youbike數量
    private int getAvailableBike() {
        int num = 0;
        for (int i = 0; i < BikesCapacity; i++) {
            if (BikeArray[i] != null) {
                if (brokenpillar.contains(i) == false) {
                    if (BikeArray[i].getState().equals("OK"))
                        num = num + 1;
                }
            }
        }
        return num;
    }

    // 查詢是否有可用 YouBike
    public boolean hasBikes() {
        if (getAvailableBike() == 0)
            return false;
        else
            return true;
    }

    // 查詢是否有可用車位
    public boolean hasEmptySlots() {
        if (getAvailableSlots() == 0)
            return false;
        else
            return true;
    }

    // return 車站腳踏車資訊
    private String getBikesInfo() {
        StringBuilder bikesInfo = new StringBuilder();
        bikesInfo.append(BikesNumber).append("/").append(BikesCapacity);
        return bikesInfo.toString();
    }

    // 查詢車站資訊
    public String getStationInfo() {
        if (this.stationName == null) {
            return "the station is null";
        }
        StringBuilder info = new StringBuilder();
        info.append("站點名稱 : ").append(stationName).append("\n");
        info.append("位置 : 經度 ").append(PositionLon).append(", 緯度 ").append(PositionLat).append("\n");
        info.append("Youbike : ").append(getBikesInfo()).append("\n");
        info.append("可用車輛 : ").append(getAvailableBike()).append("\n");
        info.append("可用車位 : ").append(getAvailableSlots()).append("\n");
        info.append(getBikeArrayinfo());
        return info.toString();
    }


}