package sqltask.applicationmenu;

@SuppressWarnings("java:S106")
public class MenuItem implements Menu {

    String label;
    Action action;

    public MenuItem(String label, Action action) {
        this.label = label;
        this.action = action;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void doAction() {
        try {
            action.doAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
