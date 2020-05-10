package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSS {

    @XmlAttribute(name = "xmlns:itunes")
    private String xmlns;

    @XmlAttribute
    private String version;

    @XmlElement
    private Channel channel;

    protected RSS() {}

    public RSS(String xmlns, String version, Channel channel) {
        this.xmlns = xmlns;
        this.version = version;
        this.channel = channel;
    }

}
