package scot.martin.black.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scot.martin.black.entity.Station;
import scot.martin.black.repository.StationRepository;
import scot.martin.black.request.StationRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/station")
public class StationController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationRepository repository;

    @GetMapping
    public ResponseEntity<List<Station>> get() {
        LOGGER.info("Request for all stations");

        List<Station> stations = StreamSupport
                .stream(repository.findAll().spliterator(), true)
                .collect(Collectors.toList());

        LOGGER.info("Returning {} station(s)", stations.size());
        return ResponseEntity.ok().body(stations);
    }

    @PostMapping
    public ResponseEntity<Station> add(@Valid @RequestBody StationRequest request) {
        LOGGER.info("Request to create station {}", request);

        ResponseEntity response;
        Station requestStation = new Station(request);

        LOGGER.info("Checking if station already exists");

        Optional<Station> currentStation = repository
                .findByNameAndUrl(request.getName(), request.getUrl());

        if (currentStation.isPresent()) {
            LOGGER.info("Station already exists");

            response = ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .body(currentStation.get());
        } else {
            LOGGER.info("Creating new station");

            Station savedStation = repository.save(requestStation);
            response = ResponseEntity.accepted().body(savedStation);
        }

        return response;
    }

    @DeleteMapping
    @RequestMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable("uuid") String uuid) {
        LOGGER.info("Request to delete station {}", uuid);

        Optional<Station> station = repository.findById(uuid);

        if (station.isPresent()) {
            LOGGER.info("Deleted station {}", station.get());

            repository.delete(station.get());
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.info("Station does not exist");

            return ResponseEntity.notFound().build();
        }
    }

}
