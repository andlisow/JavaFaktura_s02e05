package io.github.javafaktura.s02e05.child.core.provider


import io.github.javafaktura.s02e05.child.core.model.ChildNameHistoricalStats
import spock.lang.Specification

import java.time.Year

class ChildNameHistoricalStatsCsvProviderSpec extends Specification {
    private static final String HISTORICAL_STATS_FILENAME = "2_historical_records.csv"

    def "When csv is loaded, for the first record stats from 2016 year are equal to 39"() {
        given:
        ChildNameHistoricalStatsCsvProvider csvProvider = new ChildNameHistoricalStatsCsvProvider(HISTORICAL_STATS_FILENAME);
        List<ChildNameHistoricalStats> historicalChildStats = csvProvider.load()
        expect:
        39 == historicalChildStats.get(0).stats.get(Year.of(2016))
    }
}
