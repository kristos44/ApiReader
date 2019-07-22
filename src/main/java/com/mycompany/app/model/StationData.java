package com.mycompany.app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mycompany.app.deserialiaze.CustomDeserializer;
import lombok.Data;

import java.util.Map;

@Data
@JsonDeserialize(using = CustomDeserializer.class)
public class StationData {
    private final Map<String, Station> stationMap;
}
