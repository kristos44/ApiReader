package com.mycompany.app.formatter;

import com.mycompany.app.model.StationData;

import java.util.*;

public class ResponseFormatter {
    public String formatResponse(Optional<StationData> staticStationDataOptional,
                                 Optional<StationData> dynamicStationDataOptional) {
        if(staticStationDataOptional.isPresent() && dynamicStationDataOptional.isPresent()) {
            Map<String, Integer> stationsStats = new HashMap<>();

            StationData staticStationData = staticStationDataOptional.get();
            StationData dynamicStationData = dynamicStationDataOptional.get();

            staticStationData.getStationMap().forEach((k, v) -> {
                if(dynamicStationData.getStationMap().containsKey(k)) {
                    String brand = v.getBrand();
                    if(stationsStats.containsKey(brand)) {
                        stationsStats.put(brand, stationsStats.get(brand) + 1);
                    } else {
                        stationsStats.put(brand, 1);
                    }
                }
            });

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Stacji ogółem: " + staticStationData.getStationMap().size() + "\n");
            stringBuilder.append("Stacji z cenami: " + dynamicStationData.getStationMap().size() + "\n");

            Map<String, Integer> sortedStationsStats = sortByValues(stationsStats);
            sortedStationsStats.forEach((k, v) -> stringBuilder.append(k + " " + v + "\n"));

            return stringBuilder.toString();
        }

        return "";
    }

    public static <K extends Comparable<K>, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                (k1, k2) -> {
                    int compare = map.get(k2).compareTo(map.get(k1));
                    if (compare == 0) {
                        return k1.compareTo(k2);
                    } else {
                        return compare;
                    }
                };

        Map<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
