package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.function.Function;

class FileSizeFunction implements Function<File, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSizeFunction.class);

    @Override
    public Long apply(File file) {
        Long lengthBytes = null;

        if (file != null && file.exists() && file.isFile()) {
            lengthBytes = file.length();
        } else {
            LOGGER.warn("Cannot get file size of file {}", file);
        }

        return lengthBytes;
    }
}
