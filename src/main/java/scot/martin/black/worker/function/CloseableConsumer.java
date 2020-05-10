package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

class CloseableConsumer implements Consumer<Closeable> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CloseableConsumer.class);

    @Override
    public void accept(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException io) {
                LOGGER.warn("Error closing {}: {}", closeable, io.getMessage());
            }
        }
    }
}
