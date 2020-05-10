package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.rss.model.Image;

import java.util.function.Supplier;

@Service
public class ImageSupplier implements Supplier<Image> {

    @Value("${podcast.channel.image.link}")
    private String link;

    @Override
    public Image get() {
        Image image = new Image();
        image.setUrl(link);
        return image;
    }

}
