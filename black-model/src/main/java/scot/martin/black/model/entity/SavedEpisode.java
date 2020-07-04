package scot.martin.black.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
public class SavedEpisode {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    private LocalDateTime start;

    @Column(unique = true)
    private File location;

    private Long lengthBytes;

    @ManyToOne()
    @JoinColumn(name = "broadcast_uuid")
    private Broadcast broadcast;

    protected SavedEpisode() {
    }

    public SavedEpisode(LocalDateTime start, File location, Long lengthBytes, Broadcast broadcast) {
        this.start = start;
        this.location = location;
        this.lengthBytes = lengthBytes;
        this.broadcast = broadcast;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public File getLocation() {
        return location;
    }

    public Long getLengthBytes() {
        return lengthBytes;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }
}
