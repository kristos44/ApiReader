package com.mycompany.app.deserialiaze;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.app.model.Station;
import com.mycompany.app.model.StationData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomDeserializer extends JsonDeserializer<StationData> {

    @Override
    public StationData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

        Map<String, Station> map = new HashMap<>();

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode rootNode = null;
        try {
            rootNode = oc.readTree(jsonParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode items = rootNode.get("data");

        for (int i = 0; i < items.size(); i++) {

            JsonNode childNode = items.get(i);

            Station station = new Station(childNode.get("id").asText(),
                    childNode.get("brand") != null ? childNode.get("brand").asText() : "");

            map.put(station.getId(), station);
        }

        return new StationData(map);
    }
}
