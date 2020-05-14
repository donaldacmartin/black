package scot.martin.black.controller.podcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scot.martin.black.entity.SavedEpisode;
import scot.martin.black.repository.SavedEpisodeRepository;
import scot.martin.black.rss.builder.ChannelRSSTransformer;
import scot.martin.black.rss.model.Channel;
import scot.martin.black.rss.model.RSS;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/podcast")
public class PodcastController {

    @Autowired
    private Function<List<SavedEpisode>, Channel> episodeChannelTransformer;

    @Autowired
    private ChannelRSSTransformer channelRssTransformer;

    @Autowired
    private SavedEpisodeRepository episodeRepository;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public RSS get() {
        List<SavedEpisode> episodes = StreamSupport
                .stream(episodeRepository.findAll().spliterator(), false)
                .sorted(Collections.reverseOrder(Comparator.comparing(SavedEpisode::getStart)))
                .collect(Collectors.toList());

        return episodeChannelTransformer
                .andThen(channelRssTransformer)
                .apply(episodes);
    }

}
