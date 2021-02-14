package lazybro.company.factorycrm.entity;

public enum Form {

    ROD("Пруток"),
    CIRCLE("Круг"),
    SHEET("Лист"),
    BLOCK("Блок");

    private String title;

    Form(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
