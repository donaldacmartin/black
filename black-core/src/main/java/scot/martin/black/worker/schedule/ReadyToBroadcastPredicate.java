package scot.martin.black.worker.schedule;

import org.springframework.stereotype.Service;
import scot.martin.black.model.entity.Broadcast;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class ReadyToBroadcastPredicate implements Predicate<Broadcast> {

    private static final int THRESHOLD = 60000;

    private final Function<ZoneId, LocalDateTime> currentTimeFunction;
    private final Function<Broadcast, List<DayOfWeek>> daysOfWeekTransformer;

    public ReadyToBroadcastPredicate() {
        this.currentTimeFunction = t -> ZonedDateTime.now(t).toLocalDateTime();
        this.daysOfWeekTransformer = new DaysOfWeekTransformer();
    }

    @Override
    public boolean test(Broadcast broadcast) {
        LocalTime broadcastTime = broadcast.getStartTime();
        ZoneId timeZone = broadcast.getTimezone();
        LocalDateTime currentDateTime = currentTimeFunction.apply(timeZone);

        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime currentTime = currentDateTime.toLocalTime();

        List<DayOfWeek> broadcastDays = daysOfWeekTransformer.apply(broadcast);

        long timeToBroadcast;

        if (broadcastDays.contains(dayOfWeek)) {
            timeToBroadcast = currentTime.until(broadcastTime, ChronoUnit.MILLIS);
        } else {
            timeToBroadcast = Long.MAX_VALUE;
        }

        return timeToBroadcast > 0 && timeToBroadcast <= THRESHOLD;
    }
}
