package lazybro.company.factorycrm.entity;

public enum Operations {

    MILL("Фрезерная"),
    TURN("Токарная"),
    DRILL("Сверлильная"),
    TAP("Резьбонарезная"),
    SAW("Заготовительная"),
    FIX("Слесарная");

    private String title;

    Operations(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
