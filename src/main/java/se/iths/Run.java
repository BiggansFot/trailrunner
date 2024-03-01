package se.iths;

import java.time.Duration;
import java.time.LocalDate;

public class Run
{
    int distance_km;
    Duration time;
    String id;
    LocalDate date = LocalDate.now();

    public Run(String id, int distance_km, int time_seconds) {
        this.id =id;
        this.distance_km = distance_km;
        time = Duration.ofSeconds(time_seconds);
    }

    public Run(String id, int distance_km, int time_seconds, LocalDate date)
    {
        this.id = id;
        this.distance_km = distance_km;
        time = Duration.ofSeconds(time_seconds);
        this.date = date;
    }

    public double calculateAverageSpeed()
    {
        double speed = (double) distance_km / (time.toSeconds());
        return speed * 3600;
    }

    public double averageKilometerTime() {
        double averageKilometerTime = (double) (time.toMinutes()) / distance_km;  
        return averageKilometerTime;
    }

    public String getID() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Duration getTime() {
        return time;
    }

    public int getDistance_km() {
        return distance_km;
    }

    public String timeToString() {
        return time.toHours() + ":" + time.toMinutesPart() + ":" + time.toSecondsPart();
    }

    @Override
    public String toString() {
        return id;
    }
}