package lazybro.company.factorycrm.entity;

public enum State {

    WORKS("Работает"),
    BROKEN("Сломан"),
    REPAIRING("Ремонтируется");

    private String title;

    State(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
