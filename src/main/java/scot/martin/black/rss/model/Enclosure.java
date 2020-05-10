package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Enclosure {

    @XmlAttribute
    private String url;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String length;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(String length) {
        this.length = length;
    }
    
}
