package com.mycompany.app;

import com.mycompany.app.fetcher.DataFetcher;
import com.mycompany.app.properties.PropertiesLoader;
import org.apache.commons.cli.*;

import java.util.Properties;

public class App
{
    public static void main( String[] args )
    {
        Options options = new Options();

        Option apiUrl = new Option("apiUrl", "apiUrl", true, "Api url");
        apiUrl.setRequired(true);
        options.addOption(apiUrl);

        Option country = new Option("country", "country", true, "Country");
        options.addOption(country);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        Properties properties = PropertiesLoader.getProperties();

        try {
            cmd = parser.parse(options, args);
            DataFetcher dataFetcher = new DataFetcher();
            System.out.println(dataFetcher.fetchData(cmd.getOptionValue("apiUrl"),
                    cmd.getOptionValue("country") != null ? cmd.getOptionValue("country") : "NO",
                    properties.get("apiKey").toString()));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
    }
}
