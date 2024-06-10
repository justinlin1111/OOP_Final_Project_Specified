import java.util.ArrayList;
import java.util.List;

public class Maintainer {

    // Constructor
    public Maintainer() {
    };

    /**
     * get the bike's state
     * 
     * @param a  the arraylist contains all the bikes
     * @param id target bike's id number
     * @return the state of the bike
     */
    public String idGetState(ArrayList<Bike> a, String id) {
        Bike bike = idgetbike(a, id);
        assert bike != null;
        return "車號 " + id + " 的車輛狀態為 " + bike.getState();
    }

    /**
     * set the bike's state
     * 
     * @param a     the arraylist contains all the bikes
     * @param id    target bike's id number
     * @param state the state you want to set to the bike
     */
    public void BikeidSetState(ArrayList<Bike> a, String id, String state) {
        Bike bike = idgetbike(a, id);
        assert bike != null;
        bike.setState(state);
        if (bike.getnowStation() != null) {
            bike.getnowStation().determineBikeAlloutOfService();
            bike.getnowStation().determineNullPillaralloutOfService();
        }
    }

    /**
     * 修復車柱
     * 
     * @param Station 車站
     * @param pillar  修好的車柱
     */
    public void repairbrokenpillar(String StationUID, int pillar, ArrayList<Station> allStation) {
        Station station = idgetStation(allStation, StationUID);
        if (station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (pillar < 1 || pillar > station.getBikesCapacity()) {
            System.out.println("輸入車柱數字有誤");
            return;
        }
        pillar = pillar - 1;
        if (station.getBrokenpillar().contains(pillar) == false) {
            System.out.println("此車柱無損壞");
            return;
        }
        station.repairbrokenpillar(pillar);
    }

    /**
     * get the arraylist that contains all the out of region bike
     * 
     * @param a the bike arraylist contains all the bike
     * @return arraylist that contains all the out of region bike
     */
    public ArrayList<Bike> getCrossRegion(ArrayList<Bike> a) {
        ArrayList<Bike> notSameRegion = new ArrayList<Bike>();
        for (Bike bike : a) {
            bike.determineIsCrossRegional();
            if (bike.getIsCrossRegional()) {
                notSameRegion.add(bike);
            }
        }
        return notSameRegion;
    }

    /**
     * replace a slot in station with a bike，並且更新站點資訊與車輛資訊
     * 不指定車子
     * 
     * @param station        the station you want to add
     * @param adjustableBike 所有可以調度的車子
     */
    public void addBike(String stationUID, ArrayList<Bike> adjustableBike, ArrayList<Station> allStation) {
        Station station = idgetStation(allStation, stationUID);
        if (station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (station.hasEmptySlots() == false) {
            System.out.println("此站點無車柱可新增 YouBike");
            return;
        }
        if (adjustableBike.size() == 0) {
            System.out.println("目前無可調度的 YouBike");
            return;
        }
        Bike addbike = adjustableBike.get(0);
        adjustableBike.remove(addbike);
        station.returnBike_plusNumber();
        int Index = -1;
        for (int i = 0; i < station.getBikesCapacity(); i++) {
            if (station.getBikeArray()[i] == null) {
                int action = 0;
                if (station.getBrokenpillar().contains(i))
                    action = 1;
                if (action == 0) {
                    Index = i;
                    break;
                }
            }
        }
        station.PushBikeByArray(addbike, Index);
        station.determineBikeAlloutOfService(); // 更新 Station 資訊
        station.determineNullPillaralloutOfService();
        addbike.setnowStation(station);
        addbike.setState("OK");
        addbike.determineIsCrossRegional();
        addbike.setPillar(Index);
    }

    /**
     * replace a slot in station with a bike
     * 跟上面不同的是這邊指定車子 但是指定 Bike ID
     * 
     * @param station        the station you want to add
     * @param adjustableBike 所有可以調度的 YouBike
     * @param pillar         要停的車柱
     * @param BikeID         BIke 的 ID
     */
    public void addBike(String stationUID, ArrayList<Bike> adjustableBike, int pillar, String BikeID,
            ArrayList<Station> allStation) {
        Station station = idgetStation(allStation, stationUID);
        Bike addbike = idgetbike(adjustableBike, BikeID);
        pillar = pillar - 1; // pillar 是從 1 開始，所以要 -1
        if (station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (addbike == null) {
            System.out.println("查無此YouBike");
            return;
        }
        if (adjustableBike.contains(addbike) == false) {
            System.out.println("此 YouBike 無法調度，請確認 YouBike type");
            return;
        }
        if (station.getBikeArray()[pillar] != null) {
            System.out.println("此車柱已有車");
            return;
        }
        if (station.getBrokenpillar().contains(pillar)) {
            System.out.println("此車柱目前正在維修中");
            return;
        }
        adjustableBike.remove(addbike);
        station.returnBike_plusNumber();
        station.PushBikeByArray(addbike, pillar);
        station.determineBikeAlloutOfService(); // 更新 Station 資訊
        station.determineNullPillaralloutOfService();
        addbike.setnowStation(station);
        addbike.setState("OK");
        addbike.determineIsCrossRegional();
        addbike.setPillar(pillar);
    }

    /**
     * replace a slot in station with a null
     * 但不指定 YouBike
     * 
     * @param station        the station you want to remove
     * @param adjustableBike 所有可以調度的車子 因為 bike 要加進去這裡所以需要
     */
    public void removeBike(String stationUID, ArrayList<Bike> adjustableBike, ArrayList<Station> allStation) {
        Station station = idgetStation(allStation, stationUID);
        if (station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (station.hasBikes() == false) {
            System.out.println("此站點無 YouBike 可調度");
            return;
        }
        Bike bike = null;
        for (int i = 0; i < station.getBikesCapacity(); i++) {
            if (station.getBikeArray()[i] != null && station.getBikeArray()[i].getState().equals("OK")) {
                if (station.getBrokenpillar().contains(i) != true) {
                    bike = station.getBikeArray()[i];
                    station.PopBikeByArray(i);
                    break;
                }
            }
        }
        adjustableBike.add(bike);
        station.rentBike_minusNumber(); // 更新 Station 資訊
        station.determineBikeAlloutOfService(); // 更新 Station 資訊
        station.determineNullPillaralloutOfService(); // 更新 Station 資訊
        bike.setState("outOfService");
        bike.setPillar(-1);
        bike.setnowStation(null);
    }

    /**
     * replace a slot in station with a null
     * 
     * @param station        the station you want to remove
     * @param bike           the bike you want to remove
     * @param adjustableBike 所有可以調度的車子 因為 bike 要加進去這裡所以需要
     */
    public void removeBike(String stationUID, String bikeID, ArrayList<Bike> adjustableBike,
            ArrayList<Station> allStation, ArrayList<Bike> allBike) {
        Station station = idgetStation(allStation, stationUID);
        Bike bike = idgetbike(allBike, bikeID);
        if (station == null) {
            System.out.println("查無此站點");
            return;
        }
        if (bike == null) {
            System.out.println("查無此YouBike");
            return;
        }
        if (bike.getnowStation() != station) {
            System.out.println("此YouBike 不在此站點");
            return;
        }
        if (bike.getState().equals("OK") == false) {
            System.out.println("此YouBike正在維修中");
            return;
        }
        for (int i = 0; i < station.getBikesCapacity(); i++) {
            if (bike == station.getBikeArray()[i]) {
                if (station.getBrokenpillar().contains(i)) {
                    System.out.println("此 YouBike 所在的車柱正在維修中");
                    return;
                }
                station.PopBikeByArray(i);
                break;
            }
        }
        adjustableBike.add(bike);
        station.rentBike_minusNumber(); // 更新 Station 資訊
        station.determineBikeAlloutOfService(); // 更新 Station 資訊
        station.determineNullPillaralloutOfService();
        bike.setState("outOfService");
        bike.setPillar(-1);
        bike.setnowStation(null);
    }

    /**
     * use id to get bike object
     * 
     * @param a  the arraylist contains all the bikes
     * @param id the target bike's id number
     * @return return the bike object havs the id number
     */
    public Bike idgetbike(ArrayList<Bike> a, String id) {
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
     * 用rentalRecordid找rentalRecordForEasycard，
     * 與forCustomer不同的是，因為顧客跟悠遊卡的交易紀律可以不同(包含關係)
     * 因此需要分別處理交易紀錄，這邊的id是指每一張卡片依照時間的交易順序
     * @param allRentalRecordForEasyCard
     * @param rentalRecordIdForEasycard
     * @return
     */
    public RentalRecord idGetRentalRecordForEasycard(List<RentalRecord> allRentalRecordForEasyCard, int rentalRecordIdForEasycard) {
        for (RentalRecord rentalRecordForEasyCard : allRentalRecordForEasyCard) {
            if (rentalRecordForEasyCard.getId() == rentalRecordIdForEasycard) {
                return rentalRecordForEasyCard;
            }
        }
        return null;
    }

    /**
     * 用rentalRecordid找rentalRecordForCustomer，
     * 參考idGetRentalRecordForEasycard
     * @param allRentalRecordForCustomer
     * @param rentalRecordIdForCustomer
     * @return
     */
    public RentalRecord idGetRentalRecordForCustomer(List<RentalRecord> allRentalRecordForCustomer, int rentalRecordIdForCustomer) {
        for (RentalRecord rentalRecordForCustomer : allRentalRecordForCustomer) {
            if (rentalRecordForCustomer.getId() == rentalRecordIdForCustomer) {
                return rentalRecordForCustomer;
            }
        }
        return null;
    }
}