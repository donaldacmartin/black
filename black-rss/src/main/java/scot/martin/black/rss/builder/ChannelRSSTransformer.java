package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.rss.model.Channel;
import scot.martin.black.rss.model.RSS;

import java.util.function.Function;

@Service
public class ChannelRSSTransformer implements Function<Channel, RSS> {

    @Value("${podcast.xmlns}")
    private String xmlns;

    @Value("${podcast.version}")
    private String version;

    @Override
    public RSS apply(Channel channel) {
        return new RSS(xmlns, version, channel);
    }
}
