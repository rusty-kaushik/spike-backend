package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.userDto.City;
import com.spike.SecureGate.DTO.userDto.Country;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.spike.SecureGate.DTO.userDto.State;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class DropdownHelper {


    public List<Country> readCsvFileForCountry() throws IOException {
        List<Country> countries = new ArrayList<>();

        // Load CSV from "src/main/resources/countries.csv"
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("csv/countries.csv"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            if (csvParser.getHeaderMap().isEmpty()) {
                throw new IOException("CSV file does not have headers or is empty.");
            }

            for (CSVRecord csvRecord : csvParser) {
                // Access by column header name
                String id = csvRecord.get("id");
                String name = csvRecord.get("name");
                Country country = new Country(id, name);
                countries.add(country);
            }
        }

        return countries;
    }

    // Method to get states by country name
    public List<State> getStatesByCountryName(String countryName) throws IOException {
        List<State> states = readCsvFileForStates();
        List<State> result = new ArrayList<>();

        for (State state : states) {
            if (state.getCountryName().equalsIgnoreCase(countryName)) {
                result.add(state);
            }
        }

        return result;
    }

    public List<State> readCsvFileForStates() throws IOException {
        List<State> states = new ArrayList<>();

        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("csv/states.csv"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            if (csvParser.getHeaderMap().isEmpty()) {
                throw new IOException("CSV file does not have headers or is empty.");
            }

            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("name");
                int countryId = Integer.parseInt(csvRecord.get("country_id"));
                String countryCode = csvRecord.get("country_code");
                String countryName = csvRecord.get("country_name");

                State state = new State(name, countryName);
                states.add(state);
            }
        }

        return states;
    }

    // Method to get cities by state name
    public List<City> getCitiesByStateName(String stateName) throws IOException {
        List<City> cities = readCsvFileForCities();
        List<City> result = new ArrayList<>();
        for (City city : cities) {
            if (city.getStateName().equalsIgnoreCase(stateName)) {
                result.add(city);
            }
        }
        return result;
    }

    public List<City> readCsvFileForCities() throws IOException {
        List<City> cities = new ArrayList<>();
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("csv/cities.csv"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            if (csvParser.getHeaderMap().isEmpty()) {
                throw new IOException("CSV file does not have headers or is empty.");
            }
            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("name");
                String stateName = csvRecord.get("state_name");

                City city = new City(name, stateName);
                cities.add(city);
            }
        }
        return cities;
    }
}
