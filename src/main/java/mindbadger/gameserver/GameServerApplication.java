package mindbadger.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"mindbadger"})
public class GameServerApplication {

    public static void main(String[] args) {
//        ApplicationContext ctx = 
        SpringApplication.run(GameServerApplication.class, args);
    }

}
