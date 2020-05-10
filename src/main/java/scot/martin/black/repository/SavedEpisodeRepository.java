package scot.martin.black.repository;

import org.springframework.data.repository.CrudRepository;
import scot.martin.black.entity.SavedEpisode;

public interface SavedEpisodeRepository extends CrudRepository<SavedEpisode, String> {
}
