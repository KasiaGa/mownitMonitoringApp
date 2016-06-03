package gui;

import java.text.ParseException;

public class GuiTest {

    public static void main(String[] args) {
        Logic logic = new Logic();
        try {
            Gui gui = new Gui(logic.getTableData(), logic.getDistinctNames(), logic.getDistinctMonitoringCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
