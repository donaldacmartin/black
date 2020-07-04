package scot.martin.black.worker.schedule;


import scot.martin.black.model.entity.Broadcast;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class DaysOfWeekTransformer implements Function<Broadcast, List<DayOfWeek>> {

    @Override
    public List<DayOfWeek> apply(Broadcast broadcast) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        List<Boolean> isOnDaysOfWeek = List.of(
                broadcast.getMonday(),
                broadcast.getTuesday(),
                broadcast.getWednesday(),
                broadcast.getThursday(),
                broadcast.getFriday(),
                broadcast.getSaturday(),
                broadcast.getSunday()
        );

        for (int i = 0; i < 7; i++) {
            if (isOnDaysOfWeek.get(i)) {
                daysOfWeek.add(DayOfWeek.of(i + 1));
            }
        }

        return daysOfWeek;
    }

}
