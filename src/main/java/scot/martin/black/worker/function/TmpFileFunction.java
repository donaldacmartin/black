package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import scot.martin.black.entity.Broadcast;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

class TmpFileFunction implements Function<Broadcast, Optional<File>> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TmpFileFunction.class);

    private static final String SUFFIX = ".mp3";

    @Override
    public Optional<File> apply(Broadcast broadcast) {
        Optional<File> tmpFile = Optional.empty();

        if (broadcast != null && !StringUtils.isEmpty(broadcast.getName())) {
            try {
                String prefix = broadcast.getName();
                tmpFile = Optional.of(File.createTempFile(prefix, SUFFIX));
            } catch (IOException io) {
                LOGGER.error("Error creating tmp file: {}", io.getMessage());
            }
        } else {
            LOGGER.warn("Cannot create tmp file without broadcast name");
        }

        return tmpFile;
    }
}
