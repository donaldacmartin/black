package scot.martin.black.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.net.URL;
import java.util.Objects;

public class StationRequest {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "([A-z0-9 '.-]+)")
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private URL url;

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
        StationRequest that = (StationRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "StationRequest{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
