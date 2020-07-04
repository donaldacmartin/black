package scot.martin.black.rest.controller.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scot.martin.black.model.entity.SavedEpisode;
import scot.martin.black.repo.SavedEpisodeRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@RestController
@RequestMapping("/episode")
public class EpisodeController extends CrudController<SavedEpisode> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EpisodeController.class);

    @Autowired
    private SavedEpisodeRepository repository;

    @Override
    protected CrudRepository<SavedEpisode, String> getRepository() {
        return repository;
    }

    @Override
    protected Predicate<SavedEpisode> isDeletable() {
        return s -> true;
    }

    @Override
    protected Optional<Consumer<SavedEpisode>> followUpActions() {
        return Optional.of(s -> {
            try {
                LOGGER.info("Deleting {}", s.getLocation().toPath());
                Files.deleteIfExists(s.getLocation().toPath());
            } catch (IOException e) {
                LOGGER.error("Could not delete: {}", e.getMessage());
            }
        });
    }
}
