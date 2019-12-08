package io.github.javafaktura.s02e05.child.core.service

import io.github.javafaktura.s02e05.child.core.model.*
import io.github.javafaktura.s02e05.child.core.provider.ChildNameHistoricalStatsProvider
import io.github.javafaktura.s02e05.child.core.provider.ChildNameStatsProvider
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Year

class ChildNameServiceSpec extends Specification {

    private ChildNameService childNameService = new ChildNameMemoryService(
            new ChildNameStatsProviderFromMemory(),
            new ChildNameHistoricalStatsProviderFromMemory()
    )

    @Unroll
    def "When gender is #gender and popularity is #popularity should return name #name "() {
        expect:
            name == childNameService.getRandom(new ParentPreferences(gender, popularity)).name
        where:
            gender        | popularity              || name
            Gender.MALE   | Popularity.MOST_POPULAR || "ANTONI"
            Gender.MALE   | Popularity.VERY_RARE    || "BEN"
            Gender.FEMALE | Popularity.POPULAR      || "KAMILA"
    }

    def "Counting all occurrences should return 8788"() {
        expect:
        8788 == childNameService.countAllOccurrences()
    }

    @Unroll
    def "For given name = #name and year = #year should return given occurences = #expectedOccurences"() {
        expect:
        expectedOccurences == childNameService.getHistoricalOccurences(name, Year.of(year))
        where:
        name            |year || expectedOccurences
        "ANTONI"        |2010 || 10
        "ANTONI"        |2005 || 0
        "XENOTRAPEZ"    |2013 || 2
        "NONEXISTINGGUY"|2010 || 0

    }


    class ChildNameStatsProviderFromMemory implements ChildNameStatsProvider {
        @Override
        List<ChildNameStats> load() {
            return [
                    new ChildNameStats("ANTONI", 4247, Gender.MALE),
                    new ChildNameStats("BEN", 5, Gender.MALE),
                    new ChildNameStats("JULIAN", 99, Gender.MALE),
                    new ChildNameStats("ZUZANNA", 4251, Gender.FEMALE),
                    new ChildNameStats("KAMILA", 186, Gender.FEMALE),

            ]
        }
    }

    class ChildNameHistoricalStatsProviderFromMemory implements ChildNameHistoricalStatsProvider{
        @Override
        List<ChildNameHistoricalStats> load() {
            return [
                    new ChildNameHistoricalStats("ANTONI", Gender.MALE, Map.of(
                            Year.of(2010), 10,
                            Year.of(2011), 12,
                            Year.of(2012), 15,
                            Year.of(2013), 16)
                    ),
                    new ChildNameHistoricalStats("XENOTRAPEZ", Gender.MALE, Map.of(
                            Year.of(2010), 1,
                            Year.of(2011), 0,
                            Year.of(2012), 0,
                            Year.of(2013), 2)
                    )
            ]
        }
    }
}
