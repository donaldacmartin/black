package scot.martin.black;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackApplication.class, args);
    }

}
