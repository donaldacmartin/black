package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Image {

    @XmlAttribute(name = "href")
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

}
