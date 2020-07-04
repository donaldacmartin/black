package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Channel {

    @XmlElement
    private String title;

    @XmlElement
    private String link;

    @XmlElement
    private String language;

    @XmlElement(name = "itunes:subtitle")
    private String subtitle;

    @XmlElement(name = "itunes:author")
    private String author;

    @XmlElement(name = "itunes:summary")
    private String summary;

    @XmlElement
    private String description;

    @XmlElement(name = "itunes:owner")
    private Owner owner;

    @XmlElement(name = "itunes:explicit")
    private String explicit;

    @XmlElement(name = "itunes:image")
    private Image image;

    @XmlElement(name = "itunes:category")
    private String category;

    @XmlElement(name = "item")
    private List<Item> items;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setExplicit(String explicit) {
        this.explicit = explicit;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
