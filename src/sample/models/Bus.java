package sample.models;

import java.util.ArrayList;

public class Bus {

    private String name;
    private String from;
    private String destination;
    private ArrayList<BusStop> busStops = new ArrayList<BusStop>();

    public Bus() {
    }

    public Bus(String name, String from, String destination) {
        this.setName(name);
        this.from = from;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(ArrayList<BusStop> busStops) {
        this.busStops = busStops;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}