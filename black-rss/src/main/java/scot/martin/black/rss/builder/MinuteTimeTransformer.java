package scot.martin.black.rss.builder;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.function.LongFunction;

@Service
public class MinuteTimeTransformer implements LongFunction<String> {

    private static final LocalTime ZERO_LENGTH = LocalTime.of(0, 0);

    @Override
    public String apply(long minutes) {
        return ZERO_LENGTH.plusMinutes(minutes).toString() + ":00";
    }

}
