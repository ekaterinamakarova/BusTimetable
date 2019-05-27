package sample.models;

import java.util.ArrayList;

public class BusStop {

    private String name;
    private ArrayList<Double> workdayTimetable = new ArrayList<Double>();
    private ArrayList<Double> weekendTimetable = new ArrayList<Double>();

    public BusStop() {
    }

    public BusStop(String name) {
        this.name = name;
    }

    public ArrayList<Double> getWeekendTimetable() {
        return weekendTimetable;
    }

    public void setWeekendTimetable(ArrayList<Double> weekendTimetable) {
        this.weekendTimetable = weekendTimetable;
    }

    public ArrayList<Double> getWorkdayTimetable() {
        return workdayTimetable;
    }

    public void setWorkdayTimetable(ArrayList<Double> workdayTimetable) {
        this.workdayTimetable = workdayTimetable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}