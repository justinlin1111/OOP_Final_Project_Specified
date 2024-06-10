public class Bike {
    private String id;
    private String AuthorityID;
    private String type; // 加上車輛類型，(2.0/2.0E)
    private String state; // isRented, OK, outOfService, lost
    private String nowStationString;    // 為了從資料庫拿出及放入nowStation
    private Station nowStation;
    private int pillar;
    private boolean IsCrossRegional = false;

    // Constructor
    public Bike(String id, String AuthorityID, String type) {
        this.id = id;
        this.type = type;
        this.AuthorityID = AuthorityID;
        state = "outOfService";
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Station getnowStation() {
        return nowStation;
    }

    public String getState() {
        return state;
    }

    public String getAuthorityID() {
        return AuthorityID;
    }

    public Station getNowStation() {
        return nowStation;
    }

    public boolean isCrossRegional() {
        return IsCrossRegional;
    }

    public int getPillar() {
        return pillar;
    }

    public boolean getIsCrossRegional() {
        return IsCrossRegional;
    }


    public String getNowStationString() {
        return nowStationString;
    }

    public void setNowStationString(String nowStationString) {
        this.nowStationString = nowStationString;
    }

    public void setnowStation(Station nowStation) {
        this.nowStation = nowStation;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPillar(int pillar) {
        this.pillar = pillar;
    }

    public void setIsCrossRegional(Boolean IsCrossRegional){this.IsCrossRegional = IsCrossRegional;}
    // 判斷是否有跨區
    public void determineIsCrossRegional() {
        if (nowStation == null) {
            IsCrossRegional = false;
            return;
        }
        if (AuthorityID.equals(nowStation.getAuthorityID()))
            IsCrossRegional = false;
        else
            IsCrossRegional = true;
    }

    // return info
    public String infoforBike() {
        String s = "";
        s = s + "id : " + id + "\n";
        s = s + "AuthorityID : " + AuthorityID + "\n";
        s = s + "type : " + type + "\n";
        s = s + "state : " + state + "\n";
        if (nowStation != null) {
            s = s + "nowStation : " + nowStation.getStationName() + "\n";
            s = s + "pillar : " + String.valueOf(pillar + 1) + "\n";
            s = s + "IsCrossRegional : " + IsCrossRegional;
        }
        return s;
    }
}