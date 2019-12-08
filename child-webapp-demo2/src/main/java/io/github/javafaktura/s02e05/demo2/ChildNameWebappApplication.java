package io.github.javafaktura.s02e05.demo2;

import io.github.javafaktura.s02e05.child.core.provider.ChildNameHistoricalStatsCsvProvider;
import io.github.javafaktura.s02e05.child.core.provider.ChildNameStatsCsvProvider;
import io.github.javafaktura.s02e05.child.core.service.ChildNameMemoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChildNameWebappApplication {
    public static void main(String[] args) {
        SpringApplication.run(io.github.javafaktura.s02e05.demo2.ChildNameWebappApplication.class, args);
    }

    @Bean
    public ChildNameMemoryService getChooser(@Value("${current.year.csv.name}") String currentYearCsv,
                                             @Value("${historical.csv.name}") String historicalCsv) {
        return new ChildNameMemoryService(
                new ChildNameStatsCsvProvider(currentYearCsv),
                new ChildNameHistoricalStatsCsvProvider(historicalCsv)
        );
    }
}
