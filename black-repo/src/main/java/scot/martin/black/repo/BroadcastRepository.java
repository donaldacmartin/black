package scot.martin.black.repo;

import org.springframework.data.repository.CrudRepository;
import scot.martin.black.model.entity.Broadcast;

public interface BroadcastRepository extends CrudRepository<Broadcast, String> {
}
