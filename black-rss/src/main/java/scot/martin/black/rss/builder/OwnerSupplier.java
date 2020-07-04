package scot.martin.black.rss.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scot.martin.black.rss.model.Owner;

import java.util.function.Supplier;

@Service
public class OwnerSupplier implements Supplier<Owner> {

    @Value("${podcast.channel.owner.name}")
    private String name;

    @Value("${podcast.channel.owner.email}")
    private String email;

    @Override
    public Owner get() {
        Owner owner = new Owner();

        owner.setName(name);
        owner.setEmail(email);

        return owner;
    }

}
