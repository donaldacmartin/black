package scot.martin.black.controller.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CrudController<A> {

    protected static final Logger LOGGER =
            LoggerFactory.getLogger(CrudController.class);

    protected abstract CrudRepository<A, String> getRepository();

    protected abstract Predicate<A> isDeletable();

    protected Optional<Consumer<A>> followUpActions() {
        return Optional.empty();
    }

    @GetMapping
    public ResponseEntity<List<A>> get() {
        LOGGER.info("Request for all items");

        List<A> items = StreamSupport
                .stream(getRepository().findAll().spliterator(), true)
                .collect(Collectors.toList());

        LOGGER.info("Returning {} item(s)", items.size());
        return ResponseEntity.ok().body(items);
    }

    @DeleteMapping
    @RequestMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable("uuid") String uuid) {
        LOGGER.info("Request to delete UUID {}", uuid);

        Optional<A> item = getRepository().findById(uuid);
        ResponseEntity response = null;

        if (item.isPresent()) {
            LOGGER.info("Item is present {}", item.get());

            if (isDeletable().test(item.get())) {
                LOGGER.info("Deleted item {}", item.get());

                getRepository().delete(item.get());
                followUpActions().ifPresent(a -> a.accept(item.get()));
                response = ResponseEntity.noContent().build();
            } else {
                LOGGER.info("Item not deletable");
                response = ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            LOGGER.info("Item does not exist");
            response = ResponseEntity.notFound().build();
        }

        return response;
    }

}
