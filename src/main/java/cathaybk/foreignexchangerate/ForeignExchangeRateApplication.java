package cathaybk.foreignexchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForeignExchangeRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForeignExchangeRateApplication.class, args);
    }

}
