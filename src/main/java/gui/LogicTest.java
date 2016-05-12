package gui;

import java.util.HashSet;

/**
 * Created by kasia on 12.05.16.
 */
public class LogicTest {

    public static void main(String[] args) {
        Logic logic = new Logic();
        logic.getTableData();

        HashSet<String> sources = logic.getDistinctSources();
        sources.forEach(e -> System.out.println(e));
    }
}
