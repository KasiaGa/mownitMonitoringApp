package generator;

import database.DatabaseObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {

    private List<DatabaseObject> objects;
    private LocalDateTime startTime;
    private Random random;

    public Generator(LocalDateTime startTime) {
        this.startTime = startTime;
        this.random = new Random();
        this.objects = new ArrayList<>();
    }

    private LocalDateTime getRandomTime() {
        Instant startInstant = startTime.toInstant(ZoneOffset.UTC);
        Duration duration = Duration.between(startInstant, Instant.now());
        long start = duration.toMinutes();
        return LocalDateTime.now().minusMinutes(random.nextInt((int) start));
    }

    private List<String> readFile() {
        try {
            List<String> lines = new ArrayList<>();
            Scanner s = new Scanner(new File("src/main/resources/data"));
            while(s.hasNext()) {
                lines.add(s.nextLine());
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void convertToObject(List<String> lines) {
        for (String line: lines) {
            String[] fields = line.split(";");
            DatabaseObject databaseObject = new DatabaseObject();
            databaseObject.setSourceName(fields[0]);
            databaseObject.setSourceId(Integer.parseInt(fields[1]));
            databaseObject.setType(fields[2]);
            databaseObject.setComment(fields[3]);
            databaseObject.setTimestamp(getRandomTime().toString());

            objects.add(databaseObject);
        }
    }

    public void generate() {
        convertToObject(readFile());
    }

    public DatabaseObject getRandom() {
        int size = objects.size();
        return objects.get(random.nextInt(size));
    }


}
