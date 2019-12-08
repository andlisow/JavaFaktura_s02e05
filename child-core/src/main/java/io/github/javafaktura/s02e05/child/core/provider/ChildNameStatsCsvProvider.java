package io.github.javafaktura.s02e05.child.core.provider;

import com.opencsv.CSVReader;
import io.github.javafaktura.s02e05.child.core.model.ChildNameStats;
import io.github.javafaktura.s02e05.child.core.model.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ChildNameStatsCsvProvider implements ChildNameStatsProvider {
    private final Logger logger = LoggerFactory.getLogger(ChildNameStatsCsvProvider.class);
    private final String path;

    public ChildNameStatsCsvProvider(String path) {
        this.path = path;
    }

    @Override
    public List<ChildNameStats> load() {


        Resource input = new ClassPathResource(path);
//        InputStream in = input.getInputStream();

        List<ChildNameStats> records = new ArrayList<ChildNameStats>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(input.getInputStream()), ',', '"', 1)) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(new ChildNameStats(values[0], Integer.valueOf(values[1]), Gender.fromSymbol(values[2])));
            }
        } catch (FileNotFoundException e) {
            logger.error("Can't read such file {}, {}", path);
        } catch (IOException e) {
            logger.error("Problem during file {} reading, {}", path);
        }
        return records;
    }
}
