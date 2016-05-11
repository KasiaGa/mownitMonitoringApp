import database.Connection;
import generator.Generator;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Generator generator = new Generator(LocalDateTime.now().minusDays(10));
        Connection connection = new Connection();

        generator.generate();

        for (int i = 0; i < 10; i++) {
            connection.insertData(generator.getRandom(), i);
        }

    }
}
