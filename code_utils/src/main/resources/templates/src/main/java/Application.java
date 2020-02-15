package ${pPackage};

import ${pPackage}.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author ${author}
 * @date ${currTime?datetime}
 * @desc
 */
@SpringBootApplication(scanBasePackages = "${path1}.${path2}")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
