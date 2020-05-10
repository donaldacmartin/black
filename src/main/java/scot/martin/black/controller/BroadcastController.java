package scot.martin.black.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scot.martin.black.entity.Broadcast;
import scot.martin.black.entity.Station;
import scot.martin.black.repository.BroadcastRepository;
import scot.martin.black.repository.StationRepository;
import scot.martin.black.request.BroadcastRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BroadcastController.class);

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private BroadcastRepository repository;

    @GetMapping
    public ResponseEntity<List<Broadcast>> get() {
        LOGGER.info("Request for all broadcasts");

        List<Broadcast> broadcasts = StreamSupport
                .stream(repository.findAll().spliterator(), true)
                .collect(Collectors.toList());

        LOGGER.info("Returning {} broadcast(s)", broadcasts.size());
        return ResponseEntity.ok().body(broadcasts);
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
            repository.save(broadcast);
            LOGGER.info("Broadcast created {}", broadcast);
            response = ResponseEntity.accepted().body(broadcast);
        } else {
            LOGGER.info("Station UUID is not present");
            response = ResponseEntity.unprocessableEntity().build();
        }

        return response;
    }

    @DeleteMapping
    @RequestMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable("uuid") String uuid) {
        LOGGER.info("Request to delete broadcast {}", uuid);

        ResponseEntity response;

        Optional<Broadcast> broadcast = repository.findById(uuid);

        if (broadcast.isPresent()) {
            repository.delete(broadcast.get());
            LOGGER.info("Deleted broadcast {}", broadcast.get());
            response = ResponseEntity.noContent().build();
        } else {
            LOGGER.info("Broadcast does not exist");
            response = ResponseEntity.notFound().build();
        }

        return response;
    }

}
