package scot.martin.black.rest.controller.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scot.martin.black.model.entity.Station;
import scot.martin.black.model.request.StationRequest;
import scot.martin.black.repo.BroadcastRepository;
import scot.martin.black.repo.StationRepository;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/station")
public class StationController extends CrudController<Station> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationRepository repository;

    @Autowired
    private BroadcastRepository broadcastRepository;

    @Override
    protected CrudRepository<Station, String> getRepository() {
        return repository;
    }

    @Override
    protected Predicate<Station> isDeletable() {
        return s ->
                StreamSupport.stream(broadcastRepository.findAll().spliterator(), false)
                        .noneMatch(b -> b.getStation().equals(s));
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

}
