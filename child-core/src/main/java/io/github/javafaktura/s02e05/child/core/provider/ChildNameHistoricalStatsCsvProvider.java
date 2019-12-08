package io.github.javafaktura.s02e05.child.core.provider;

import com.opencsv.CSVReader;
import io.github.javafaktura.s02e05.child.core.model.ChildNameHistoricalStats;
import io.github.javafaktura.s02e05.child.core.model.Gender;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ChildNameHistoricalStatsCsvProvider implements ChildNameHistoricalStatsProvider {
    public static final int FIRST_COLUMN_STORING_YEAR = 2;
    private final Logger logger = LoggerFactory.getLogger(ChildNameHistoricalStatsCsvProvider.class);
    private final String path;

    public ChildNameHistoricalStatsCsvProvider(String path) {
        this.path = path;
    }

    @Override
    public List<ChildNameHistoricalStats> load() {
        Resource input = new ClassPathResource(path);

        List<ChildNameHistoricalStats> records = new ArrayList<ChildNameHistoricalStats>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(input.getInputStream()), ',', '"', 0)) {
            String[] values = null;

            String[] headers = csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                String name = values[0];
                String genderSymbol = values[1];
                Map<Year, Integer> historicalStats = new TreeMap();
                for (int i = FIRST_COLUMN_STORING_YEAR; i < values.length; i++) {
                    Integer occurences = Strings.isNotBlank(values[i]) ? Integer.valueOf(values[i]) : 0;
                    historicalStats.put(Year.of(Integer.valueOf(headers[i])), occurences);
                }
                records.add(new ChildNameHistoricalStats(name, Gender.fromSymbol(genderSymbol), historicalStats));
            }
        } catch (FileNotFoundException e) {
            logger.error("Can't read such file {}, {}", path);
        } catch (IOException e) {
            logger.error("Problem during file {} reading, {}", path);
        }
        return records;
    }
}
