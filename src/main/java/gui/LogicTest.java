package gui;

import java.text.ParseException;
import java.util.HashSet;

/**
 * Created by kasia on 12.05.16.
 */
public class LogicTest {

    public static void main(String[] args) {
        Logic logic = new Logic();
        try {
            logic.getTableData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashSet<String> sources = logic.getDistinctNames();
        sources.forEach(e -> System.out.println(e));
    }
}
