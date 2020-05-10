package scot.martin.black.repository;

import org.springframework.data.repository.CrudRepository;
import scot.martin.black.entity.Broadcast;

public interface BroadcastRepository extends CrudRepository<Broadcast, String> {
}
