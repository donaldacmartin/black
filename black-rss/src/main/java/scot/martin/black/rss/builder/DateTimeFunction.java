package scot.martin.black.rss.builder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

@Service
public class DateTimeFunction implements Function<LocalDateTime, String> {

    private static final String PATTERN = "EE, d MMM u HH:mm:ss Z";
    private static final Locale LOCALE = Locale.CANADA;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, LOCALE);
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();

    @Override
    public String apply(LocalDateTime localDateTime) {
        String formattedTimestamp = "";

        if (localDateTime != null) {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, TIMEZONE);
            formattedTimestamp = zonedDateTime.format(FORMATTER).replaceAll("\\.", "");
        }

        return formattedTimestamp;
    }

}
