package com.mycompany.app.fetcher;

import com.mycompany.app.formatter.ResponseFormatter;
import com.mycompany.app.integration.FuelApiClient;
import com.mycompany.app.mapper.JsonToModelMapper;
import com.mycompany.app.model.StationData;
import com.mycompany.app.properties.PropertiesLoader;

import java.util.*;

public class DataFetcher {
    private FuelApiClient fuelApiClient;
    private JsonToModelMapper jsonToModelMapper;
    private ResponseFormatter responseFormatter;

    public DataFetcher() {
        fuelApiClient = new FuelApiClient();
        jsonToModelMapper = new JsonToModelMapper();
        responseFormatter = new ResponseFormatter();
    }

    public DataFetcher(FuelApiClient fuelApiClient) {
        this.fuelApiClient = fuelApiClient;
        jsonToModelMapper = new JsonToModelMapper();
        responseFormatter = new ResponseFormatter();
    }

    public String fetchData(String apiUrl, String country) {
        Properties properties = PropertiesLoader.getProperties();

        Optional<StationData> staticStationDataOptional = jsonToModelMapper.mapJsontoStationData(
                fuelApiClient.fetchStationData(apiUrl + properties.get("staticApiUrl") + country));

        Optional<StationData> dynamicStationDataOptional = jsonToModelMapper.mapJsontoStationData(
                fuelApiClient.fetchStationData(apiUrl + properties.get("dynamicApiUrl") + country));

        return responseFormatter.formatResponse(staticStationDataOptional, dynamicStationDataOptional);
    }
}
