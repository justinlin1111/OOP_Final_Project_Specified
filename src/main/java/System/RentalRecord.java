package System;

import java.time.LocalDateTime;

/**
 * By KUO
 * list 租借紀錄 的 node
 */
public class RentalRecord {
    public int getId;
    private int id;
    private LocalDateTime Time;
    private Station Station;
    private Bike Bike;
    private String RentOrReturn;
    private int cost;

    RentalRecord(LocalDateTime Time, Station Station, Bike Bike, String RentOrReturn, int cost) {
        this.Time = Time;
        this.Station = Station;
        this.Bike = Bike;
        this.RentOrReturn = RentOrReturn;
        this.cost = cost;
    }

    /**
     * getter & setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return Time;
    }

    public Station getStation() {
        return Station;
    }

    public Bike getBike() {
        return Bike;
    }

    public String getRentOrReturn() {
        return RentOrReturn;
    }

    public int getCost() {
        return cost;
    }

    public void setStation(Station station) {
        Station = station;
    }

}