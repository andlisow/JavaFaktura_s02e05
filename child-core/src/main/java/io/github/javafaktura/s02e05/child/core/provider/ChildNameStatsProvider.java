package io.github.javafaktura.s02e05.child.core.provider;

import io.github.javafaktura.s02e05.child.core.model.ChildNameStats;

import java.util.List;

public interface ChildNameStatsProvider {
    List<ChildNameStats> load();
}
