package se.iths;

import java.util.*;

public class User 
{
    int weight;
    int height;
    int totalDistanceRun;
    double averageDistanceRun;

    DatabaseAPI data;
    
    public User(int weight, int height) //weight in 'Kg', Height in 'cm'
    {
        this.weight = weight;
        this.height = height;
    }
    
    public User(int weight, int height, DatabaseAPI databaseAPI) //weight in 'Kg', Height in 'cm'
    {
        this(weight, height);
        this.data = databaseAPI;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double calculateBMI() //
    {
        double BMI = (double) weight / (height * height);
        return BMI * 10000;
    }

    public int totalDistance() {
        for (String id : data.getRecordIDs()) {
            Run run = data.readRecord(id);
            totalDistanceRun = totalDistanceRun + run.distance_km;
        }
        return totalDistanceRun;
    }

    public double averageDistance() {
        List<String> runIDs = data.getRecordIDs();
        for (String id : runIDs) {
            Run run = data.readRecord(id);
            averageDistanceRun = averageDistanceRun + run.distance_km;
        }
        return (double) averageDistanceRun / runIDs.size();
    }

    public String detailsOfRun(String id) {
        List<String> runIDs = data.getRecordIDs();
        String runDetails = "";
        for (String runId : runIDs) {
            Run run = data.readRecord(runId);
            if (runId == id) {
                runDetails = "This run had a distance of: " + run.getDistance_km() + "km. And a time of: " + run.timeToString() + " and occured at: " + run.getDate();
            }
        }
        return runDetails;
    }

    public boolean removeRun(String id) {
       
        for (String runID : data.getRecordIDs()) {
            if (runID == id) {
                return data.deleteRecord(runID);
            }
        }
        return false;
    }

    public List<Run> filterByDistance(int lower, int higher) {
        List<String> runIDs = data.getRecordIDs();
        List<Run> runList = new ArrayList<>();
        for (String runId : runIDs) {
            Run run = data.readRecord(runId);
            if (run.getDistance_km() >= lower && run.getDistance_km() <= higher) {
                runList.add(run);
            }
        }
        return runList;
    }
}
