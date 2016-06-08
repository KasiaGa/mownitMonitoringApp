import gui.Gui;
import gui.Logic;

import java.text.ParseException;

public class View {

    public static void main(String[] args) {
        Logic logic = new Logic();
        try {
            Gui gui = new Gui(logic.getTableData(), logic.getDistinctNames(), logic.getDistinctMonitoringCase(), logic.getDistinctAgentAddress());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
