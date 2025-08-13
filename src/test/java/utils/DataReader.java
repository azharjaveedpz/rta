package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.InputStream;

public class DataReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getTestData(String key) throws Exception {
        // Load file from resources folder
        InputStream is = DataReader.class
                .getClassLoader()
                .getResourceAsStream("testdata/qa/whitelistplate_data.json");

        if (is == null) {
            throw new RuntimeException("Test data file not found in resources/testdata/");
        }

        JsonNode rootNode = mapper.readTree(is);
        return rootNode.get(key);
    }
}
