package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;

class FileMoverBiConsumer implements BiConsumer<File, File> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FileMoverBiConsumer.class);

    @Override
    public void accept(File origin, File destDir) {
        if (origin != null && destDir != null) {
            try {
                Path originPath = origin.toPath();
                Path destDirPath = destDir.toPath();
                Path destPath = destDirPath.resolve(originPath.getFileName());

                Files.move(originPath, destPath);
            } catch (IOException io) {
                LOGGER.error("Error moving file: {}", io.getMessage());
            }
        } else {
            LOGGER.warn("Cannot move file {} to {}", origin, destDir);
        }
    }
}
