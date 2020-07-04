package scot.martin.black.repo;

import org.springframework.data.repository.CrudRepository;
import scot.martin.black.model.entity.SavedEpisode;

public interface SavedEpisodeRepository extends CrudRepository<SavedEpisode, String> {
}
