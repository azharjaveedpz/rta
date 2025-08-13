package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class JsonDataGenerator {

    public static void main(String[] args) throws IOException {
        Faker faker = new Faker();
        Random random = new Random();
        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file into a String
        File file = new File("src/test/resources/validRecord.json");
        String jsonContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));

        // Replace placeholders with random/faker data
        jsonContent = jsonContent.replace("RANDOM_PLATE", faker.bothify("??###??###"));
        jsonContent = jsonContent.replace("RANDOM_SOURCE", faker.country().name());
        jsonContent = jsonContent.replace("RANDOM_COLOR", faker.color().name());

        // Date handling
        LocalDateTime fromDate = LocalDateTime.now().plusDays(1);
        LocalDateTime toDate = fromDate.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        jsonContent = jsonContent.replace("RANDOM_FROM_DATE", fromDate.format(formatter));
        jsonContent = jsonContent.replace("RANDOM_TO_DATE", toDate.format(formatter));

        // Random integer
        jsonContent = jsonContent.replace("RANDOM_NUMBER", String.valueOf(random.nextInt(100)));

        System.out.println("Generated JSON:\n" + jsonContent);
    }
}
