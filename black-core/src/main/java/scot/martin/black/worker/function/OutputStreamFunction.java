package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.function.Function;

class OutputStreamFunction implements Function<File, Optional<OutputStream>> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OutputStreamFunction.class);

    @Override
    public Optional<OutputStream> apply(File file) {
        Optional<OutputStream> stream = Optional.empty();

        if (file != null && file.exists() && file.isFile()) {
            try {
                stream = Optional.of(new FileOutputStream(file));
            } catch (IOException io) {
                LOGGER.error("Error creating stream: {}", io.getMessage());
            }
        } else {
            LOGGER.warn("Cannot create stream without valid file: {}", file);
        }

        return stream;
    }
}
