package lazybro.company.factorycrm.entity;

public enum Processing {

    MILLING("Фрезерный"),
    TURNING("Токарный"),
    DRILLING("Сверлильный"),
    TAPPING("Резьбонарезной"),
    SAWING("Ленточнопильный");

    private String title;

    Processing(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
