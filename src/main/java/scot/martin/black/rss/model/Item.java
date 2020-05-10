package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlElement;

public class Item {

    @XmlElement
    private String title;

    @XmlElement(name = "itunes:summary")
    private String summary;

    @XmlElement
    private String description;

    @XmlElement
    private String link;

    @XmlElement
    private Enclosure enclosure;

    @XmlElement
    private String pubDate;

    @XmlElement(name = "itunes:author")
    private String author;

    @XmlElement(name = "itunes:duration")
    private String duration;

    @XmlElement(name = "itunes:explicit")
    private String explicit;

    @XmlElement
    private String guid;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setExplicit(String explicit) {
        this.explicit = explicit;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
