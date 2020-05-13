package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.entity.SavedEpisode;
import scot.martin.black.rss.model.Enclosure;
import scot.martin.black.rss.model.Item;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.LongFunction;

@Service
public class EpisodeItemTransformer implements Function<SavedEpisode, Item> {

    @Value("${podcast.channel.link}")
    private String link;

    @Value("${podcast.channel.explicit}")
    private String explicit;

    @Autowired
    private Function<SavedEpisode, Enclosure> episodeEnclosureTransformer;

    @Autowired
    private LongFunction<String> minuteTimeTransformer;

    @Autowired
    private Function<LocalDateTime, String> dateTimeFunction;

    @Override
    public Item apply(SavedEpisode episode) {
        Item item = new Item();

        item.setTitle(episode.getBroadcast().getName());
        item.setDescription(episode.getBroadcast().getName());
        item.setSummary(episode.getBroadcast().getName());
        item.setLink(link);
        item.setPubDate(dateTimeFunction.apply(episode.getStart()));
        item.setAuthor(episode.getBroadcast().getStation().getName());
        item.setDuration(minuteTimeTransformer.apply(episode.getBroadcast().getLengthInMinutes()));
        item.setEnclosure(episodeEnclosureTransformer.apply(episode));
        item.setExplicit(explicit);
        item.setGuid(episode.getUuid());

        return item;
    }
}
