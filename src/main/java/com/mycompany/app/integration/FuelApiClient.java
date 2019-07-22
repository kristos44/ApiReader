package com.mycompany.app.integration;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class FuelApiClient {
    private Client client;

    public FuelApiClient() {
        client = Client.create();
    }

    public String fetchStationData(String url) throws RuntimeException {
        WebResource webResource = client.resource(url);

        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);

        if(clientResponse.getStatus() != 200) {
            throw new RuntimeException("HTTP error " + clientResponse.getStatus());
        }

        return clientResponse.getEntity(String.class);
    }
}
