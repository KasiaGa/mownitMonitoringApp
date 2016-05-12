package gui;

public class GuiTest {

    public static void main(String[] args) {
        Logic logic = new Logic();
        Gui gui = new Gui(logic.getTableData(), logic.getDistinctSources(), logic.getDistinctTypes());
    }
}
