package scot.martin.black.model.entity;

import org.hibernate.annotations.GenericGenerator;
import scot.martin.black.model.request.BroadcastRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
public class Broadcast {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    private String name;

    private LocalTime startTime;
    private ZoneId timezone;

    private Boolean isMonday;
    private Boolean isTuesday;
    private Boolean isWednesday;
    private Boolean isThursday;
    private Boolean isFriday;
    private Boolean isSaturday;
    private Boolean isSunday;

    private Long lengthInMinutes;

    @ManyToOne()
    @JoinColumn(name = "station_uuid")
    private Station station;

    protected Broadcast() {
    }

    public Broadcast(BroadcastRequest request, Station station) {
        this.name = request.getName();
        this.startTime = request.getStartTime();
        this.timezone = request.getTimezone();

        this.isMonday = request.getDayOfTheWeek().get(0).booleanValue();
        this.isTuesday = request.getDayOfTheWeek().get(1).booleanValue();
        this.isWednesday = request.getDayOfTheWeek().get(2).booleanValue();
        this.isThursday = request.getDayOfTheWeek().get(3).booleanValue();
        this.isFriday = request.getDayOfTheWeek().get(4).booleanValue();
        this.isSaturday = request.getDayOfTheWeek().get(5).booleanValue();
        this.isSunday = request.getDayOfTheWeek().get(6).booleanValue();

        this.lengthInMinutes = request.getLengthInMinutes();
        this.station = station;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public Boolean getMonday() {
        return isMonday;
    }

    public Boolean getTuesday() {
        return isTuesday;
    }

    public Boolean getWednesday() {
        return isWednesday;
    }

    public Boolean getThursday() {
        return isThursday;
    }

    public Boolean getFriday() {
        return isFriday;
    }

    public Boolean getSaturday() {
        return isSaturday;
    }

    public Boolean getSunday() {
        return isSunday;
    }

    public Long getLengthInMinutes() {
        return lengthInMinutes;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broadcast broadcast = (Broadcast) o;
        return Objects.equals(uuid, broadcast.uuid) &&
                Objects.equals(name, broadcast.name) &&
                Objects.equals(startTime, broadcast.startTime) &&
                Objects.equals(timezone, broadcast.timezone) &&
                Objects.equals(isMonday, broadcast.isMonday) &&
                Objects.equals(isTuesday, broadcast.isTuesday) &&
                Objects.equals(isWednesday, broadcast.isWednesday) &&
                Objects.equals(isThursday, broadcast.isThursday) &&
                Objects.equals(isFriday, broadcast.isFriday) &&
                Objects.equals(isSaturday, broadcast.isSaturday) &&
                Objects.equals(isSunday, broadcast.isSunday) &&
                Objects.equals(lengthInMinutes, broadcast.lengthInMinutes) &&
                Objects.equals(station, broadcast.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, startTime, timezone, isMonday,
                isTuesday, isWednesday, isThursday, isFriday, isSaturday,
                isSunday, lengthInMinutes, station);
    }

    @Override
    public String toString() {
        return "Show{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", timezone=" + timezone +
                ", isMonday=" + isMonday +
                ", isTuesday=" + isTuesday +
                ", isWednesday=" + isWednesday +
                ", isThursday=" + isThursday +
                ", isFriday=" + isFriday +
                ", isSaturday=" + isSaturday +
                ", isSunday=" + isSunday +
                ", lengthInMinutes=" + lengthInMinutes +
                ", station=" + station +
                '}';
    }

}
