package com.mycompany.app.fetcher;

import com.mycompany.app.integration.FuelApiClient;
import com.mycompany.app.mapper.JsonToModelMapper;
import com.mycompany.app.model.StationData;
import com.mycompany.app.properties.PropertiesLoader;

import java.util.*;

public class DataFetcher {
    private FuelApiClient fuelApiClient;
    private JsonToModelMapper jsonToModelMapper;

    public DataFetcher() {
        fuelApiClient = new FuelApiClient();
        jsonToModelMapper = new JsonToModelMapper();
    }

    public DataFetcher(FuelApiClient fuelApiClient) {
        this.fuelApiClient = fuelApiClient;
        jsonToModelMapper = new JsonToModelMapper();
    }

    public String fetchData(String apiUrl, String country) {
        Properties properties = PropertiesLoader.getProperties();

        Optional<StationData> staticStationDataOptional = jsonToModelMapper.mapJsontoStationData(
                fuelApiClient.fetchStationData(apiUrl + properties.get("staticApiUrl") + country));

        Optional<StationData> dynamicStationDataOptional = jsonToModelMapper.mapJsontoStationData(
                fuelApiClient.fetchStationData(apiUrl + properties.get("dynamicApiUrl") + country));

        Map<String, Integer> stationsStats = new HashMap<>();

        if(staticStationDataOptional.isPresent() && dynamicStationDataOptional.isPresent()) {
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
