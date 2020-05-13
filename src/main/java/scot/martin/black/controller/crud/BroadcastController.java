package scot.martin.black.controller.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scot.martin.black.entity.Broadcast;
import scot.martin.black.entity.Station;
import scot.martin.black.repository.BroadcastRepository;
import scot.martin.black.repository.SavedEpisodeRepository;
import scot.martin.black.repository.StationRepository;
import scot.martin.black.request.BroadcastRequest;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/broadcast")
public class BroadcastController extends CrudController<Broadcast> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BroadcastController.class);

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private SavedEpisodeRepository savedEpisodeRepository;

    @Autowired
    private BroadcastRepository broadcastRepository;

    @Override
    protected CrudRepository<Broadcast, String> getRepository() {
        return broadcastRepository;
    }

    @Override
    protected Predicate<Broadcast> isDeletable() {
        return b ->
                StreamSupport
                        .stream(savedEpisodeRepository.findAll().spliterator(), false)
                        .noneMatch(e -> e.getBroadcast().equals(b));
    }

    @PostMapping
    public ResponseEntity<Broadcast> add(@Valid @RequestBody BroadcastRequest request) {
        LOGGER.info("Request to create broadcast {}", request);

        ResponseEntity response;

        String stationUuid = request.getStationUuid();
        Optional<Station> station = stationRepository.findById(stationUuid);

        if (station.isPresent()) {
            Broadcast broadcast = new Broadcast(request, station.get());
            LOGGER.info("Saving broadcast {}", broadcast);
            broadcastRepository.save(broadcast);
            LOGGER.info("Broadcast created {}", broadcast);
            response = ResponseEntity.accepted().body(broadcast);
        } else {
            LOGGER.info("Station UUID is not present");
            response = ResponseEntity.unprocessableEntity().build();
        }

        return response;
    }

}
