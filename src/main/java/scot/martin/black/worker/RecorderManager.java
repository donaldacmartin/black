package scot.martin.black.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scot.martin.black.entity.Broadcast;
import scot.martin.black.repository.BroadcastRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Component
public class RecorderManager extends Thread {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RecorderManager.class);

    @Autowired
    private BroadcastRepository broadcastRepository;

    @Autowired
    private Predicate<Broadcast> readyToBroadcastPredicate;

    @Autowired
    private Function<Broadcast, Runnable> recorderRunnableFunction;

    private ExecutorService executorService;

    public RecorderManager() {
        executorService = Executors.newFixedThreadPool(2);
    }

    @Scheduled(fixedDelay = 60000)
    public void start() {
        LOGGER.info("Checking for broadcasts ready to air");

        StreamSupport
                .stream(broadcastRepository.findAll().spliterator(), false)
                .filter(readyToBroadcastPredicate)
                .map(recorderRunnableFunction::apply)
                .forEach(executorService::submit);
    }

}
