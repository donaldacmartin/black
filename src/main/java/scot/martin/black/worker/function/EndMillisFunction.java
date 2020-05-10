package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scot.martin.black.entity.Broadcast;

import java.util.function.Function;

class EndMillisFunction implements Function<Broadcast, Long> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EndMillisFunction.class);

    @Override
    public Long apply(Broadcast broadcast) {
        Long endMillis = -1L;

        if (broadcast != null && broadcast.getLengthInMinutes() != null
                && broadcast.getLengthInMinutes() > 0) {
            long lengthMins = broadcast.getLengthInMinutes();
            long lengthMillis = lengthMins * 60 * 1000;
            endMillis = System.currentTimeMillis() + lengthMillis;
        } else {
            LOGGER.warn("Cannot calculate end time of {}", broadcast);
        }

        return endMillis;
    }
}
