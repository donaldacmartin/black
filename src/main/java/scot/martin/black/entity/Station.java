package scot.martin.black.entity;

import org.hibernate.annotations.GenericGenerator;
import scot.martin.black.request.StationRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.util.Objects;

@Entity
public class Station {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private URL url;

    protected Station() {
    }

    public Station(StationRequest request) {
        this.name = request.getName();
        this.url = request.getUrl();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(uuid, station.uuid) &&
                Objects.equals(name, station.name) &&
                Objects.equals(url, station.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, url);
    }

    @Override
    public String toString() {
        return "Station{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", url=" + url +
                '}';
    }

}
