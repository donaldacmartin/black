package scot.martin.black.worker.function;

import org.apache.logging.log4j.util.TriConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

class IOStreamConsumer implements TriConsumer<InputStream, OutputStream, Long> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(IOStreamConsumer.class);

    private static final long MAX_BYTES = 512 * 1024 * 1024;

    private final Consumer<Closeable> closeableConsumer;

    public IOStreamConsumer() {
        this.closeableConsumer = new CloseableConsumer();
    }

    @Override
    public void accept(InputStream input, OutputStream output, Long endMillis) {
        try {
            long bytesRead = 0;

            while (endMillis > System.currentTimeMillis()
                    && bytesRead < MAX_BYTES) {
                output.write(input.read());
                bytesRead++;
            }
        } catch (IOException io) {
            LOGGER.error("Error streaming: {}", io.getMessage());
        } finally {
            closeableConsumer.accept(input);
            closeableConsumer.accept(output);
        }
    }
}
