package sqltask;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@SuppressWarnings("java:S106")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}


