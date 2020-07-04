package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.model.entity.SavedEpisode;
import scot.martin.black.rss.model.Channel;
import scot.martin.black.rss.model.Image;
import scot.martin.black.rss.model.Item;
import scot.martin.black.rss.model.Owner;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EpisodeChannelTransformer
        implements Function<List<SavedEpisode>, Channel> {

    @Value("${podcast.channel.title}")
    private String title;

    @Value("${podcast.channel.link}")
    private String link;

    @Value("${podcast.channel.language}")
    private String language;

    @Value("${podcast.channel.subtitle}")
    private String subtitle;

    @Value("${podcast.channel.author}")
    private String author;

    @Value("${podcast.channel.summary}")
    private String summary;

    @Value("${podcast.channel.description}")
    private String description;

    @Value("${podcast.channel.explicit}")
    private String explicit;

    @Value("${podcast.channel.category}")
    private String category;

    @Autowired
    private Supplier<Owner> ownerSupplier;

    @Autowired
    private Supplier<Image> imageSupplier;

    @Autowired
    private Function<SavedEpisode, Item> episodeItemTransformer;

    @Override
    public Channel apply(List<SavedEpisode> episodes) {
        Channel channel = new Channel();

        channel.setTitle(title);
        channel.setLink(link);
        channel.setLanguage(language);
        channel.setSubtitle(subtitle);
        channel.setAuthor(author);
        channel.setSummary(summary);
        channel.setDescription(description);
        channel.setExplicit(explicit);
        channel.setCategory(category);
        channel.setOwner(ownerSupplier.get());
        channel.setImage(imageSupplier.get());

        channel.setItems(episodes.stream().map(episodeItemTransformer).collect(Collectors.toList()));

        return channel;
    }
}
