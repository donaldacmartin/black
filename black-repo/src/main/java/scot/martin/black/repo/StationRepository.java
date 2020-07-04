package scot.martin.black.repo;

import org.springframework.data.repository.CrudRepository;
import scot.martin.black.model.entity.Station;

import java.net.URL;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, String> {

    Optional<Station> findByNameAndUrl(String name, URL url);

}
