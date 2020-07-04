package scot.martin.black.worker.function;

import org.apache.logging.log4j.util.TriConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scot.martin.black.model.entity.Broadcast;
import scot.martin.black.model.entity.SavedEpisode;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

class StreamingBiFunction
        implements BiFunction<Broadcast, File, Optional<SavedEpisode>> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StreamingBiFunction.class);

    private final Function<Broadcast, Optional<File>> tmpFileFunction;
    private final Function<Broadcast, Long> endMillisFunction;
    private final Function<File, Optional<OutputStream>> outputStreamFunction;
    private final Function<Broadcast, Optional<InputStream>> inputStreamFunction;
    private final TriConsumer<InputStream, OutputStream, Long> ioStreamConsumer;
    private final Consumer<Closeable> closeableConsumer;
    private final Function<File, Long> fileSizeFunction;
    private final BinaryOperator<File> fileMoverBinaryOperator;

    public StreamingBiFunction() {
        this.tmpFileFunction = new TmpFileFunction();
        this.endMillisFunction = new EndMillisFunction();
        this.outputStreamFunction = new OutputStreamFunction();
        this.inputStreamFunction = new InputStreamFunction();
        this.ioStreamConsumer = new IOStreamConsumer();
        this.closeableConsumer = new CloseableConsumer();
        this.fileSizeFunction = new FileSizeFunction();
        this.fileMoverBinaryOperator = new FileMoverBinaryOperator();
    }

    @Override
    public Optional<SavedEpisode> apply(Broadcast broadcast, File destDirectory) {
        Optional<SavedEpisode> savedEpisode = Optional.empty();
        Optional<File> tmpFile = tmpFileFunction.apply(broadcast);

        if (tmpFile.isPresent()) {
            Optional<OutputStream> os = outputStreamFunction.apply(tmpFile.get());

            if (os.isPresent()) {
                Optional<InputStream> is = inputStreamFunction.apply(broadcast);

                if (is.isPresent()) {
                    LOGGER.info("Beginning stream for {}", broadcast.getName());

                    long endMillis = endMillisFunction.apply(broadcast);
                    LocalDateTime startTime = LocalDateTime.now();

                    ioStreamConsumer.accept(is.get(), os.get(), endMillis);
                    Long fileSize = fileSizeFunction.apply(tmpFile.get());

                    File movedFile = fileMoverBinaryOperator.apply(tmpFile.get(), destDirectory);
                    savedEpisode = Optional.of(new SavedEpisode(startTime, movedFile, fileSize, broadcast));

                    LOGGER.info("Stream ended for {}", broadcast.getName());
                } else {
                    LOGGER.warn("No input to stream");
                    closeableConsumer.accept(os.get());
                }
            } else {
                LOGGER.warn("Nowhere to stream output");
            }
        } else {
            LOGGER.warn("Couldn't create tmp file");
        }

        return savedEpisode;
    }
}
