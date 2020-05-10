package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scot.martin.black.entity.Broadcast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Function;

class InputStreamFunction implements Function<Broadcast, Optional<InputStream>> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(InputStreamFunction.class);

    @Override
    public Optional<InputStream> apply(Broadcast broadcast) {
        Optional<InputStream> stream = Optional.empty();

        if (broadcast != null && broadcast.getStation() != null
                && broadcast.getStation().getUrl() != null) {

            try {
                InputStream urlStream = broadcast
                        .getStation()
                        .getUrl()
                        .openStream();

                stream = Optional.of(new BufferedInputStream(urlStream));
            } catch (IOException io) {
                LOGGER.error("Error creating stream: {}", io.getMessage());
            }

        } else {
            LOGGER.warn("Cannot stream with missing info: {}", broadcast);
        }

        return stream;
    }
}
