package lazybro.company.factorycrm.entity;

public enum Specialization {

    MILL("Фреза"),
    TURN("Резец"),
    DRILL("Сверло"),
    TAP("Метчик");

    private String title;

    Specialization(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
