package scot.martin.black.rss.model;

import javax.xml.bind.annotation.XmlElement;

public class Owner {

    @XmlElement(name = "itunes:name")
    private String name;

    @XmlElement(name = "itunes:email")
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
