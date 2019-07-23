package com.mycompany.app.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.model.StationData;

import java.io.IOException;
import java.util.Optional;

public class JsonToModelMapper {
    private ObjectMapper objectMapper;

    public JsonToModelMapper() {
        objectMapper = new ObjectMapper();
    }

    public Optional<StationData> mapJsonToStationData(String json) {
        StationData stationData;
        try {
            stationData = objectMapper.readValue(json, StationData.class);
            return Optional.of(stationData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
