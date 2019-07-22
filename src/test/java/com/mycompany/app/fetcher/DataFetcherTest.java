package com.mycompany.app.fetcher;

import com.mycompany.app.integration.FuelApiClient;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DataFetcherTest {
    @Test
    public void fetchDataTest() {
        FuelApiClient testFuelApiClient = mock(FuelApiClient.class);

        String staticResponse = "";
        String dynamicResponse = "";

        try {
            staticResponse = IOUtils.toString(
                    this.getClass().getResourceAsStream("/StaticFeed-NO.json"), "UTF-8"
            );
            dynamicResponse = IOUtils.toString(
                    this.getClass().getResourceAsStream("/DynamicFeed-NO.json"), "UTF-8"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(testFuelApiClient.fetchStationData("http://localhost:8080/fuel/v1.0/bulk/static/NO"))
                .thenReturn(staticResponse);

        when(testFuelApiClient.fetchStationData("http://localhost:8080/fuel/v1.0/bulk/dynamic/NO"))
                .thenReturn(dynamicResponse);

        DataFetcher dataFetcher = new DataFetcher(testFuelApiClient);

        assertEquals("Stacji ogółem: 1831\n" +
                        "Stacji z cenami: 1625\n" +
                        "Circle K 428\n" +
                        "Shell 333\n" +
                        "Esso 236\n" +
                        "YX 227\n" +
                        "Uno-X 167\n" +
                        "Best 86\n" +
                        "Independent 73\n" +
                        "St1 32\n" +
                        "Bunker Oil 28\n" +
                        "Automat1 5\n" +
                        "1-2-3 1\n" +
                        "Automat 1 1\n" +
                        "Bilhuset Sandnes 1\n" +
                        "Buskerud Olje AS 1\n" +
                        "Fåvang Servicesenter As 1\n" +
                        "Jæren Olje Skurve 1\n" +
                        "Max Bensin 1\n" +
                        "Pedersen Varmeservice Alta 1\n" +
                        "Tanken Brenna 1\n" +
                        "Trøndelag Diesel 1\n",
                dataFetcher.fetchData("http://localhost:8080", "NO"));
    }
}
