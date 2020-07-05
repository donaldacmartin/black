package scot.martin.black.worker.schedule;

import org.springframework.stereotype.Service;
import scot.martin.black.model.entity.Broadcast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class ReadyToBroadcastPredicate implements Predicate<Broadcast> {

    private static final int THRESHOLD = 60000;

    private final Function<ZoneId, LocalDateTime> currentTimeFunction;
    private final Function<Broadcast, List<DayOfWeek>> daysOfWeekTransformer;

    private final BiPredicate<LocalDate, LocalDate> afterStartPredicate;
    private final BiPredicate<LocalDate, LocalDate> beforeEndPredicate;
    private final BiPredicate<Broadcast, LocalDate> inSeasonPredicate;

    public ReadyToBroadcastPredicate() {
        this.currentTimeFunction = t -> ZonedDateTime.now(t).toLocalDateTime();
        this.daysOfWeekTransformer = new DaysOfWeekTransformer();

        this.afterStartPredicate = (d, t) -> d == null || t.isEqual(d) || t.isAfter(d);
        this.beforeEndPredicate = (d, t) -> d == null || t.isEqual(d) || t.isBefore(d);

        this.inSeasonPredicate = (b, t) -> afterStartPredicate.test(b.getStartDate(), t)
                && beforeEndPredicate.test(b.getEndDate(), t);
    }

    @Override
    public boolean test(Broadcast broadcast) {
        LocalTime broadcastTime = broadcast.getStartTime();
        ZoneId timeZone = broadcast.getTimezone();
        LocalDateTime currentDateTime = currentTimeFunction.apply(timeZone);

        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalDate currentDate = currentDateTime.toLocalDate();

        List<DayOfWeek> broadcastDays = daysOfWeekTransformer.apply(broadcast);

        long timeToBroadcast;

        if (broadcastDays.contains(dayOfWeek)) {
            timeToBroadcast = currentTime.until(broadcastTime, ChronoUnit.MILLIS);
        } else {
            timeToBroadcast = Long.MAX_VALUE;
        }

        return inSeasonPredicate.test(broadcast, currentDate)
                && timeToBroadcast > 0 && timeToBroadcast <= THRESHOLD;
    }
}
