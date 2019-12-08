package io.github.javafaktura.s02e05.child.core.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.Year

class ChildNameHistoricalStatsSpec extends Specification {


    @Unroll
    def "For given historical stats #historicalStats model should return #mostPopularYear as most popular year"() {
        when:
        ChildNameHistoricalStats childHistoricalStats = new ChildNameHistoricalStats("_", Gender.MALE, historicalStats)
        then:
        mostPopularYear == childHistoricalStats.getLastMostPopularYear()
        where:
        mostPopularYear || historicalStats
        2012            || [(Year.of(2010)) : 1, (Year.of(2011)) : 2, (Year.of(2012)) : 3]
        2012            || [(Year.of(2010)) : 3, (Year.of(2011)) : 2, (Year.of(2012)) : 3]
        2012            || [(Year.of(2010)) : 1, (Year.of(2011)) : 1, (Year.of(2012)) : 1]
        2010            || [(Year.of(2010)) : 1, (Year.of(2011)) : 0, (Year.of(2012)) : 0]
    }

}
