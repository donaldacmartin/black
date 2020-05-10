package scot.martin.black.worker.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.entity.Broadcast;
import scot.martin.black.entity.SavedEpisode;
import scot.martin.black.repository.SavedEpisodeRepository;

import java.io.File;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class RecorderRunnableFunction implements Function<Broadcast, Runnable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecorderRunnableFunction.class);

    @Value("${file.directory}")
    private String destinationDirectory;

    @Autowired
    private SavedEpisodeRepository savedEpisodeRepository;

    @Override
    public Runnable apply(Broadcast broadcast) {
        BiFunction<Broadcast, File, Optional<SavedEpisode>> streamingBiFunction = new StreamingBiFunction();
        File destDir = new File(destinationDirectory);

        return () -> {
            Optional<SavedEpisode> savedEpisode = streamingBiFunction.apply(broadcast, destDir);

            if (savedEpisode.isPresent()) {
                LOGGER.info("Episode saved: {}", savedEpisodeRepository.save(savedEpisode.get()));
            } else {
                LOGGER.warn("No saved episode returned");
            }
        };
    }

}
