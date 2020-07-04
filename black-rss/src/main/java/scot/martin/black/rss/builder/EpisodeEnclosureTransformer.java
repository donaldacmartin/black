package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.model.entity.SavedEpisode;
import scot.martin.black.rss.model.Enclosure;

import java.util.function.Function;

@Service
public class EpisodeEnclosureTransformer implements Function<SavedEpisode, Enclosure> {

    @Value("${podcast.channel.item.type}")
    private String type;

    @Value("${podcast.channel.link}")
    private String hostUrl;

    @Override
    public Enclosure apply(SavedEpisode episode) {
        Enclosure enclosure = new Enclosure();

        enclosure.setUrl(hostUrl + "/" + episode.getLocation().getName());
        enclosure.setLength(Long.toString(episode.getLengthBytes()));
        enclosure.setType(type);

        return enclosure;
    }
}
