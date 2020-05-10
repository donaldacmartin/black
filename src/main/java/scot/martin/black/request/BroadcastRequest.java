package scot.martin.black.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class BroadcastRequest {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "([A-z0-9 '.-]+)")
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private ZoneId timezone;

    @NotNull
    @Size(min = 7, max = 7)
    private List<Boolean> dayOfTheWeek;

    @NotNull
    @Min(1)
    @Max(180)
    private Long lengthInMinutes;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "([A-z0-9 '.-]+)")
    private String stationUuid;

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public List<Boolean> getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public Long getLengthInMinutes() {
        return lengthInMinutes;
    }

    public String getStationUuid() {
        return stationUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BroadcastRequest that = (BroadcastRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(timezone, that.timezone) &&
                Objects.equals(dayOfTheWeek, that.dayOfTheWeek) &&
                Objects.equals(lengthInMinutes, that.lengthInMinutes) &&
                Objects.equals(stationUuid, that.stationUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, timezone, dayOfTheWeek,
                lengthInMinutes, stationUuid);
    }

    @Override
    public String toString() {
        return "BroadcastRequest{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", timezone=" + timezone +
                ", dayOfTheWeek=" + dayOfTheWeek +
                ", lengthInMinutes=" + lengthInMinutes +
                ", stationUuid='" + stationUuid + '\'' +
                '}';
    }
    
}
