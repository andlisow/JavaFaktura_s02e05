package io.github.javafaktura.s02e05.child.core.service;

import io.github.javafaktura.s02e05.child.core.model.ChildNameHistoricalStats;
import io.github.javafaktura.s02e05.child.core.model.ChildNameStats;
import io.github.javafaktura.s02e05.child.core.model.ParentPreferences;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface ChildNameService {
    List<ChildNameStats> getAll(ParentPreferences preferences);
    int countAllOccurrences();
    ChildNameStats add(String name);
    ChildNameStats getRandom(ParentPreferences preferences);
    ChildNameStats lookFor(String name);
    Optional<ChildNameHistoricalStats> getHistoricalStats(String name);
    Integer getHistoricalOccurences(String name, Year year);
}
